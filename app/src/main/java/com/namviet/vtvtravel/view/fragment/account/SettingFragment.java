package com.namviet.vtvtravel.view.fragment.account;

import android.content.DialogInterface;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentSettingAccBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.service.LinphoneService;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.RuleActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2callnow.SettingNotiFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.RuleDialog;

import org.greenrobot.eventbus.EventBus;
import org.linphone.core.AuthInfo;
import org.linphone.core.Core;
import org.linphone.core.ProxyConfig;

public class SettingFragment extends MainFragment {
    private FragmentSettingAccBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_acc, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        binding.toolBar.tvTitle.setText(getString(R.string.setting));
        binding.toolBar.ivSearch.setVisibility(View.GONE);
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.llChangePass.setOnClickListener(this);
        binding.llLogout.setOnClickListener(this);
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            binding.llChangePass.setVisibility(View.VISIBLE);
            binding.llLogout.setVisibility(View.VISIBLE);
        } else {
            binding.llChangePass.setVisibility(View.GONE);
            binding.llLogout.setVisibility(View.GONE);
        }

        binding.layoutRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RuleActivity.startScreen(mActivity);
            }
        });

        binding.llSetting.setOnClickListener(view -> mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, new SettingNotiFragment()).addToBackStack(null).commit());

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Account account = MyApplication.getInstance().getAccount();
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        }
//        else if (view == binding.ll1) {
//            if (null != account && account.isLogin()) {
//                mActivity.switchFragment(SlideMenu.MenuType.UPDATE_INFO_SCREEN);
//            } else {
//                mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
//            }
//        }
        else if (view == binding.llChangePass) {
            if (null != account && account.isLogin()) {
                mActivity.switchFragment(SlideMenu.MenuType.CHANGE_PASSWORD);
            } else {
                mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
            }
        } else if (view == binding.llLogout) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
            alertDialogBuilder
                    .setMessage(R.string.logout_title)
                    .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (null != account && account.isLogin()) {
                                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.IS_LOGIN, false);
                                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.FACEBOOK_ID, "");
                                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.GOOGLE_ID, "");
                                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.MOBILE, "");
                                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.PASSWORD, "");
                                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.LOGIN, 0);
                                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.ACCOUNT_ID, "");

                                MyApplication.getInstance().setVipRegisted(false);
                                MyApplication.getInstance().setChatBot(true);
                                MyApplication.getInstance().setFirstTimeApp(true);
                                MyApplication.getInstance().setmIsFirstChat(true);

                                MyApplication.getInstance().setAccount(new Account());
                                mActivity.updateLogin();
                                EventBus.getDefault().post(new OnLoginSuccessAndUpdateUserView());
                                removeSIPAccount();
                                mActivity.onBackPressed();

                                try {
                                    TrackingAnalytic.postEvent(TrackingAnalytic.SIGN_OUT, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SETTING, TrackingAnalytic.ScreenTitle.SETTING).setScreen_class(this.getClass().getName()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    })
                    .setNegativeButton(R.string.dimiss, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void removeSIPAccount(){
        try {
            ProxyConfig proxyConfig = LinphoneService.getCore().getDefaultProxyConfig();
            AuthInfo mAuthInfo = proxyConfig.findAuthInfo();
            Core core = LinphoneService.getCore();
            if (core != null) {
                if (proxyConfig != null) {
                    core.removeProxyConfig(proxyConfig);
                }
                if (mAuthInfo != null) {
                    core.removeAuthInfo(mAuthInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
