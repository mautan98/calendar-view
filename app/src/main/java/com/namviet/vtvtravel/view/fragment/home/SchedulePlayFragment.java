package com.namviet.vtvtravel.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ScheduleTravelAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.FragmentScheduleFinishBinding;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.model.Schedule;
import com.namviet.vtvtravel.response.ScheduleResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SchedulePlayFragment extends MainFragment implements Observer {
    private FragmentScheduleFinishBinding binding;
    private ScheduleTravelAdapter scheduleTravelAdapter;
    private List<Schedule> scheduleList = new ArrayList<>();
    private PlaceViewModel placeViewModel;
    private EndlessRecyclerViewScrollListener scrollListener;

    public static SchedulePlayFragment newInstance() {
        SchedulePlayFragment fragment = new SchedulePlayFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_finish, container, false);
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
        placeViewModel = new PlaceViewModel(getContext());
        binding.setPlaceViewModel(placeViewModel);
        placeViewModel.addObserver(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvListSchedule.setLayoutManager(layoutManager);
        scheduleTravelAdapter = new ScheduleTravelAdapter(getContext(), scheduleList);
        binding.rvListSchedule.setAdapter(scheduleTravelAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager, 0) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                MyLocation myLocation = MyApplication.getInstance().getMyLocation();
                if (null != myLocation) {
                    placeViewModel.loadScheduleCenter(myLocation.getLat(), myLocation.getLog(), page);
                }
            }
        };
        binding.rvListSchedule.addOnScrollListener(scrollListener);
        updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }

    @Override
    protected void updateViews() {
        super.updateViews();
        binding.prLoading.setVisibility(View.VISIBLE);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MyLocation myLocation = MyApplication.getInstance().getMyLocation();
                if (null != myLocation) {
                    placeViewModel.loadScheduleCenter(myLocation.getLat(), myLocation.getLog(), 0);
                }
            }
        },150);

    }

    @Override
    public void update(Observable observable, final Object o) {
        binding.prLoading.setVisibility(View.GONE);
        if (observable instanceof PlaceViewModel) {
            if (o != null) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (o instanceof ScheduleResponse) {
                            ScheduleResponse response = (ScheduleResponse) o;
                            scheduleList.addAll(response.getData().getContent());
                            scheduleTravelAdapter.notifyDataSetChanged();
                        }
                    }
                });

            } else {

            }
        }
    }

    public ArrayList<Schedule> getScheduleSelected() {
        return scheduleTravelAdapter.getListSelected();
    }
}
