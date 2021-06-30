package com.namviet.vtvtravel.view.fragment.f2service;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.DialogRegisterFailBinding;
import com.namviet.vtvtravel.view.dialog.BaseDialogFragment;


public class RegisterFailDialog extends BaseDialogFragment {
    private DialogRegisterFailBinding binding;
    private String message;
    private boolean isNotEnoughMoney;

    public static RegisterFailDialog newInstance(String message, boolean isNotEnoughMoney) {
        RegisterFailDialog loadingDialog = new RegisterFailDialog();
        loadingDialog.message = message;
        loadingDialog.isNotEnoughMoney = isNotEnoughMoney;
        return loadingDialog;
    }

    public static RegisterFailDialog newInstance() {
        RegisterFailDialog loadingDialog = new RegisterFailDialog();
        return loadingDialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_register_fail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected void initView(View v) {
        updateView();
    }

    protected void updateView() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        try {
            if (isNotEnoughMoney){
                if (message != null && message.length() > 0){
                    binding.txt2.setText(message);
                    binding.txt3.setVisibility(View.VISIBLE);
                } else {

                }
            }else {
                if (message != null && message.length() > 0){
                    binding.txt2.setText(message);
                } else {
                    binding.txt2.setText("Có lỗi đã xảy ra");
                }
                binding.txt3.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//    }
}
