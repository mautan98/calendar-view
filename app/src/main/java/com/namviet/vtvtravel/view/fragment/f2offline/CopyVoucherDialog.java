package com.namviet.vtvtravel.view.fragment.f2offline;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2DialogCopyVoucherBinding;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2;

public class CopyVoucherDialog extends BaseDialogFragment2 {
    private F2DialogCopyVoucherBinding binding;
    private ClickButton clickButton;
    private ListVoucherResponse.Data.Voucher voucher;


    public static CopyVoucherDialog newInstance(ListVoucherResponse.Data.Voucher voucher, ClickButton clickButton) {
        CopyVoucherDialog oneButtonTitleDialog = new CopyVoucherDialog();
        oneButtonTitleDialog.voucher = voucher;
        return oneButtonTitleDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_copy_voucher, container, false);
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
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                clickButton.onClickButton();
                dismiss();
            }
        });

        binding.tvCode.setText(voucher.getCode());

        binding.tvShortLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(voucher.getLinkVoucher()));
                    getContext().startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        binding.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", voucher.getCode() + "");
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getContext(), "Đã sao chép mã vào bộ nhớ tạm", Toast.LENGTH_SHORT).show();

                    try {
                        TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault("CopyVoucher", "Popup sao chép voucher").setScreen_class(this.getClass().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            binding.tvTimeLeft.setText("Hạn sử dụng đến ngày " + DateUtltils.timeToString(Long.valueOf(voucher.getEndAt()) / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ClickButton {
        void onClickButton();
    }
}
