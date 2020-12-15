package com.namviet.vtvtravel.view.fragment.f2offline;

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
import com.namviet.vtvtravel.databinding.F2DialogOneButtonTitleImageBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class OneButtonTitleImageDialog extends BaseDialogFragment2 {
    private F2DialogOneButtonTitleImageBinding binding;
    private ClickButton clickButton;


    public static OneButtonTitleImageDialog newInstance(ClickButton clickButton) {
        OneButtonTitleImageDialog oneButtonTitleDialog = new OneButtonTitleImageDialog();
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_one_button_title_image, container, false);
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
//                clickButton.onClickButton();
                dismiss();
            }
        });
    }

    public interface ClickButton {
        void onClickButton();
    }
}
