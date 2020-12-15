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
import com.namviet.vtvtravel.databinding.F2DialogOneButtonTitleBinding;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment;

public class OneButtonTitleDialog extends BaseDialogFragment {
    private F2DialogOneButtonTitleBinding binding;
    public String title;
    public String button;
    private String description;
    private ClickButton clickButton;


    public static OneButtonTitleDialog newInstance(String title, String description, String button, ClickButton clickButton) {
        OneButtonTitleDialog oneButtonTitleDialog = new OneButtonTitleDialog();
        oneButtonTitleDialog.title = title;
        oneButtonTitleDialog.button = button;
        oneButtonTitleDialog.clickButton = clickButton;
        oneButtonTitleDialog.description = description;
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_one_button_title, container, false);
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
        binding.tvTitle.setText(title);
        binding.tvDescription.setText(description);
        binding.tvLabel.setText(button);

        binding.tvLabel.setOnClickListener(new View.OnClickListener() {
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
