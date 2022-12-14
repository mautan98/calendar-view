package com.namviet.vtvtravel.view.fragment.f2account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentRegisterBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnChangeTab;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.ultils.StringUtils;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RegisterF2Fragment extends BaseFragment<F2FragmentRegisterBinding> implements Observer {
    private AccountViewModel accountViewModel;
    private final String TYPE_OTP = "REGISTER_OTP";
    private String phone;

    public RegisterF2Fragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_register;
    }

    @Override
    public void initView() {
        accountViewModel = new AccountViewModel();
        getBinding().setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);

        handleValidateSuccess(getBinding().edtPhone, getBinding().linearUsername);
        handleValidateSuccess(getBinding().edtName, getBinding().layoutName);

    }

    @Override
    public void initData() {
        getBinding().checkboxTravelVip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setCheckedChange(b);
            }
        });
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetError();
                phone = getBinding().edtPhone.getText().toString();
                String name = getBinding().edtName.getText().toString();
                ((LoginAndRegisterActivityNew)mActivity).fullName = name;

                if (phone.isEmpty()) {
                    handleValidateFail(getBinding().edtPhone, getBinding().linearUsername, getString(R.string.phone_empty_v2), getBinding().tvPhoneError);
                } else if (name.isEmpty()) {
                    handleValidateFail(getBinding().edtName, getBinding().layoutName, getString(R.string.name_empty), getBinding().tvNameError);
                } else if (name.trim().length() < 3) {
                    handleValidateFail(getBinding().edtName, getBinding().layoutName, "????? d??i t??n nh??? h??n 3 k?? t???", getBinding().tvNameError);
                }else if (name.length() > 60) {
                    handleValidateFail(getBinding().edtName, getBinding().layoutName, getString(R.string.name_invalid), getBinding().tvNameError);
                } else if (ValidateUtils.isString(name)) {
                    handleValidateFail(getBinding().edtName, getBinding().layoutName, getString(R.string.special_charactor), getBinding().tvNameError);
                } else if (!ValidateUtils.isValidPhoneNumberNew(phone)) {
                    handleValidateFail(getBinding().edtPhone, getBinding().linearUsername, getString(R.string.phone_invalid), getBinding().tvPhoneError);
                } else {
                    if (phone.substring(0, 3).equals("840")) {
                        phone = phone.replaceFirst("0", "");
                    }
                    showLoading();
                    accountViewModel.register(StringUtils.isPhoneValidateV2(phone, 84), name, ((LoginAndRegisterActivityNew)mActivity).packageCode);

                    try {
                        TrackingAnalytic.postEvent(TrackingAnalytic.SIGN_UP, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.REGISTER, TrackingAnalytic.ScreenTitle.REGISTER).setScreen_class(this.getClass().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        getBinding().btnDetailDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new DetailDealFragment());
            }
        });

        getBinding().tvRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new RulesFragment());
            }
        });

        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

        getBinding().btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OnChangeTab(0));
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
        hideLoading();
        try {
        if (observable instanceof AccountViewModel && null != o) {
            if (o instanceof AccountResponse) {
                AccountResponse accountResponse = (AccountResponse) o;
                if (accountResponse.isSuccess()) {
                    MyApplication.getInstance().setAccount(accountResponse.getData());
                    OtpF2Fragment otpF2Fragment = new OtpF2Fragment();
                    otpF2Fragment.setTypeOTP(Constants.IntentKey.TYPE_OTP_REGISTER);
                    otpF2Fragment.setMobile(phone);
                    addFragment(otpF2Fragment);
                }
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
                    getBinding().tvRegisterFail.setVisibility(View.VISIBLE);
                    getBinding().tvRegisterFail.setText(responseError.getMessage());
                } catch (Exception e) {


                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCheckedChange(boolean b) {
        getBinding().checkboxTravelVip.setChecked(b);
        if (b) {
            ((LoginAndRegisterActivityNew) mActivity).packageCode = Constants.TypePackage.TRAVEL_VIP;
        } else {
            ((LoginAndRegisterActivityNew) mActivity).packageCode = Constants.TypePackage.FRIEND_TRAVEL_FREE;
        }
    }

    private void handleValidateFail(EditText editText, View linearLayout, String error , TextView tvError) {
        linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login_fail));
        tvError.setText(error);
        tvError.setVisibility(View.VISIBLE);
        editText.requestFocus();
    }

    @SuppressLint("CheckResult")
    private void handleValidateSuccess(EditText editText, View linearLayout) {
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
        setDataScreen(TrackingAnalytic.ScreenCode.REGISTER, TrackingAnalytic.ScreenTitle.REGISTER);
    }

    private void resetError(){
        getBinding().tvPhoneError.setVisibility(View.INVISIBLE);
        getBinding().tvNameError.setVisibility(View.INVISIBLE);
        getBinding().tvRegisterFail.setVisibility(View.INVISIBLE);
        getBinding().tvPhoneError.setText("");
        getBinding().tvNameError.setText("");
        getBinding().tvRegisterFail.setText("");
    }


}
