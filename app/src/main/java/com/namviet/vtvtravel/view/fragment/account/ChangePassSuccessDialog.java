package com.namviet.vtvtravel.view.fragment.account;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2DialogChangePassSuccessBinding;
import com.namviet.vtvtravel.databinding.F2DialogOtpSuccessBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class ChangePassSuccessDialog extends BaseDialogFragment2 {

    private F2DialogChangePassSuccessBinding binding;
    private ChangePassSuccessDialog.ClickButton clickButton;

    public static ChangePassSuccessDialog newInstance(ClickButton clickButton) {
        ChangePassSuccessDialog otpSuccessDialog = new ChangePassSuccessDialog();
        otpSuccessDialog.clickButton = clickButton;
        return otpSuccessDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_change_pass_success, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLabel();
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

    private void setLabel() {
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton.onClickButton();
                dismiss();
            }
        });
    }

    public interface ClickButton {
        void onClickButton();
    }
}
