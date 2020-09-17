package com.namviet.vtvtravel.view.fragment.f2account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentLoginBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToBooking;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToCallNow;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndReloadDeal;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.model.f2event.OnReloadCountSystemInbox;
import com.namviet.vtvtravel.model.f2event.OnUpdateLogin;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;
import com.namviet.vtvtravel.tracking.TrackingViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginF2Fragment extends BaseFragment<F2FragmentLoginBinding> implements Observer {
    private AccountViewModel accountViewModel;
    private PreferenceUtil mPreferenceUtil;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_login;
    }

    @Override
    public void initView() {

        accountViewModel = new AccountViewModel();
        getBinding().setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);

        handleValidateSuccess(getBinding().edtUsername, getBinding().linearUsername);
        handleValidateSuccess(getBinding().edtPassword, getBinding().linearPassword);


        getBinding().btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = getBinding().edtUsername.getText().toString();
                String password = getBinding().edtPassword.getText().toString();

                if (mobile.isEmpty()) {
                    handleValidateFail(getBinding().edtUsername, getBinding().linearUsername, getString(R.string.phone_empty_login));
                } else if (password.isEmpty()) {
                    handleValidateFail(getBinding().edtPassword, getBinding().linearPassword, "Mật khẩu không được để trống");
                } else if (!ValidateUtils.isValidPhoneNumberNew(mobile)) {
                    handleValidateFail(getBinding().edtUsername, getBinding().linearUsername, "Số điện thoại nhập không đúng định dạng");
                } else {
//                    showDialogLoading();
                    showLoading();
                    PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.LOGIN, Constants.TypeLogin.MOBILE);
                    PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.MOBILE, mobile);
                    PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.PASSWORD, password);

                    accountViewModel.login(ValidateUtils.isPhoneValidateV2(mobile, 84), password);
                }
            }
        });

        getBinding().imgEyeCovered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                getBinding().imgEyeCovered.setVisibility(View.GONE);
                getBinding().imgEye.setVisibility(View.VISIBLE);
            }
        });

        getBinding().imgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                getBinding().imgEyeCovered.setVisibility(View.VISIBLE);
                getBinding().imgEye.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void initData() {
        mPreferenceUtil = PreferenceUtil.getInstance(getContext());
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new ForgetPassF2Fragment());
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof AccountViewModel && null != o) {
            if (o instanceof AccountResponse) {
                AccountResponse accountResponse = (AccountResponse) o;
                if (accountResponse.isSuccess()) {
                    PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.IS_LOGIN, true);
                    MyApplication.getInstance().setAccount(accountResponse.getData());
                    /*                mActivity.updateLogin();*/
                    EventBus.getDefault().post(new OnUpdateLogin());

//                mActivity.switchFragment(SlideMenu.MenuType.HOME_SCREEN);
                    if (((LoginAndRegisterActivityNew) mActivity).isFromButtonCallNow) {
                        EventBus.getDefault().post(new OnLoginSuccessAndGoToCallNow());
                    }else if(((LoginAndRegisterActivityNew) mActivity).isFromBooking){
                        EventBus.getDefault().post(new OnLoginSuccessAndGoToBooking());
                    }else if(((LoginAndRegisterActivityNew) mActivity).isFromDeal){
                        EventBus.getDefault().post(new OnLoginSuccessAndReloadDeal());
                    }
                    EventBus.getDefault().post(new OnLoginSuccessAndUpdateUserView());
                    EventBus.getDefault().post(new OnReloadCountSystemInbox());
                    String token = FirebaseInstanceId.getInstance().getToken();
                    accountViewModel.notificationReg(DeviceUtils.getDeviceId(getContext()), token, "ANDROID");
                    mPreferenceUtil.setValue(Constants.PrefKey.ACCOUNT_ID, accountResponse.getData().getId().toString());

                    /*mActivity.onBackPressed();*/
                    mActivity.finish();

                } else {
                    ResponseError responseError = (ResponseError) o;
                    Toast.makeText(mActivity, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                    mPreferenceUtil.setValue(Constants.PrefKey.ACCOUNT_ID, "");
                }

            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

    private void handleValidateFail(EditText editText, LinearLayout linearLayout, String error) {
        ((LoginAndRegisterActivityNew) mActivity).showWarning(error);
        linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login_fail));
        editText.requestFocus();
    }

    @SuppressLint("CheckResult")
    private void handleValidateSuccess(EditText editText, LinearLayout linearLayout) {
        RxTextView.afterTextChangeEvents(editText)
                .skipInitialValue()
                .debounce(450, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewAfterTextChangeEvent -> {
                    linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login));
                });
    }
}
