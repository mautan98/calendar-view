package com.namviet.vtvtravel.view.fragment.f2account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentLoginBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnChangeTab;
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

import org.greenrobot.eventbus.EventBus;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginF2Fragment extends BaseFragment<F2FragmentLoginBinding> implements Observer {
    private AccountViewModel accountViewModel;
    private PreferenceUtil mPreferenceUtil;
    private boolean isShowingPass;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_login;
    }

    @SuppressLint("CheckResult")
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
                resetError();
                String mobile = getBinding().edtUsername.getText().toString();
                String password = getBinding().edtPassword.getText().toString();

                if (mobile.isEmpty()) {
                    handleValidateFail(getBinding().edtUsername, getBinding().linearUsername, getString(R.string.phone_empty_login), getBinding().tvPhoneError);
                } else if (password.isEmpty()) {
                    handleValidateFail(getBinding().edtPassword, getBinding().linearPassword, "Mật khẩu không được để trống", getBinding().tvPassError);
                } else if (!ValidateUtils.isValidPhoneNumberNew(mobile)) {
                    handleValidateFail(getBinding().edtUsername, getBinding().linearUsername, "Số điện thoại nhập không đúng định dạng", getBinding().tvPhoneError);
                } else {
//                    showDialogLoading();
                    showLoading();
                    PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.LOGIN, Constants.TypeLogin.MOBILE);
                    PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.MOBILE, mobile);
                    PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.PASSWORD, password);

                    accountViewModel.login(ValidateUtils.isPhoneValidateV2(mobile, 84), password, PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.DEVICE_TOKEN, ""));


                    try {
                        TrackingAnalytic.postEvent(TrackingAnalytic.SIGN_IN, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.LOGIN, TrackingAnalytic.ScreenTitle.LOGIN).setScreen_class(this.getClass().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        RxView.clicks(getBinding().imgEyeCovered)
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (isShowingPass) {
                        isShowingPass = false;
                        getBinding().edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        getBinding().imgEyeCovered.setImageDrawable(getResources().getDrawable(R.drawable.f2_ic_eye_covered));
                    } else {
                        isShowingPass = true;
                        getBinding().edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        getBinding().imgEyeCovered.setImageDrawable(getResources().getDrawable(R.drawable.f2_ic_eye));
                    }
                    if(getBinding().edtPassword.hasFocus()){
                        getBinding().edtPassword.setSelection(getBinding().edtPassword.length());
                    }
                }, Throwable::printStackTrace);

    }

    @Override
    public void initData() {
        mPreferenceUtil = PreferenceUtil.getInstance(getContext());
        handleFocusChange();
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

        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

        getBinding().btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OnChangeTab(1));
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
                    Log.e("Debuggg"+"LoginF2Frm", new Gson().toJson(accountResponse.getData()));
                    /*                mActivity.updateLogin();*/
                    EventBus.getDefault().post(new OnUpdateLogin());

//                mActivity.switchFragment(SlideMenu.MenuType.HOME_SCREEN);
                    if (((LoginAndRegisterActivityNew) mActivity).isFromButtonCallNow) {
                        EventBus.getDefault().post(new OnLoginSuccessAndGoToCallNow());
                    } else if (((LoginAndRegisterActivityNew) mActivity).isFromBooking) {
                        EventBus.getDefault().post(new OnLoginSuccessAndGoToBooking());
                    } else if (((LoginAndRegisterActivityNew) mActivity).isFromDeal) {
                        EventBus.getDefault().post(new OnLoginSuccessAndReloadDeal());
                    }
                    EventBus.getDefault().post(new OnLoginSuccessAndUpdateUserView());
                    EventBus.getDefault().post(new OnReloadCountSystemInbox());
                    String token = FirebaseInstanceId.getInstance().getToken();
                    accountViewModel.notificationReg(DeviceUtils.getDeviceId(getContext()), token, "ANDROID");
                    mPreferenceUtil.setValue(Constants.PrefKey.ACCOUNT_ID, accountResponse.getData().getId().toString());
                    PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.ACCOUNT, new Gson().toJson(accountResponse.getData()));
                    PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.ACCESS_KEY, accountResponse.getData().getAccessKey());
                    setCurrentTimeStampToCache();
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
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                    getBinding().tvLoginFail.setVisibility(View.VISIBLE);
                    getBinding().tvLoginFail.setText(responseError.getMessage());
                    if(responseError.getMessage().isEmpty()){
                        getBinding().tvLoginFail.setText("Có lỗi đã xảy ra trong quá trình đăng nhâp!");
                    }
                } catch (Exception e) {

                }
            }

        }
    }

    private void setCurrentTimeStampToCache(){
        long tsLong = System.currentTimeMillis()/1000;
        PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.TIME_STAMP, tsLong);
    }

    private void handleValidateFail(EditText editText, LinearLayout linearLayout, String error, TextView tvError) {
//        ((LoginAndRegisterActivityNew) mActivity).showWarning(error);
        linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login_fail));
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(error);
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

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.LOGIN, TrackingAnalytic.ScreenTitle.LOGIN);
    }


    private void handleFocusChange(){
        getBinding().edtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    getBinding().tvPhoneHint.setVisibility(View.VISIBLE);
                }else {
                    getBinding().tvPhoneHint.setVisibility(View.INVISIBLE);
                }
            }
        });


        getBinding().edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    getBinding().tvPassHint.setVisibility(View.VISIBLE);
                }else {
                    getBinding().tvPassHint.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void resetError(){
        getBinding().tvPhoneError.setVisibility(View.INVISIBLE);
        getBinding().tvPassError.setVisibility(View.INVISIBLE);
        getBinding().tvLoginFail.setVisibility(View.INVISIBLE);
        getBinding().tvPhoneError.setText("");
        getBinding().tvPassError.setText("");
        getBinding().tvLoginFail.setText("");
    }
}
