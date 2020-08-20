package com.namviet.vtvtravel.view.fragment.f2filter;

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
import com.namviet.vtvtravel.adapter.filter.SortAdapter;
import com.namviet.vtvtravel.databinding.F2DialogSortBinding;
import com.namviet.vtvtravel.model.filter.Sort;
import com.namviet.vtvtravel.response.f2smalllocation.SortSmallLocationResponse;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SortDialog extends BaseDialogBottom {
    private F2DialogSortBinding binding;
    private SortAdapter sortAdapter;
    private DoneSort doneSort;

    @SuppressLint("ValidFragment")
    public SortDialog(SortSmallLocationResponse sortSmallLocationResponse, DoneSort doneSort) {
        this.sortSmallLocationResponse = sortSmallLocationResponse;
        this.doneSort = doneSort;
    }

    public SortDialog() {
    }

    private SortSmallLocationResponse sortSmallLocationResponse;

    @Override
    protected int getLayoutResource() {
        return R.layout.f2_dialog_sort;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_sort, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        sortAdapter = new SortAdapter(getBaseActivity(), sortList());
        binding.recycleSort.setAdapter(sortAdapter);

        sortAdapter.setClickButton(new SortAdapter.ClickButton() {
            @Override
            public void onClickItem(int position) {
//                SortDialog.this.dismiss();
                doneSort.onDoneSort();
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

    private List<SortSmallLocationResponse.Data.Item> sortList() {
        return  sortSmallLocationResponse.getData().getItems();
    }

    public interface DoneSort{
        void onDoneSort();
    }
}
