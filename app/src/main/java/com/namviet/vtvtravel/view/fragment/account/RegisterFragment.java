package com.namviet.vtvtravel.view.fragment.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.KeyboardUtils;
import com.baseapp.utils.MyFragment;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentRegisterBinding;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.StringUtils;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import static com.facebook.FacebookSdk.getApplicationContext;


public class RegisterFragment extends MainFragment implements Observer {

    private FragmentRegisterBinding binding;
    private AccountViewModel accountViewModel;
    private String mobile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
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

        accountViewModel = new AccountViewModel();
        binding.setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);

        binding.toolBar.ivBack.setOnClickListener(this);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        binding.btNext.setOnClickListener(this);
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
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.IntentKey.KEY_TYPE, Constants.TypeScreen.OTP_REGISTER);
                        bundle.putString(OtpFragment.KEY_MOBILE, mobile);
                        mActivity.setBundle(bundle);
                        mActivity.switchFragment(SlideMenu.MenuType.OTP_SCREEN);
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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.btNext) {
            mobile = binding.edPhone.getText().toString().trim();
            String name = binding.edName.getText().toString().trim();
            if (mobile.isEmpty()) {
                showMessage(getString(R.string.phone_empty_v2));
            } else if (name.isEmpty()) {
                showMessage(getString(R.string.name_empty));
            } else if (name.length() > 60) {
                showMessage(getString(R.string.name_invalid));
            } else if (ValidateUtils.isString(name)) {
                showMessage(getString(R.string.special_charactor));
            } else if (!ValidateUtils.isValidPhoneNumber(mobile)) {
                showMessage(getString(R.string.phone_invalid));
            } else {
                showDialogLoading();
//                KeyboardUtils.hideKeyboard1(mActivity);
                if (mobile.substring(0, 3).equals("840")) {
                    mobile = mobile.replaceFirst("0", "");
                }
                accountViewModel.register(StringUtils.isPhoneValidateV2(mobile, 84), name);
            }


        } else if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
    }
}
