package com.namviet.vtvtravel.view.fragment.f2travelvoucher;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2DialogCanReceiverBinding;
import com.namviet.vtvtravel.databinding.F2DialogNotifyBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class NotifiDialog extends BaseDialogFragment2 {
    private F2DialogNotifyBinding binding;
    private String title;
    private String description;
    private String label;



    public static NotifiDialog newInstance(String title, String description, String label) {
        NotifiDialog oneButtonTitleDialog = new NotifiDialog();
        oneButtonTitleDialog.label = label;
        oneButtonTitleDialog.title = title;
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
                case "VOUCHER_ENDED":
                    binding.tvDescription.setText("Voucher bạn chọn đã hết hạn, \nmời bạn chọn Voucher khác!");
                    binding.tvLabel.setText("Chọn lại");
                    break;
                case "VOUCHER_OUT_OF_STORAGE":
                    binding.tvDescription.setText("Voucher bạn chọn đã hết, mời \nbạn chọn Voucher khác!");
                    binding.tvLabel.setText("Đồng ý");
                    break;
                case "VOUCHER_RANK_OVER":
                    binding.tvDescription.setText("Voucher không áp dụng cho Hội \nviên này, mời bạn chọn voucher khác!");
                    binding.tvLabel.setText("Chọn lại");
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
