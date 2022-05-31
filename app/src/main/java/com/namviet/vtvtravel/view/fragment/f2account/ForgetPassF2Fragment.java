package com.namviet.vtvtravel.view.fragment.f2account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentForgetPassBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.StringUtils;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ForgetPassF2Fragment extends BaseFragment<F2FragmentForgetPassBinding> implements Observer {
    private AccountViewModel accountViewModel;
    private String phone;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_forget_pass;
    }

    @Override
    public void initView() {
        accountViewModel = new AccountViewModel();
        getBinding().setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);
    }

    @Override
    public void initData() {
        handleValidateSuccess(getBinding().edtUsername, getBinding().edtPhone);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetError();
//                accountViewModel.resetPassword(phone);
//                addFragment(new RecreatePassF2Fragment());


                /*mobile = binding.edPhone.getText().toString().trim();*/
                phone = getBinding().edtUsername.getText().toString();
                if (phone.isEmpty()) {
//                    ((LoginAndRegisterActivityNew)mActivity).showWarning("Mật khẩu không được để trống");
                    handleValidateFail(getBinding().edtUsername, getBinding().edtPhone, "Số điện thoại không được để trống", getBinding().tvPhoneError);
                } else if (!ValidateUtils.isValidPhoneNumberNew(phone)) {
                    handleValidateFail(getBinding().edtUsername, getBinding().edtPhone, "Số điện thoại nhập không đúng định dạng", getBinding().tvPhoneError);
//                    ((LoginAndRegisterActivityNew)mActivity).showWarning("Số điện thoại nhập không đúng định dạng");
                } else {
                    showLoading();
                    accountViewModel.resetPassword(StringUtils.isPhoneValidateV2(phone, 84));
                }

            }
        });

        getBinding().btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void update(Observable observable, Object o) {
//        if (observable instanceof AccountViewModel && null != o) {
//            if (o instanceof AccountResponse) {
//                AccountResponse accountResponse = (AccountResponse) o;
//
//            } else if (o instanceof ResponseError) {
//                ResponseError responseError = (ResponseError) o;
//                logE(responseError.getMessage());
//            }
//
//        }



        hideLoading();
        if (observable instanceof AccountViewModel) {
            if (null != o) {
                if (o instanceof AccountResponse) {
                    AccountResponse accountResponse = (AccountResponse) o;
                    if (accountResponse.isSuccess()) {
                        MyApplication.getInstance().setAccount(accountResponse.getData());
                        Log.e("Debuggg"+"ForgetPassF2", new Gson().toJson(accountResponse.getData()));
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(Constants.IntentKey.KEY_TYPE, Constants.TypeScreen.OTP_RESET_PASS);
//                        bundle.putString(OtpFragment.KEY_MOBILE, mobile);
//                        mActivity.setBundle(bundle);
//                        mActivity.switchFragment(SlideMenu.MenuType.OTP_SCREEN);

                        OtpF2Fragment otpF2Fragment = new OtpF2Fragment();
                        otpF2Fragment.setTypeOTP(Constants.IntentKey.TYPE_OTP_RESET_PASS);
                        otpF2Fragment.setMobile(phone);
                        addFragment(otpF2Fragment);
                    } else {
//                        showMessage(accountResponse.getMessage());
                        ((LoginAndRegisterActivityNew)mActivity).showWarning(accountResponse.getMessage());
                    }
                } else if (o instanceof ErrorResponse) {
                    ErrorResponse responseError = (ErrorResponse) o;
                    try {
//                        ((LoginAndRegisterActivityNew)mActivity).showWarning(responseError.getMessage());

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
    }

    private void handleValidateFail(EditText editText, LinearLayout linearLayout, String error, TextView tvError) {
//        ((LoginAndRegisterActivityNew) mActivity).showWarning(error);
        linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login_fail));
        tvError.setText(error);
        tvError.setVisibility(View.VISIBLE);
        editText.requestFocus();
    }

    @SuppressLint("CheckResult")
    private void handleValidateSuccess(EditText editText, LinearLayout linearLayout) {
        RxTextView.afterTextChangeEvents(editText)
                .skipInitialValue()
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewAfterTextChangeEvent -> {
                    linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login));
                });
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.FORGET_PASS, TrackingAnalytic.ScreenTitle.FORGET_PASS);
    }

    private void resetError(){
        getBinding().tvPhoneError.setVisibility(View.INVISIBLE);
        getBinding().tvLoginFail.setVisibility(View.INVISIBLE);
        getBinding().tvPhoneError.setText("");
        getBinding().tvLoginFail.setText("");
    }
}
