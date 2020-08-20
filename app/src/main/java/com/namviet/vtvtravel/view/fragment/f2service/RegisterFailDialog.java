package com.namviet.vtvtravel.view.fragment.f2service;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.DialogTypeRegisterBinding;
import com.namviet.vtvtravel.view.dialog.BaseDialogFragment;


public class RegisterFailDialog extends BaseDialogFragment {
    private DialogTypeRegisterBinding binding;

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


    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//    }
}
