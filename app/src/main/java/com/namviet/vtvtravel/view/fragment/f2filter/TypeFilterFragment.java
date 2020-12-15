package com.namviet.vtvtravel.view.fragment.f2filter;

import android.annotation.SuppressLint;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.filter.FilterTypeAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentTypeFilterBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.filter.ItemTab;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;

import java.util.List;

public class TypeFilterFragment extends BaseFragment<F2FragmentTypeFilterBinding> {
    private FilterTypeAdapter filterTypeAdapter;

    private FilterByCodeResponse filterByCodeResponse;
    private DoneOptionFilterType doneOptionFilterType;
    private String code;


    @SuppressLint("ValidFragment")
    public TypeFilterFragment(FilterByCodeResponse filterByCodeResponse, String code, DoneOptionFilterType doneOptionFilterType) {
        this.filterByCodeResponse = filterByCodeResponse;
        this.doneOptionFilterType = doneOptionFilterType;
        this.code = code;
    }

    public TypeFilterFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_type_filter;
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

        ItemTab itemTab = filterByCodeResponse.getData().getItems().get(position);

        int positionTypeSelected = 1000;

        for (int i = 0; i < itemTab.getDataHasLoaded().getData().size(); i++) {
            if(itemTab.getDataHasLoaded().getData().get(i).isSelected()){
                positionTypeSelected = i;
                break;
            }
        }

        List<FilterByPageResponse.Data.Input> inputs = itemTab.getDataHasLoaded().getData().get(positionTypeSelected).getInputs();

        filterTypeAdapter = new FilterTypeAdapter(inputs, mActivity, new FilterTypeAdapter.ClickItem() {
            @Override
            public void onClickItem(CommentResponse.Data.Comment comment) {

            }
        });
        getBinding().rclContent.setAdapter(filterTypeAdapter);


        getBinding().btnUnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputs != null && inputs.size() > 0) {
                    for (int i = 0; i < inputs.size(); i++) {
                        inputs.get(i).setSelected(false);
                    }
                    filterTypeAdapter.notifyDataSetChanged();
                }
            }
        });
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
                doneOptionFilterType.onDoneOptionFilterType();
            }
        });

        getBinding().btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
                doneOptionFilterType.onDoneOptionFilterType();
            }
        });


    }

    @Override
    public void setObserver() {

    }

    public interface DoneOptionFilterType{
        void onDoneOptionFilterType();
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.FILTER_TYPE, TrackingAnalytic.ScreenTitle.FILTER_TYPE);
    }
}
