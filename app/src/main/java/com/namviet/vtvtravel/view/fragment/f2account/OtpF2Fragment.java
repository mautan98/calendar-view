package com.namviet.vtvtravel.view.fragment.f2account;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baseapp.utils.KeyboardUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentOtpBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.StringUtils;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.f2offline.OtpSuccessDialog;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class OtpF2Fragment extends BaseFragment<F2FragmentOtpBinding> implements Observer {
    private String typeOtp;
    private String mobile;

    private AccountViewModel accountViewModel;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_otp;
    }

    @Override
    public void initView() {
        try {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showKeyBoard(getBinding().et1);
        accountViewModel = new AccountViewModel();
        getBinding().setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);
    }

    @Override
    public void initData() {
        getBinding().et1.requestFocus();
        startCountDown();
        getBinding().et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    getBinding().et2.requestFocus();
                } else if (s.length() == 0) {
                    getBinding().et1.clearFocus();
                }
            }
        });

        getBinding().et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    getBinding().et3.requestFocus();
                } else if (s.length() == 0) {
                    getBinding().et1.requestFocus();
                }
            }
        });

        getBinding().et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    getBinding().et4.requestFocus();
                } else if (s.length() == 0) {
                    getBinding().et2.requestFocus();
                }
            }
        });

        getBinding().et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    getBinding().et5.requestFocus();
                } else if (s.length() == 0) {
                    getBinding().et3.requestFocus();
                }
            }
        });

        getBinding().et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    getBinding().et6.requestFocus();
                } else if (s.length() == 0) {
                    getBinding().et4.requestFocus();
                }
            }
        });

        getBinding().et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    getBinding().et6.clearFocus();
                    KeyboardUtils.hideKeyboard(mActivity, getBinding().et6);
                } else if (s.length() == 0) {
                    getBinding().et5.requestFocus();
                }
            }
        });

        handleValidateSuccess(getBinding().et1, getBinding().et1);
        handleValidateSuccess(getBinding().et2, getBinding().et2);
        handleValidateSuccess(getBinding().et3, getBinding().et3);
        handleValidateSuccess(getBinding().et4, getBinding().et4);
        handleValidateSuccess(getBinding().et5, getBinding().et5);
        handleValidateSuccess(getBinding().et6, getBinding().et6);
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

        getBinding().tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = getBinding().et1.getText().toString();
                String s2 = getBinding().et2.getText().toString();
                String s3 = getBinding().et3.getText().toString();
                String s4 = getBinding().et4.getText().toString();
                String s5 = getBinding().et5.getText().toString();
                String s6 = getBinding().et6.getText().toString();
                if ("".equals(s1) || "".equals(s2) || "".equals(s3) || "".equals(s4) || "".equals(s5) || "".equals(s6)) {
//                    Toast.makeText(mActivity, "Mã OTP không được bỏ trống", Toast.LENGTH_SHORT).show();
                    handleValidateFail(getBinding().et2, getBinding().et2, "Mã OTP không được bỏ trống");
                    handleValidateFail(getBinding().et3, getBinding().et3, "Mã OTP không được bỏ trống");
                    handleValidateFail(getBinding().et4, getBinding().et4, "Mã OTP không được bỏ trống");
                    handleValidateFail(getBinding().et5, getBinding().et5, "Mã OTP không được bỏ trống");
                    handleValidateFail(getBinding().et6, getBinding().et6, "Mã OTP không được bỏ trống");
                    handleValidateFail(getBinding().et1, getBinding().et1, "Mã OTP không được bỏ trống");
                } else {
                    String otp = s1 + s2 + s3 + s4 + s5 + s6;
                    if (typeOtp.equals(Constants.IntentKey.TYPE_OTP_REGISTER)) {
                        showLoading();
                        accountViewModel.verifyOtpRegister(StringUtils.isPhoneValidateV2(mobile, 84), otp);
                    } else if (typeOtp.equals(Constants.IntentKey.TYPE_OTP_RESET_PASS)) {
                        showLoading();
                        accountViewModel.verifyOtpResetPass(StringUtils.isPhoneValidateV2(mobile, 84), otp);
                    }
                }


            }
        });

        getBinding().tvResentOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                startCountDown();
                accountViewModel.resendOtp(StringUtils.isPhoneValidateV2(mobile, 84));
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof AccountViewModel && null != o) {
            if (o instanceof AccountResponse) {
                AccountResponse accountResponse = (AccountResponse) o;
                if (accountResponse.isSuccess()) {
                    Account account = accountResponse.getData();
                    MyApplication.getInstance().setAccount(account);
                    if (typeOtp.equals( Constants.IntentKey.TYPE_OTP_REGISTER)) {
                        OtpSuccessDialog otpSuccessDialog = OtpSuccessDialog.newInstance(new OtpSuccessDialog.ClickButton() {
                            @Override
                            public void onClickButton() {
                                RecreatePassF2Fragment recreatePassF2Fragment = new RecreatePassF2Fragment();
                                recreatePassF2Fragment.setScreenType(Constants.IntentKey.TYPE_OTP_REGISTER);
                                addFragment(recreatePassF2Fragment);
                            }
                        });
                        otpSuccessDialog.show(mActivity.getSupportFragmentManager(), null);

                    } else if (typeOtp.equals(Constants.IntentKey.TYPE_OTP_RESET_PASS)) {
                        OtpSuccessDialog otpSuccessDialog = OtpSuccessDialog.newInstance(new OtpSuccessDialog.ClickButton() {
                            @Override
                            public void onClickButton() {
                                RecreatePassF2Fragment recreatePassF2Fragment = new RecreatePassF2Fragment();
                                recreatePassF2Fragment.setScreenType(Constants.IntentKey.TYPE_OTP_RESET_PASS);
                                addFragment(recreatePassF2Fragment);
                            }
                        });
                        otpSuccessDialog.show(mActivity.getSupportFragmentManager(), null);

                    }
                } else {
//                    showMessage(accountResponse.getMessage());
                    Toast.makeText(mActivity, accountResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                if(responseError.getErrorCode().equals("REQUEST_OTP_GREATER_THAN_RULES")){
                    getBinding().tvTimeLeft.setVisibility(View.GONE);
                }
                try {
                    ((LoginAndRegisterActivityNew)mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }


    private CountDownTimer cTimer;

    private void startCountDown() {
        cTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                String minute = String.valueOf(millisUntilFinished / 60000);
                String second = String.valueOf((millisUntilFinished % 60000) / 1000);
                if (second.length() == 1) {
                    second = "0" + second;
                }
                getBinding().tvTimeLeft.setText("0" + minute + ":" + second);
//                getBinding().tvResentOTP.setAlpha(0.5f);
//                getBinding().tvResentOTP.setClickable(false);
            }

            public void onFinish() {
                getBinding().tvTimeLeft.setText("");
//                getBinding().tvResentOTP.setAlpha(1);
//                getBinding().tvResentOTP.setClickable(true);
            }

        }.start();
    }

    private void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    public void setTypeOTP(String typeOtp) {
        this.typeOtp = typeOtp;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }


    private void handleValidateFail(EditText editText, EditText linearLayout, String error) {
        ((LoginAndRegisterActivityNew) mActivity).showWarning(error);
        linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login_fail));
        editText.requestFocus();
    }

    @SuppressLint("CheckResult")
    private void handleValidateSuccess(EditText editText, EditText linearLayout) {
        RxTextView.afterTextChangeEvents(editText)
                .skipInitialValue()
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewAfterTextChangeEvent -> {
                    linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login));
                });
    }
}
