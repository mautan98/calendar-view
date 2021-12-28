package com.namviet.vtvtravel.view.fragment.f2chat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2LayoutBottomSheetCall1039Binding;
import com.namviet.vtvtravel.databinding.F2LayoutBottomSheetQuestionChangeThemeBinding;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.Objects;

public class QuestionCall1939Dialog extends BaseDialogBottom{
    private F2LayoutBottomSheetCall1039Binding binding;
    ClickOption option;

    @SuppressLint("ValidFragment")
    public QuestionCall1939Dialog(ClickOption option) {
        this.option = option;
    }
    public QuestionCall1939Dialog() {}

    @Override
    protected int getLayoutResource() {
        return R.layout.f2_layout_bottom_sheet_call_1039;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_layout_bottom_sheet_call_1039, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        binding.tvChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option.clickChangeTheme();
                dismiss();
            }
        });


        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    public interface ClickOption {
        void clickChangeTheme();
    }
}
