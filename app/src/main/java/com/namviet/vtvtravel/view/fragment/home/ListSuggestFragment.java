package com.namviet.vtvtravel.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ListSuggestAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentListSuggestTravelBinding;
import com.namviet.vtvtravel.databinding.FragmentSuggestTravelBinding;
import com.namviet.vtvtravel.listener.CitySelectListener;
import com.namviet.vtvtravel.listener.ScheduleSelectListener;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.Schedule;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.FilterResponse;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.response.ScheduleResponse;
import com.namviet.vtvtravel.view.dialog.CityDialogFragment;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ListSuggestFragment extends MainFragment implements ScheduleSelectListener, Observer, CitySelectListener {
    private FragmentListSuggestTravelBinding binding;
    private ListSuggestAdapter listSuggestAdapter;
    private PlaceViewModel placeViewModel;
    private ArrayList<City> cityList;
    private ArrayList<Schedule> schedulesList = new ArrayList<>();
    private City mCitySelect;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_suggest_travel, container, false);
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
        binding.toolBar.tvTitle.setText(getString(R.string.suggest_travel_for_you));
        binding.toolBar.ivSearch.setVisibility(View.INVISIBLE);
        binding.toolBar.ivSearch.setEnabled(false);
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.tvCity.setOnClickListener(this);
        binding.tvCreate.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvListSuggest.setLayoutManager(layoutManager);
        listSuggestAdapter = new ListSuggestAdapter(getContext(), schedulesList);
        listSuggestAdapter.setScheduleSelectListener(this);
        binding.rvListSuggest.setAdapter(listSuggestAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager, 0) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                placeViewModel.loadTourSuggest(mCitySelect, page);

            }
        };
        binding.rvListSuggest.addOnScrollListener(scrollListener);
        updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.tvCity) {
            if (null != cityList) {
                CityDialogFragment cityDialogFragment = CityDialogFragment.newInstance(cityList);
                cityDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                cityDialogFragment.setCitySelectListener(this);
            }
        } else if (view == binding.tvCreate) {
            mActivity.switchFragment(SlideMenu.MenuType.SCHEDULE_TRAVEL_SCREEN);
        }

    }

    @Override
    protected void updateViews() {
        super.updateViews();
        showDialogLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                placeViewModel.loadCity();
            }
        }, Constants.TimeDelay);

    }

    @Override
    public void onScheduleSelect(Schedule schedule) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, schedule);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_SCHEDULE_SCREEN);
    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof PlaceViewModel) {
            if (o != null) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (o instanceof ScheduleResponse) {
                            schedulesList.clear();
                            ScheduleResponse response = (ScheduleResponse) o;
                            schedulesList.addAll(response.getData().getContent());
                            listSuggestAdapter.notifyDataSetChanged();
                        } else if (o instanceof CityResponse) {
                            CityResponse response = (CityResponse) o;
                            cityList = response.getData();
                            if (cityList.size() > 0) {
                                binding.tvCity.setText(cityList.get(0).getName());
                            }
                            mCitySelect = cityList.get(0);
                            placeViewModel.loadTourSuggest(cityList.get(0), 0);
                        }
                    }
                });

            } else {

            }
        }
    }

    @Override
    public void onSelect(ArrayList<City> list, City city) {
        schedulesList.clear();
        mCitySelect = city;
        cityList = list;
        binding.tvCity.setText(city.getName());
        placeViewModel.loadTourSuggest(mCitySelect, 0);
    }
}
