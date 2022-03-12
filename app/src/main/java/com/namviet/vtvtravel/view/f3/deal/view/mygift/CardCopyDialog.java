package com.namviet.vtvtravel.view.f3.deal.view.mygift;

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
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2travelvoucher.CategoryFilterAdapter;
import com.namviet.vtvtravel.databinding.F2DialogSortVoucherBinding;
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.Objects;

public class CardCopyDialog extends BaseDialogBottom {
    private F2DialogSortVoucherBinding binding;

    @SuppressLint("ValidFragment")
    public CardCopyDialog() {
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.f3_dialog_card_copy;
    }

    @Override
    protected void init(Bundle saveInstanceState, View view) {
        try {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            view.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f3_dialog_card_copy, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
    }

}
