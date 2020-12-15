package com.namviet.vtvtravel.view.fragment.account;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ScheduleCreatedAdapter;
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

public class AccountScheduleFragment extends MainFragment implements Observer, ScheduleSelectListener {

    private FragmentAccountTabBinding binding;
    private PlaceViewModel placeViewModel;
    private ScheduleCreatedAdapter myListSuggestAdapter;
    private List<Schedule> scheduleList = new ArrayList<>();
    private EndlessRecyclerViewScrollListener scrollListener;
    public static AccountScheduleFragment newInstance() {
        AccountScheduleFragment fragment = new AccountScheduleFragment();
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvPhotos.setLayoutManager(layoutManager);
        myListSuggestAdapter = new ScheduleCreatedAdapter(getContext(), scheduleList);
        binding.rvPhotos.setAdapter(myListSuggestAdapter);
        myListSuggestAdapter.setScheduleSelectListener(this);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager, 0) {
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
            binding.prLoading.setVisibility(View.VISIBLE);
            placeViewModel.loadScheduleCreate(account.getId());
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
                        myListSuggestAdapter.notifyDataSetChanged();
                        if(myListSuggestAdapter.getItemCount()==0){
                            binding.tvNotSchedule.setText(getString(R.string.not_schedule));
                            binding.tvNotSchedule.setVisibility(View.VISIBLE);
                        }else {
                            binding.tvNotSchedule.setVisibility(View.GONE);
                        }
                    } else {
                        binding.tvNotSchedule.setVisibility(View.VISIBLE);
                        binding.tvNotSchedule.setText(getString(R.string.not_schedule));
                    }
                }
            } else {
                binding.tvNotSchedule.setVisibility(View.VISIBLE);
                binding.tvNotSchedule.setText(getString(R.string.not_schedule));
            }
        }
    }

    @Override
    public void onScheduleSelect(Schedule schedule) {
        Bundle bundle=new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT,schedule);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_SCHEDULE_CREATE_SCREEN);
    }
}
