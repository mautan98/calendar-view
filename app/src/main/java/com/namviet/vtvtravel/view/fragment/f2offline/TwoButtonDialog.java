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
import com.namviet.vtvtravel.databinding.F2DialogTwoButtonBinding;
import com.namviet.vtvtravel.view.dialog.BaseDialogFragment;

public class TwoButtonDialog extends BaseDialogFragment {
    private F2DialogTwoButtonBinding binding;
    public String title;
    public String button;
    public String button2;
    private ClickButton clickButton;

    public static TwoButtonDialog newInstance(String title, String button, String button2, ClickButton clickButton) {
        TwoButtonDialog offlineDialog = new TwoButtonDialog();
        offlineDialog.title = title;
        offlineDialog.button = button;
        offlineDialog.button2 = button2;
        offlineDialog.clickButton = clickButton;
        return offlineDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_two_button, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClick();
        initData();
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

    private void initData(){
        binding.tvTitle.setText(title);
        binding.btnInvite.setText(button);
        binding.btnCancel.setText(button2);
    }

    private void setClick(){
        binding.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton.onClickButton1();
                dismiss();
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton.onClickButton2();
                dismiss();
            }
        });
    }

    public interface ClickButton{
        void onClickButton1();
        void onClickButton2();
    }
}
