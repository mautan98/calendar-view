package com.namviet.vtvtravel.view.fragment.f2filter;

import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.filter.BaseFilterAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentBaseFilterBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;

import java.util.ArrayList;
import java.util.List;

public class BaseFilterFragment extends BaseFragment<F2FragmentBaseFilterBinding> {
    private BaseFilterAdapter baseFilterAdapter;
    private FilterByCodeResponse filterByCodeResponse;
    private String code;
    private FinishBaseFilter finishBaseFilter;

    public BaseFilterFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_base_filter;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        int position = 0;
        for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
            if(filterByCodeResponse.getData().getItems().get(i).getCode().equals(code)){
                position = i;
                break;
            }
        }
        baseFilterAdapter = new BaseFilterAdapter(filterByCodeResponse.getData().getItems().get(position).getDataHasLoaded().getData(), mActivity, new BaseFilterAdapter.ClickItem() {
            @Override
            public void onClickItem(CommentResponse.Data.Comment comment) {

            }
        }, getBaseFilterSelected());
        getBinding().rclContent.setAdapter(baseFilterAdapter);
    }


    private int getBaseFilterSelected(){
        List<FilterByPageResponse.Data> dataList = new ArrayList<>();
        if (filterByCodeResponse != null
                && filterByCodeResponse.getData() != null
                && filterByCodeResponse.getData().getItems() != null
                && filterByCodeResponse.getData().getItems().size() > 0)
            for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
                if (filterByCodeResponse.getData().getItems().get(i).isSelected()) {
                    if (filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded() != null) {
                        dataList = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded().getData();
                        break;
                    }
                }
            }

        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).isSelected()) {
                    return i;
                }
            }
        }
        return 1000;
    }


    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishBaseFilter.onFinishBaseFilter(filterByCodeResponse);
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void setObserver() {

    }

    public void setData(FilterByCodeResponse filterByCodeResponse, String code, FinishBaseFilter finishBaseFilter) {
        this.filterByCodeResponse = filterByCodeResponse;
        this.code = code;
        this.finishBaseFilter = finishBaseFilter;
    }


    public interface FinishBaseFilter{
        public void onFinishBaseFilter(FilterByCodeResponse filterByCodeResponse);
    }
}
