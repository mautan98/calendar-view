package com.namviet.vtvtravel.view.f2.call;

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
import com.namviet.vtvtravel.databinding.F2DialogEnterPhoneToTestBinding;
import com.namviet.vtvtravel.databinding.F2DialogNotifyBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class EnterPhoneToTestDialog extends BaseDialogFragment2 {
    private F2DialogEnterPhoneToTestBinding binding;
    private String description;
    private ClickButton clickButton;



    public static EnterPhoneToTestDialog newInstance(String description, ClickButton clickButton) {
        EnterPhoneToTestDialog oneButtonTitleDialog = new EnterPhoneToTestDialog();
        oneButtonTitleDialog.description =description;
        oneButtonTitleDialog.clickButton = clickButton;
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_enter_phone_to_test, container, false);
        setLabel();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        binding.tvLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton.onClickButton(binding.tvDescription.getText().toString().trim());
                dismiss();
            }
        });
    }

    public interface ClickButton {
        void onClickButton(String phone);
    }


}
