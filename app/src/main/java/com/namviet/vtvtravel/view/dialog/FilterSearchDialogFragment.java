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
import com.namviet.vtvtravel.adapter.CategoryFilterSearchAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.DialogFilterSearchBinding;
import com.namviet.vtvtravel.listener.FilterSelectListener;
import com.namviet.vtvtravel.model.Filter;
import com.namviet.vtvtravel.response.FilterData;
import com.namviet.vtvtravel.response.FilterSearchData;

import java.util.ArrayList;

public class FilterSearchDialogFragment extends BaseDialogFragment implements FilterSelectListener {
    private DialogFilterSearchBinding binding;
    private CategoryFilterSearchAdapter categoryFilterAdapter;
    private ArrayList<FilterSearchData> listFilterDatas;
    private FilterSelectListener filterSelectListener;

    public void setFilterSelectListener(FilterSelectListener filterSelectListener) {
        this.filterSelectListener = filterSelectListener;
    }

    public static FilterSearchDialogFragment newInstance(ArrayList<FilterSearchData> filterDatas) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.IntentKey.KEY_DIALOG, filterDatas);
        FilterSearchDialogFragment filterDialogFragment = new FilterSearchDialogFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_filter_search, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected void initView(View v) {
        binding.ivClose.setOnClickListener(this);
        binding.rvFilter.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryFilterAdapter = new CategoryFilterSearchAdapter(getActivity(), listFilterDatas);
        binding.rvFilter.setAdapter(categoryFilterAdapter);
        categoryFilterAdapter.setFilterSelectListener(this);
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
        if (view == binding.ivClose) {
            dismiss();
        }
    }

    @Override
    public void onSelect(ArrayList<FilterData> list) {

    }

    @Override
    public void onSelectItem(Filter filter, String title) {
        dismiss();
        if (null != filterSelectListener) {
            filterSelectListener.onSelectItem(filter, title);
        }
    }
}
