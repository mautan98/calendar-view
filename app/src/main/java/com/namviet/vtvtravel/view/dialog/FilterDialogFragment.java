package com.namviet.vtvtravel.view.dialog;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.CategoryFilterAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.DialogFilterBinding;
import com.namviet.vtvtravel.listener.FilterSelectListener;
import com.namviet.vtvtravel.response.FilterData;

import java.util.ArrayList;

public class FilterDialogFragment extends BaseDialogFragment {
    private DialogFilterBinding binding;
    private CategoryFilterAdapter categoryFilterAdapter;
    private ArrayList<FilterData> listFilterDatas;
    private FilterSelectListener filterSelectListener;

    public void setFilterSelectListener(FilterSelectListener filterSelectListener) {
        this.filterSelectListener = filterSelectListener;
    }

    public static FilterDialogFragment newInstance(ArrayList<FilterData> filterDatas) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.IntentKey.KEY_DIALOG, filterDatas);
        FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
        filterDialogFragment.setArguments(bundle);
        return filterDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            listFilterDatas = getArguments().getParcelableArrayList(Constants.IntentKey.KEY_DIALOG);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_filter, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected void initView(View v) {
        binding.tvCancelFilter.setOnClickListener(this);
        binding.ivClose.setOnClickListener(this);
        binding.btApply.setOnClickListener(this);
        binding.rvFilter.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryFilterAdapter = new CategoryFilterAdapter(getActivity(), listFilterDatas);
        binding.rvFilter.setAdapter(categoryFilterAdapter);
        updateView();
    }

    protected void updateView() {


    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.tvCancelFilter) {
            if (null != categoryFilterAdapter) {
                categoryFilterAdapter.cancelFilter();
            }
        } else if (view == binding.ivClose) {
            dismiss();
        } else if (view == binding.btApply) {
            if (null != categoryFilterAdapter) {
                ArrayList<FilterData> filterData = categoryFilterAdapter.getListFilterData();
                if (null != filterSelectListener) {
                    filterSelectListener.onSelect(filterData);
                }
            }
            dismiss();
        }
    }
}
