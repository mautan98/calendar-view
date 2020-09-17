package com.namviet.vtvtravel.view.fragment.f2smalllocation;

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
import com.namviet.vtvtravel.adapter.f2search.RecentAdapter;
import com.namviet.vtvtravel.databinding.F2DialogNotifyBinding;
import com.namviet.vtvtravel.databinding.F2DialogTwoButton2Binding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class CancelReviewDialog extends BaseDialogFragment2 {
    private F2DialogTwoButton2Binding binding;

    private ClickButton clickButton;



    public static CancelReviewDialog newInstance(ClickButton clickButton) {
        CancelReviewDialog oneButtonTitleDialog = new CancelReviewDialog();
        oneButtonTitleDialog.clickButton = clickButton;
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_two_button2, container, false);
        setLabel();
        binding.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton.onClickButton();
                dismiss();
            }
        });
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

    }

    public interface ClickButton {
        void onClickButton();
    }


}
