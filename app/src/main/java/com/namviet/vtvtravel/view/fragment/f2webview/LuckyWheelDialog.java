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
import com.namviet.vtvtravel.databinding.F2DialogLuckyWheelBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class LuckyWheelDialog extends BaseDialogFragment2 {
    private int type;
    private String title;

    private F2DialogLuckyWheelBinding binding;
    private ClickButton clickButton;


    public static LuckyWheelDialog newInstance(ClickButton clickButton, int type, String title) {
        LuckyWheelDialog oneButtonTitleDialog = new LuckyWheelDialog();
        oneButtonTitleDialog.clickButton = clickButton;
        oneButtonTitleDialog.type = type;
        oneButtonTitleDialog.title = title;
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_lucky_wheel, container, false);
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
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.btnRegVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                clickButton.onClickButton();
            }
        });
    }

    private void setData(){
        binding.tvMessage.setText(title);
        switch (type){
            case Type.BEST_WISH_TYPE:
                binding.imgBestWish.setVisibility(View.VISIBLE);
                binding.imgAddTurn.setVisibility(View.GONE);
                binding.imgNotEnoughTurn.setVisibility(View.GONE);
                binding.imgSad.setVisibility(View.GONE);
                binding.imgVip.setVisibility(View.GONE);

                binding.btnConfirm.setVisibility(View.GONE);
                binding.btnReceiverNow.setVisibility(View.GONE);
                binding.btnRegVip.setVisibility(View.GONE);

                binding.btnClose.setVisibility(View.VISIBLE);
                break;

            case Type.SAD_TYPE:
                binding.imgBestWish.setVisibility(View.GONE);
                binding.imgAddTurn.setVisibility(View.GONE);
                binding.imgNotEnoughTurn.setVisibility(View.GONE);
                binding.imgSad.setVisibility(View.VISIBLE);
                binding.imgVip.setVisibility(View.GONE);

                binding.btnConfirm.setVisibility(View.VISIBLE);
                binding.btnReceiverNow.setVisibility(View.GONE);
                binding.btnRegVip.setVisibility(View.GONE);

                binding.btnClose.setVisibility(View.GONE);
                break;


            case Type.NOT_ENOUGH_TYPE:
                binding.imgBestWish.setVisibility(View.GONE);
                binding.imgAddTurn.setVisibility(View.GONE);
                binding.imgNotEnoughTurn.setVisibility(View.VISIBLE);
                binding.imgSad.setVisibility(View.GONE);
                binding.imgVip.setVisibility(View.GONE);

                binding.btnConfirm.setVisibility(View.GONE);
                binding.btnReceiverNow.setVisibility(View.GONE);
                binding.btnRegVip.setVisibility(View.GONE);

                binding.btnClose.setVisibility(View.VISIBLE);
                break;


            case Type.ADD_TYPE:
                binding.imgBestWish.setVisibility(View.GONE);
                binding.imgAddTurn.setVisibility(View.VISIBLE);
                binding.imgNotEnoughTurn.setVisibility(View.GONE);
                binding.imgSad.setVisibility(View.GONE);
                binding.imgVip.setVisibility(View.GONE);

                binding.btnConfirm.setVisibility(View.VISIBLE);
                binding.btnReceiverNow.setVisibility(View.GONE);
                binding.btnRegVip.setVisibility(View.GONE);

                binding.btnClose.setVisibility(View.GONE);
                break;


            case Type.VIP_TYPE:
                binding.imgBestWish.setVisibility(View.GONE);
                binding.imgAddTurn.setVisibility(View.GONE);
                binding.imgNotEnoughTurn.setVisibility(View.GONE);
                binding.imgSad.setVisibility(View.GONE);
                binding.imgVip.setVisibility(View.VISIBLE);

                binding.btnConfirm.setVisibility(View.GONE);
                binding.btnReceiverNow.setVisibility(View.GONE);
                binding.btnRegVip.setVisibility(View.VISIBLE);

                binding.btnClose.setVisibility(View.VISIBLE);
                break;
        }
    }

    public interface ClickButton {
        void onClickButton();
    }


    public static class Type{
        public static final int VIP_TYPE = 1;
        public static final int NOT_ENOUGH_TYPE = 2;
        public static final int BEST_WISH_TYPE = 3;
        public static final int SAD_TYPE = 4;
        public static final int ADD_TYPE = 5;
    }
}
