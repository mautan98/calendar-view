package com.namviet.vtvtravel.view.fragment.f2comment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2DialogCaptchaBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

import java.util.Random;

public class CaptchaDialog extends BaseDialogFragment2 {
    private F2DialogCaptchaBinding binding;
    private ClickButton clickButton;


    public static CaptchaDialog newInstance(ClickButton clickButton) {
        CaptchaDialog oneButtonTitleDialog = new CaptchaDialog();
        oneButtonTitleDialog.clickButton = clickButton;
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_captcha, container, false);
        try {
            setLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        binding.tvDescription.setText(genCaptchaCode());
        binding.tvLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(binding.edtCaptcha.getText().toString().equals(binding.tvDescription.getText().toString())){
                        clickButton.onClickButton();
                        dismiss();
                    }else {
                        Toast.makeText(getContext(), "Mã không chính xác", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        binding.btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface ClickButton {
        void onClickButton();
    }

    private String genCaptchaCode() {
        int min = 111111;
        int max = 999999;
        int random = new Random().nextInt((max - min) + 1) + min;
        return String.valueOf(random);
    }


}
