package com.namviet.vtvtravel.view.fragment.account;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentChangePassBinding;
import com.namviet.vtvtravel.databinding.FragmentChangePassBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import io.sentry.context.Context;

public class ChangePassFragment extends MainFragment implements Observer {

    private F2FragmentChangePassBinding binding;
    private AccountViewModel accountViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_change_pass, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar.myToolbar);
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
        binding.btUpdate.setOnClickListener(this);
        binding.toolBar.ivSearch.setVisibility(View.GONE);
        binding.toolBar.tvTitle.setText(getString(R.string.title_change_pass_tb));


        binding.edPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resetTitleTextView(binding.tvOldPass);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resetTitleTextView(binding.tvNewPass);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edReNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resetTitleTextView(binding.tvReTypeNewPass);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideWarning();
            }
        });
    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            try {
                KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
            } catch (Exception e) {

            }
            mActivity.onBackPressed();
        } else if (view == binding.btUpdate) {
            String oldPass = binding.edPass.getText().toString().trim();
            String newPass = binding.edNewPass.getText().toString().trim();
            String reNewPass = binding.edReNewPass.getText().toString().trim();
            String mobile = MyApplication.getInstance().getAccount().getMobile();

            if (oldPass.isEmpty()) {
                showWarning("Thông tin nhập không được để trống");
                setErrorColorForTextView(binding.tvOldPass);
            }

            if (newPass.isEmpty()) {
                showWarning("Thông tin nhập không được để trống");
                setErrorColorForTextView(binding.tvNewPass);
            }

            if (reNewPass.isEmpty()) {
                showWarning("Thông tin nhập không được để trống");
                setErrorColorForTextView(binding.tvReTypeNewPass);
                return;
            }


            if (!reNewPass.equals(newPass)) {
                showWarning("Xác nhận mật khẩu phải trùng với thông tin mật khẩu");
                setErrorColorForTextView(binding.tvReTypeNewPass);
                setErrorColorForTextView(binding.tvNewPass);
                return;
            }

            if (reNewPass.length() < 6) {
                showWarning("Mật khẩu không được nhỏ hơn 6 ký tự");
                setErrorColorForTextView(binding.tvReTypeNewPass);
                setErrorColorForTextView(binding.tvNewPass);
                return;
            }
            showDialogLoading();
            Account account = MyApplication.getInstance().getAccount();
            accountViewModel.changePassword(account.getToken(), oldPass, newPass, mobile);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        dimissDialogLoading();
        try {
            if (observable instanceof AccountViewModel) {
                if (null != o) {
                    if (o instanceof AccountResponse) {
                        AccountResponse accountResponse = (AccountResponse) o;
                        if (accountResponse.isSuccess()) {
                            ChangePassSuccessDialog changePassSuccessDialog = ChangePassSuccessDialog.newInstance(new ChangePassSuccessDialog.ClickButton() {
                                @Override
                                public void onClickButton() {

                                }
                            });
                            changePassSuccessDialog.show(getChildFragmentManager(), "");
                            hideWarning();
                        } else {
                            showMessage(accountResponse.getMessage());
                        }
                    } else if (o instanceof ErrorResponse) {
                        ErrorResponse responseError = (ErrorResponse) o;
                        if(responseError.getErrorCode().equals("USER_PASSWORD_INCORRECT")){
                            showWarning(responseError.getMessage());
                            setErrorColorForTextView(binding.tvOldPass);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showWarning(String message) {
        binding.tvWarningMessage.setText(message);
        binding.layoutWarning.setVisibility(View.VISIBLE);
    }

    public void hideWarning() {
        binding.layoutWarning.setVisibility(View.GONE);
    }

    public void resetTitleTextView(TextView textView) {
        textView.setTextColor(Color.parseColor("#707070"));
    }

    public void setErrorColorForTextView(TextView textView) {
        textView.setTextColor(Color.parseColor("#FF0000"));
    }
}
