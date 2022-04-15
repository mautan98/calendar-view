package com.namviet.vtvtravel.view.fragment.f2travelvoucher;

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

public class CategoryDialog extends BaseDialogBottom {
    private F2DialogSortVoucherBinding binding;

    @SuppressLint("ValidFragment")
    public CategoryDialog(CategoryVoucherResponse categoryVoucherResponse, DoneSort doneSort, String title, int type) {
        this.categoryVoucherResponse = categoryVoucherResponse;
        this.doneSort = doneSort;
        this.title = title;
        this.type = type;
        categoryFilterAdapter = new CategoryFilterAdapter(categoryVoucherResponse.getData(), getBaseActivity(), new CategoryFilterAdapter.ClickItem() {
            @Override
            public void onClickItem(CategoryVoucherResponse.Category category) {
                doneSort.onDoneSort(category);
                dismiss();
            }
        });

    }

    private CategoryFilterAdapter categoryFilterAdapter;

    public CategoryFilterAdapter getCategoryFilterAdapter() {
        return categoryFilterAdapter;
    }

    private DoneSort doneSort;
    private CategoryVoucherResponse categoryVoucherResponse;
    private String title;
    private int type = Type.VOUCHER_TYPE;

    public class Type {
        public static final int VOUCHER_TYPE = 0;
        public static final int LUCKY_WHEEL_TYPE = 1;
    }

    public void setDoneSort(DoneSort doneSort) {
        this.doneSort = doneSort;
    }

    public CategoryDialog() {
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.f2_dialog_sort_voucher;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_sort_voucher, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        binding.tvTitle.setText(title);
        binding.recycleSort.setAdapter(categoryFilterAdapter);

        if(type == Type.LUCKY_WHEEL_TYPE) {
            binding.tvTitle.setBackground(ContextCompat.getDrawable(getBaseActivity(), R.drawable.f2_bg_title_sort_lucky_wheel));
        }
    }

    public interface DoneSort {
        void onDoneSort(CategoryVoucherResponse.Category category);
    }
}
