package com.namviet.vtvtravel.view.fragment.f2travelvoucher;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2DialogCanReceiverBinding;
import com.namviet.vtvtravel.databinding.F2DialogCopyVoucherBinding;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class CanReceiverGiftDialog extends BaseDialogFragment2 {
    private F2DialogCanReceiverBinding binding;
    private ClickButton clickButton;
    private boolean isCheck;


    public static CanReceiverGiftDialog newInstance(boolean isCheck, ClickButton clickButton) {
        CanReceiverGiftDialog oneButtonTitleDialog = new CanReceiverGiftDialog();
        oneButtonTitleDialog.isCheck = isCheck;
        oneButtonTitleDialog.clickButton = clickButton;
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_can_receiver, container, false);
        setLabel();
        setData(isCheck);
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
        binding.btnReceiverNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                clickButton.onClickButton(isCheck);
            }
        });
    }

    public interface ClickButton {
        void onClickButton(boolean isCheck);
    }

    private void setData(boolean isCheck){
        if(isCheck){
            binding.tvTitle.setText("VTVTravel tặng bạn \nmột lượt chọn quà\n mời bạn chọn voucher ưng ý");
            binding.tvReceiverNow.setText("Nhận ngay");
        }else {
            binding.tvTitle.setText("VTVTravel tặng bạn \n3 lượt quay trúng thưởng");
            binding.tvReceiverNow.setText("Quay ngay");
        }
    }
}
