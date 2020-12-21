package com.namviet.vtvtravel.view.fragment.account;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentChangePassBinding;
import com.namviet.vtvtravel.databinding.FragmentChangePassBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

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
                showMessage(getString(R.string.pass_empty));
            } else if (newPass.isEmpty()) {
                showMessage(getString(R.string.pass_empty));
            } else if (!newPass.equals(reNewPass)) {
                showMessage(getString(R.string.re_pass_invalid));
            } else {
                showDialogLoading();
                Account account = MyApplication.getInstance().getAccount();
                accountViewModel.changePassword(account.getToken(), oldPass, newPass, mobile);
            }

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
                        showMessage(getString(R.string.change_pass_success));
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
}
