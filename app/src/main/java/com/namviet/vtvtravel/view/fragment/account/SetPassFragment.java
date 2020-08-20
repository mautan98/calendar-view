package com.namviet.vtvtravel.view.fragment.account;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentSetPassBinding;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import java.util.Observable;
import java.util.Observer;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SetPassFragment extends MainFragment implements Observer {

    private FragmentSetPassBinding binding;
    private AccountViewModel accountViewModel;
    private int typeOtp;

    public static SetPassFragment newInstance() {
        SetPassFragment fragment = new SetPassFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            typeOtp = getArguments().getInt(Constants.IntentKey.KEY_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_pass, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar.rlToolBar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
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
        accountViewModel = new AccountViewModel();
        binding.setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.btRegister.setOnClickListener(this);
    }


    @Override
    protected void updateViews() {
        super.updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.btRegister) {
            String pass = binding.edPass.getText().toString().trim();
            String rePass = binding.edRePass.getText().toString().trim();
            if (pass.isEmpty()) {
                showMessage(getString(R.string.pass_empty));
            }else if (pass.length() < 6 || rePass.length() < 6) {
                showMessage(getString(R.string.pass_invalid));
            } else if (!pass.equals(rePass)) {
                showMessage(getString(R.string.re_pass_invalid));
            } else {
                Integer id = MyApplication.getInstance().getAccount().getId();
                String mobile = MyApplication.getInstance().getAccount().getMobile();
                String token = MyApplication.getInstance().getAccount().getToken();
                if (typeOtp == Constants.TypeScreen.OTP_REGISTER){
//                    accountViewModel.setPassRegister(id, mobile, pass);
                } else {
                    accountViewModel.setPassReset(id, mobile, pass, token);
                }
                showDialogLoading();
            }
        } else if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        dimissDialogLoading();
        if (observable instanceof AccountViewModel) {
            if (null != o) {
                if (o instanceof AccountResponse) {
                    AccountResponse accountResponse = (AccountResponse) o;
                    if (accountResponse.isSuccess()) {
                        MyApplication.getInstance().setAccount(accountResponse.getData());
                        mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
                    } else {
                        showMessage(accountResponse.getMessage());
                    }
                }  else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

