package com.namviet.vtvtravel.view.fragment.f2callnow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2callnow.AllCallHistoryAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentAllHistoryBinding;
import com.namviet.vtvtravel.databinding.F2FragmentCallNowBinding;
import com.namviet.vtvtravel.model.f2event.OnDeleteSuccess;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2callnow.CallNowHistoryResponse;
import com.namviet.vtvtravel.model.f2callnow.CallNowHistory;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.f2callnow.MainCallHistoryViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.List;


public class AllCallHistoryFragment extends MainFragment implements Observer, AllCallHistoryAdapter.ClickItem {

    private F2FragmentAllHistoryBinding binding;
    private AllCallHistoryAdapter allCallHistoryAdapter;
    private MainCallHistoryViewModel mainCallHistoryViewModel;

    private List<CallNowHistory> callNowHistories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setData(List<CallNowHistory> callNowHistories) {
        this.callNowHistories = callNowHistories;
        if (allCallHistoryAdapter != null) {
            allCallHistoryAdapter.setData(callNowHistories);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_all_history, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        updateViews();
        allCallHistoryAdapter = new AllCallHistoryAdapter(mActivity, null, this);
        binding.rclContent.setAdapter(allCallHistoryAdapter);

        mainCallHistoryViewModel = new MainCallHistoryViewModel();
        binding.setMainCallHistoryViewModel(mainCallHistoryViewModel);
        mainCallHistoryViewModel.addObserver(this);

    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void enableDelete(boolean isEnable) {
        allCallHistoryAdapter.setEnableDelete(isEnable);
    }

    @Override
    public void onClickItem(int position, String phone) {
        SearchNumberFragment searchNumberFragment = new SearchNumberFragment();
        searchNumberFragment.setPhone(phone);
        mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, searchNumberFragment).addToBackStack(null).commit();
    }

    @Override
    public void onClickDelete(int position, String id) {
        List<String> list = new ArrayList<>();
        list.add(id);
        mainCallHistoryViewModel.deleteCallHistory("FEW", list);
    }


    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof MainCallHistoryViewModel && null != o) {
            if (o instanceof BaseResponse) {
                BaseResponse baseResponse = (BaseResponse) o;
                EventBus.getDefault().post(new OnDeleteSuccess());
            } else if (o instanceof ResponseError) {
                ResponseError responseError = (ResponseError) o;
                showMessage(responseError.getMessage());
            }
        }

    }
}
