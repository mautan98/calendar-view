package com.namviet.vtvtravel.view.fragment.f2travelvoucher;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2travelvoucher.CategoryFilterAdapter;
import com.namviet.vtvtravel.adapter.filter.SortAdapter;
import com.namviet.vtvtravel.databinding.F2DialogSortVoucherBinding;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SortSmallLocationResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.Objects;

public class CategoryDialog extends BaseDialogBottom {
    private F2DialogSortVoucherBinding binding;
    private CategoryFilterAdapter categoryFilterAdapter;
    private DoneSort doneSort;
    private CategoryVoucherResponse categoryVoucherResponse;
    private String title;

    @SuppressLint("ValidFragment")
    public CategoryDialog(CategoryVoucherResponse categoryVoucherResponse, DoneSort doneSort, String title) {
        this.categoryVoucherResponse = categoryVoucherResponse;
        this.doneSort = doneSort;
        this.title = title;
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
        categoryFilterAdapter = new CategoryFilterAdapter(categoryVoucherResponse.getData(), getBaseActivity(), new CategoryFilterAdapter.ClickItem() {
            @Override
            public void onClickItem(CategoryVoucherResponse.Category category) {
                doneSort.onDoneSort(category);
                dismiss();
            }
        });
        binding.recycleSort.setAdapter(categoryFilterAdapter);

    }

    public interface DoneSort{
        void onDoneSort(CategoryVoucherResponse.Category category);
    }
}
