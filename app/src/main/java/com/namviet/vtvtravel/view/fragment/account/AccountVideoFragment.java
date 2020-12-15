package com.namviet.vtvtravel.view.fragment.account;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.YourMomentAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentAccountTabBinding;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AccountVideoFragment extends MainFragment implements Observer, TravelSelectListener {

    private FragmentAccountTabBinding binding;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlaceViewModel placeViewModel;
    private List<Travel> travelList = new ArrayList<>();
    private YourMomentAdapter yourMomentAdapter;

    public static AccountVideoFragment newInstance() {
        AccountVideoFragment fragment = new AccountVideoFragment();
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

        binding.rlParent.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg_moment));
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rvPhotos.setLayoutManager(mLayoutManager);
        yourMomentAdapter = new YourMomentAdapter(getContext(), travelList);
        binding.rvPhotos.setAdapter(yourMomentAdapter);
        yourMomentAdapter.setTravelSelectListener(this);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                placeViewModel.loadYourMoment();
            }
        }, Constants.TimeDelay);

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof PlaceViewModel) {
            if (o != null) {
                if (o instanceof PlaceResponse) {
                    travelList.clear();
                    PlaceResponse itemCategoryData = (PlaceResponse) o;
                    travelList.addAll(itemCategoryData.getData().getItems());
                    yourMomentAdapter.notifyDataSetChanged();
                    if (yourMomentAdapter.getItemCount() == 0) {
                        binding.tvNotSchedule.setText(getString(R.string.not_moment));
                        binding.tvNotSchedule.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvNotSchedule.setVisibility(View.GONE);
                    }
                }
            } else {

            }
        }
    }

    @Override
    public void onSelectTravel(Travel travel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, travel);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_MOMENT_SCREEN);
    }
}
