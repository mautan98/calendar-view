package com.namviet.vtvtravel.view.fragment.f2chat;

import android.annotation.SuppressLint;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2LayoutBottomSheetUserOptionBinding;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.Objects;

public class ChangeAvatarDialog extends BaseDialogBottom implements View.OnClickListener{
    private F2LayoutBottomSheetUserOptionBinding binding;
    ClickOption option;

    @SuppressLint("ValidFragment")
    public ChangeAvatarDialog(ClickOption option) {
        this.option = option;
    }
    public ChangeAvatarDialog() {}

    @Override
    protected int getLayoutResource() {
        return R.layout.f2_layout_bottom_sheet_user_option;
    }

    @Override
    protected void init(Bundle saveInstanceState, View view) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.setBackgroundColor(Color.TRANSPARENT);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((View) Objects.requireNonNull(getView()).getParent()).setBackgroundColor(Color.TRANSPARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getDialog().getWindow() != null) {
            this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_layout_bottom_sheet_user_option, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        binding.txtCamera.setOnClickListener(this);
        binding.txtGallery.setOnClickListener(this);
        binding.txtViewImage.setOnClickListener(this);
        binding.txtCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtGallery:
                option.clickGallery();
                dismiss();
                break;
            case R.id.txtCamera:
                option.clickCamera();
                dismiss();
                break;
            case R.id.txtViewImage:
                option.clickView();
                dismiss();
                break;
            case R.id.txtCancel:
                dismiss();
                break;
        }
    }
    public interface ClickOption {
        void clickGallery();
        void clickCamera();
        void clickView();
    }
}
