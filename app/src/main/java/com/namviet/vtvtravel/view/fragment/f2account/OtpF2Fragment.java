package com.namviet.vtvtravel.view.fragment.f2account;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.StringUtils;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.f2offline.OtpSuccessDialog;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;
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
        accountViewModel = new AccountViewModel();
        getBinding().setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);
    }

    @Override
    public void initData() {
        startCountDown();

        getBinding().otpView.requestFocus();
        getBinding().otpView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
                ((LoginAndRegisterActivityNew)mActivity).hideWarning();
            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
                KeyboardUtils.hideKeyboard(getActivity(), getBinding().getRoot());
            }
        });
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
                submitOtp();
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

    private void submitOtp() {
        if (getBinding().otpView.getOTP().length()<6) {
            ((LoginAndRegisterActivityNew) mActivity).showWarning("Mã OTP không được bỏ trống");
            getBinding().otpView.showError();
        } else {
            String otp = getBinding().otpView.getOTP();
            if (typeOtp.equals(Constants.IntentKey.TYPE_OTP_REGISTER)) {
                showLoading();
                accountViewModel.verifyOtpRegister(StringUtils.isPhoneValidateV2(mobile, 84), otp);
            } else if (typeOtp.equals(Constants.IntentKey.TYPE_OTP_RESET_PASS)) {
                showLoading();
                accountViewModel.verifyOtpResetPass(StringUtils.isPhoneValidateV2(mobile, 84), otp);
            }
        }
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
                    getBinding().otpView.showError();
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

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.OTP, TrackingAnalytic.ScreenTitle.OTP);
    }
}
