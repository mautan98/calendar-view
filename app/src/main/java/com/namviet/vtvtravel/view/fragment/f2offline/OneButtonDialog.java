package com.namviet.vtvtravel.view.fragment.f2offline;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2DialogOneButtonBinding;
import com.namviet.vtvtravel.view.dialog.BaseDialogFragment;

public class OneButtonDialog extends BaseDialogFragment {
    private F2DialogOneButtonBinding binding;
    public String title;
    public String button;
    public String name;
    private ClickButton clickButton;


    public static OneButtonDialog newInstance(String title, String button, ClickButton clickButton) {
        OneButtonDialog offlineDialog = new OneButtonDialog();
        offlineDialog.title = title;
        offlineDialog.button = button;
        offlineDialog.clickButton = clickButton;
        return offlineDialog;
    }

    public static OneButtonDialog newInstance(String name, String title, String button, ClickButton clickButton) {
        OneButtonDialog offlineDialog = new OneButtonDialog();
        offlineDialog.title = title;
        offlineDialog.button = button;
        offlineDialog.name = name;
        offlineDialog.clickButton = clickButton;
        return offlineDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_one_button, container, false);
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
        binding.tvLabel.setText(button);
        try {
            if (name != null && !"".equals(name)) {
                binding.tvName.setText(name);
                binding.tvName.setVisibility(View.VISIBLE);
            } else {
                binding.tvName.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
