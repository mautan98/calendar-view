package com.namviet.vtvtravel.view.fragment.account;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentOtpBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.StringUtils;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class OtpFragment extends MainFragment implements Observer {

    private FragmentOtpBinding binding;
    private AccountViewModel accountViewModel;
    private int typeOtp;
    private Account account;
    private String mobile = "";
    public static final String KEY_MOBILE = "mobile";


    public static OtpFragment newInstance() {
        OtpFragment fragment = new OtpFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) -> {
            //Catch your exception
            // Without System.exit() this will not work.
            System.exit(2);
        });
        if (null != getArguments()) {
            typeOtp = getArguments().getInt(Constants.IntentKey.KEY_TYPE);
            mobile = getArguments().getString(KEY_MOBILE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp, container, false);
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
        //open keyboard
//        KeyboardUtils.showKeyboard(mActivity, mActivity.getCurrentFocus());
//        binding.edOtp.requestFocus();

        account = MyApplication.getInstance().getAccount();
        if (mobile.length() > 0) {
            binding.tvNoteOtp.setText(getString(R.string.tv_note_otp, mobile));
        }
        accountViewModel = new AccountViewModel();
        binding.setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.btVerify.setOnClickListener(this);

        SpannableString content = new SpannableString(getString(R.string.resend_otp));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.tvForgotPass.setText(content);
        binding.tvForgotPass.setOnClickListener(this);

        binding.tvForgotPass.setClickable(false);
        binding.tvForgotPass.setTextColor(getResources().getColor(R.color.md_grey_700));
        new Handler().postDelayed(() -> {
            binding.tvForgotPass.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvForgotPass.setClickable(true);
        }, 120000);
    }


    @Override
    protected void updateViews() {
        super.updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.btVerify) {

            String otp = binding.edOtp.getText().toString().trim();
            String mobile = account.getMobile();
            if (otp.isEmpty()) {
                showMessage(getString(R.string.otp_empty));
            } else {
                showDialogLoading();

                if (null != getArguments()) {
                    typeOtp = getArguments().getInt(Constants.IntentKey.KEY_TYPE);
                }

                if (typeOtp == Constants.TypeScreen.OTP_REGISTER) {
                    accountViewModel.verifyOtpRegister(mobile, otp);
                } else if (typeOtp == Constants.TypeScreen.OTP_RESET_PASS) {
                    accountViewModel.verifyOtpResetPass(mobile, otp);
                }
            }
//        KeyboardUtils.hideKeyboard1(mActivity);

        } else if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.tvForgotPass) {
            binding.tvForgotPass.setClickable(false);
            binding.tvForgotPass.setTextColor(getResources().getColor(R.color.md_grey_700));
            accountViewModel.resendOtp(StringUtils.isPhoneValidateV2(mobile, 84));
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
                        Account account = accountResponse.getData();
                        MyApplication.getInstance().setAccount(account);
                        if (typeOtp == Constants.TypeScreen.OTP_REGISTER) {
                            putBundle(Constants.TypeScreen.OTP_REGISTER);
                        } else if (typeOtp == Constants.TypeScreen.OTP_RESET_PASS) {
                            putBundle(Constants.TypeScreen.OTP_RESET_PASS);
                        }
                    } else {
                        showMessage(accountResponse.getMessage());
                    }
                } else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                }
            }
        }

    }

    private void putBundle(int value) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IntentKey.KEY_TYPE, value);
        bundle.putString(OtpFragment.KEY_MOBILE, mobile);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.SET_PASS_SCREEN);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.edOtp.setText("");
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
        } catch (Exception e) {

        }
    }

}
