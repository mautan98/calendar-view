package com.namviet.vtvtravel.view.fragment.f2webview;

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
import com.namviet.vtvtravel.databinding.F2DialogVoucherReceiverBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class ReceiverVoucherLuckyWheelDialog extends BaseDialogFragment2 {
    private String title;

    private F2DialogVoucherReceiverBinding binding;
    private ClickButton clickButton;


    public static ReceiverVoucherLuckyWheelDialog newInstance(ClickButton clickButton,String title) {
        ReceiverVoucherLuckyWheelDialog oneButtonTitleDialog = new ReceiverVoucherLuckyWheelDialog();
        oneButtonTitleDialog.clickButton = clickButton;
        oneButtonTitleDialog.title = title;
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_voucher_receiver, container, false);
        setLabel();
        setData();
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
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        binding.btnReceiverNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton.onClickButton();
                dismiss();
            }
        });

    }

    private void setData(){
        binding.tvTitle.setText(title);


    }

    public interface ClickButton {
        void onClickButton();
    }
}
