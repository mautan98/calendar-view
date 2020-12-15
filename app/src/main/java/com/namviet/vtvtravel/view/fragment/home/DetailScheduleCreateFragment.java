package com.namviet.vtvtravel.view.fragment.home;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.GroupScheduleAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentDetailScheduleCreateBinding;
import com.namviet.vtvtravel.model.GroupSchedule;
import com.namviet.vtvtravel.model.Schedule;
import com.namviet.vtvtravel.response.DetailScheduleCreateResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DetailScheduleCreateFragment extends MainFragment implements Observer {
    private FragmentDetailScheduleCreateBinding binding;
    private PlaceViewModel placeViewModel;
    private GroupScheduleAdapter groupScheduleAdapter;
    private ArrayList<GroupSchedule> groupScheduleList = new ArrayList<>();
    private Schedule schedule;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            schedule = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_schedule_create, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar.myToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
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
        binding.toolBar.tvTitle.setText(getString(R.string.my_schedule_travel));
        binding.toolBar.ivSearch.setVisibility(View.INVISIBLE);
        binding.toolBar.ivSearch.setEnabled(false);
        binding.toolBar.ivBack.setOnClickListener(this);

        binding.rvScheduleSelect.setLayoutManager(new LinearLayoutManager(getContext()));
        groupScheduleAdapter = new GroupScheduleAdapter(mActivity, groupScheduleList, true);
        binding.rvScheduleSelect.setAdapter(groupScheduleAdapter);

        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        if (null != schedule) {
            showDialogLoading();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    placeViewModel.loadDetailScheduleCreate(schedule.getId());
                }
            }, Constants.TimeDelay);
        }

    }

    @Override
    public void update(Observable observable, Object o) {
        dimissDialogLoading();
        if (observable instanceof PlaceViewModel) {
            if (null != o) {
                if (o instanceof DetailScheduleCreateResponse) {
                    DetailScheduleCreateResponse response = (DetailScheduleCreateResponse) o;
                    if (null != response.getData().getRestaurants()) {
                        groupScheduleList.add(new GroupSchedule("Ăn uống", response.getData().getRestaurants().getContent(), Constants.TypeSchedule.RESTAURANTS));
                    }
                    if (null != response.getData().getPlaces() && response.getData().getPlaces().getContent().size() > 0) {
                        groupScheduleList.add(new GroupSchedule("Thăm quan mua sắm", response.getData().getPlaces().getContent(), Constants.TypeSchedule.CENTERS));
                    }

                    if (null != response.getData().getCenters() && response.getData().getCenters().getContent().size() > 0) {
                        groupScheduleList.add(new GroupSchedule("Vui chơi giải trí", response.getData().getCenters().getContent(), Constants.TypeSchedule.HOTEL));
                    }
                    groupScheduleAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        }
    }
}
