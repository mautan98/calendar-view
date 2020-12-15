package com.namviet.vtvtravel.view.fragment.share;

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
import com.namviet.vtvtravel.adapter.f2travelvoucher.SortAdapter;
import com.namviet.vtvtravel.databinding.F2DialogShareBinding;
import com.namviet.vtvtravel.databinding.F2DialogSortVoucherBinding;
import com.namviet.vtvtravel.response.f2smalllocation.SortSmallLocationResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.SortClass;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.CategoryDialog;

import java.util.Objects;

public class ShareBottomDialog extends BaseDialogBottom {
    private F2DialogShareBinding binding;
    private DoneClickShare doneClickShare;


    @SuppressLint("ValidFragment")
    public ShareBottomDialog(DoneClickShare doneClickShare) {
        this.doneClickShare = doneClickShare;
    }


    public ShareBottomDialog(){

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.f2_dialog_share;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_share, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        binding.btnShareViaAnotherApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneClickShare.onDoneClickShare(false);
                dismiss();
            }
        });

        binding.btnShareViaVTVApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneClickShare.onDoneClickShare(true);
                dismiss();
            }
        });

    }


    public interface DoneClickShare {
        void onDoneClickShare(boolean isVTVApp);
    }
}
