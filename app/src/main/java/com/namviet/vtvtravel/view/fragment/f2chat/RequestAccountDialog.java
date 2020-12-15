package com.namviet.vtvtravel.view.fragment.f2chat;

import android.app.Dialog;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2DialogRequestAccountBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class RequestAccountDialog extends BaseDialogFragment2 {
    private F2DialogRequestAccountBinding binding;
    private RequestAccountDialog.ClickButton clickButton;

    public static RequestAccountDialog newInstance(RequestAccountDialog.ClickButton clickButton) {
        RequestAccountDialog requestAccountDialog = new RequestAccountDialog();
        requestAccountDialog.clickButton = clickButton;
        return requestAccountDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_request_account, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

    }

    private void initView() {
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton.clickExit();
                dismiss();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton.clickLogin();
                dismiss();
            }
        });
    }

    public interface ClickButton {
        void clickLogin();
        void clickExit();

    }
}
