package com.namviet.vtvtravel.view.fragment.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.TourCreatedAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentAccountTabBinding;
import com.namviet.vtvtravel.listener.ScheduleSelectListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Schedule;
import com.namviet.vtvtravel.response.SaveScheduleResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AccountTourFragment extends MainFragment implements Observer, ScheduleSelectListener {
    private FragmentAccountTabBinding binding;
    private PlaceViewModel placeViewModel;
    private EndlessRecyclerViewScrollListener scrollListener;
    private TourCreatedAdapter tourCreatedAdapter;
    private List<Schedule> scheduleList = new ArrayList<>();

    public static AccountTourFragment newInstance() {
        AccountTourFragment fragment = new AccountTourFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_tab, container, false);
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rvPhotos.setLayoutManager(gridLayoutManager);
        tourCreatedAdapter = new TourCreatedAdapter(getContext(), scheduleList);
        binding.rvPhotos.setAdapter(tourCreatedAdapter);
        tourCreatedAdapter.setScheduleSelectListener(this);
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager, 0) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        };
        binding.rvPhotos.addOnScrollListener(scrollListener);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        Account account = MyApplication.getInstance().getAccount();
        if (account != null) {
            placeViewModel.loadTourCreate(account.getId());
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        binding.prLoading.setVisibility(View.GONE);
        if (observable instanceof PlaceViewModel) {
            if (o != null) {
                if (o instanceof SaveScheduleResponse) {
                    SaveScheduleResponse response = (SaveScheduleResponse) o;
                    if (response.isSuccess()) {
                        scheduleList.clear();
                        scheduleList.addAll(response.getData().getContent());
                        tourCreatedAdapter.notifyDataSetChanged();
                        if (tourCreatedAdapter.getItemCount() == 0) {
//                            binding.tvNotSchedule.setText(getString(R.string.not_schedule));
                            binding.tvNotSchedule.setText("Chưa có lịch trình nào.");
                            binding.tvNotSchedule.setVisibility(View.VISIBLE);
                        } else {
                            binding.tvNotSchedule.setVisibility(View.GONE);
                        }
                    } else {
                        binding.tvNotSchedule.setVisibility(View.VISIBLE);
                        binding.tvNotSchedule.setText(getString(R.string.not_tour));
                    }
                }
            } else {
                binding.tvNotSchedule.setVisibility(View.VISIBLE);
                binding.tvNotSchedule.setText(getString(R.string.not_tour));
            }
        }

    }

    @Override
    public void onScheduleSelect(Schedule schedule) {
        schedule.setId(schedule.getTourId());
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, schedule);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_SCHEDULE_SCREEN_MINE);
    }
}
