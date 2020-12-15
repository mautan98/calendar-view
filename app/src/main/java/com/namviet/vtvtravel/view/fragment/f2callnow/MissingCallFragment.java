package com.namviet.vtvtravel.view.fragment.f2callnow;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2callnow.MissingCallHistoryAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentCallMissingBinding;
import com.namviet.vtvtravel.model.f2callnow.CallNowHistory;
import com.namviet.vtvtravel.model.f2event.OnDeleteSuccess;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.f2callnow.MainCallHistoryViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MissingCallFragment extends MainFragment implements MissingCallHistoryAdapter.ClickItem, Observer {
    private F2FragmentCallMissingBinding binding;
    private MissingCallHistoryAdapter missingCallHistoryAdapter;
    private MainCallHistoryViewModel mainCallHistoryViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setData(List<CallNowHistory> callNowHistories){
        if(missingCallHistoryAdapter != null){
            missingCallHistoryAdapter.setData(callNowHistories);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_call_missing, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.MISSING_CALL, TrackingAnalytic.ScreenTitle.MISSING_CALL).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        updateViews();
        missingCallHistoryAdapter = new MissingCallHistoryAdapter(mActivity, null, this);
        binding.rclContent.setAdapter(missingCallHistoryAdapter);

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

    public void enableDelete(boolean isEnable){
        missingCallHistoryAdapter.setEnableDelete(isEnable);
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
