package com.namviet.vtvtravel.view.fragment.f2account;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.newhome.NewHomeAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentHuntDealBinding;
import com.namviet.vtvtravel.databinding.F2FragmentRegisterBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.StringUtils;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

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
        handleValidateSuccess(getBinding().edtName, getBinding().linearName);
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

                phone = getBinding().edtPhone.getText().toString();
                String name = getBinding().edtName.getText().toString();

                if (phone.isEmpty()) {
                    handleValidateFail(getBinding().edtPhone, getBinding().linearUsername, getString(R.string.phone_empty_v2));
                } else if (name.isEmpty()) {
                    handleValidateFail(getBinding().edtName, getBinding().linearName, getString(R.string.name_empty));
                } else if (name.length() > 60) {
                    handleValidateFail(getBinding().edtName, getBinding().linearName, getString(R.string.name_invalid));
                } else if (ValidateUtils.isString(name)) {
                    handleValidateFail(getBinding().edtName, getBinding().linearName, getString(R.string.special_charactor));
                } else if (!ValidateUtils.isValidPhoneNumber(phone)) {
                    handleValidateFail(getBinding().edtPhone, getBinding().linearUsername, getString(R.string.phone_invalid));
                } else {
                    if (phone.substring(0, 3).equals("840")) {
                        phone = phone.replaceFirst("0", "");
                    }
                    showLoading();
                    accountViewModel.register(StringUtils.isPhoneValidateV2(phone, 84), name);
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
                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

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

    private void handleValidateFail(EditText editText, LinearLayout linearLayout, String error) {
        ((LoginAndRegisterActivityNew) mActivity).showWarning(error);
        linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login_fail));
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
}
