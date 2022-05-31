package com.namviet.vtvtravel.view.fragment.f2travelvoucher;

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
import com.namviet.vtvtravel.databinding.F2DialogNotifyBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class AlreadyReceiverDialog extends BaseDialogFragment2 {
    private F2DialogNotifyBinding binding;

    private String description;
    private String dialogTitle;
    private String labelButton;

    public void setLabelButton(String labelButton) {
        this.labelButton = labelButton;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }



    public static AlreadyReceiverDialog newInstance(String description) {
        AlreadyReceiverDialog oneButtonTitleDialog = new AlreadyReceiverDialog();
        oneButtonTitleDialog.description =description;
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_notify, container, false);
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

        setTitle(description);

        binding.tvLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public interface ClickButton {
        void onClickButton();
    }

    private void setTitle(String code){
        try {
            switch (code){
                case "ALREADY_RECEIVE_VOUCHER":
                    binding.tvDescription.setText("Bạn đã sở hữu phần quà này.");
                    binding.tvLabel.setText("Đóng");
                    break;
                default:
                    binding.tvTitle.setText(dialogTitle);
                    binding.tvDescription.setText(description);
                    binding.tvLabel.setText(labelButton);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
