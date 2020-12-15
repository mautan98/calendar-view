package com.namviet.vtvtravel.view.fragment.f2webview;

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

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2DialogVoucherDetailBinding;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.Objects;

public class DetailVoucherDialog extends BaseDialogBottom {
    private F2DialogVoucherDetailBinding binding;
    private ListVoucherResponse.Data.Voucher voucher;

    @SuppressLint("ValidFragment")
    public DetailVoucherDialog(ListVoucherResponse.Data.Voucher voucher) {
        this.voucher = voucher;
    }

    public DetailVoucherDialog() {
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.f2_dialog_voucher_detail;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_voucher_detail, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        try {
            Glide.with(getBaseActivity()).load(voucher.getAvatarUri()).into(binding.imgAvatar);
            binding.tvName.setText(voucher.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            binding.tvTimeLeft.setText("Hạn đến " + DateUtltils.timeToString(Long.valueOf(voucher.getEndAt()) / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            binding.webView.loadDataWithBaseURL("", voucher.getContent(), "text/html", "UTF-8", null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
