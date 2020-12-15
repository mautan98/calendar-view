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

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.vqmm.AllVoucherAdapter;
import com.namviet.vtvtravel.databinding.F2DialogSelectedVoucherBinding;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.ArrayList;
import java.util.Objects;

public class SelectedVoucherDialog extends BaseDialogBottom {
    private F2DialogSelectedVoucherBinding binding;
    private AllVoucherAdapter allVoucherAdapter;
    private RollNow rollNow;
    private ArrayList<ListVoucherResponse.Data.Voucher> vouchers = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public SelectedVoucherDialog(RollNow rollNow, ArrayList<ListVoucherResponse.Data.Voucher> vouchers) {
        this.rollNow = rollNow;
        this.vouchers = vouchers;
    }

    public SelectedVoucherDialog() {
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.f2_dialog_selected_voucher;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_selected_voucher, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        allVoucherAdapter = new AllVoucherAdapter(vouchers, getBaseActivity(), null, false);
        binding.rclContent.setAdapter(allVoucherAdapter);

        binding.btnStartRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollNow.onRollNow();
                dismiss();
            }
        });

    }

    public interface RollNow{
        void onRollNow();
    }
}
