package com.namviet.vtvtravel.view.fragment.travel;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.FoodEatAdapter;
import com.namviet.vtvtravel.adapter.WhereGoAdapter;
import com.namviet.vtvtravel.adapter.WhereStayAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentNearPlaceBinding;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.ListPlace;
import com.namviet.vtvtravel.model.NearItem;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NearPlaceFragment extends MainFragment implements Observer, TravelSelectListener {
    private FragmentNearPlaceBinding binding;
    private WhereGoAdapter whereGoAdapter;
    private FoodEatAdapter foodEatAdapter;
    private WhereStayAdapter whereStayAdapter;
    private PlaceViewModel placeViewModel;
    private NearItem nearItem;
    private List<Travel> travelList = new ArrayList<>();
    private EndlessRecyclerViewScrollListener scrollListener;
    private String moreLink;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            nearItem = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_near_place, container, false);
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
        binding.toolBar.tvTitle.setText(getString(R.string.tv_living));
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.toolBar.ivSearch.setOnClickListener(this);
        if (null != nearItem) {
            if (nearItem.getContent_type().equals(Constants.TypePlace.places) || nearItem.getContent_type().equals(Constants.TypePlace.centers)) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                binding.rvNearPlace.setLayoutManager(layoutManager);
                whereGoAdapter = new WhereGoAdapter(getContext(), travelList);
                whereGoAdapter.setTravelSelectListener(this);
                binding.rvNearPlace.setAdapter(whereGoAdapter);
                scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        if (null != moreLink && moreLink.length() > 0) {
                            placeViewModel.loadNearPlace(moreLink);
                        }

                    }
                };

            } else if (nearItem.getContent_type().equals(Constants.TypePlace.restaurants)) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                binding.rvNearPlace.setLayoutManager(gridLayoutManager);
                foodEatAdapter = new FoodEatAdapter(getContext(), travelList);
                binding.rvNearPlace.setAdapter(foodEatAdapter);
                foodEatAdapter.setTravelSelectListener(this);
                scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        if (null != moreLink && moreLink.length() > 0) {
                            placeViewModel.loadNearPlace(moreLink);
                        }

                    }
                };
            } else if (nearItem.getContent_type().equals(Constants.TypePlace.hotels)) {
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position % 5 == 0) {
                            return 2;
                        } else {
                            return 1;
                        }
                    }
                });
                binding.rvNearPlace.setLayoutManager(layoutManager);
                whereStayAdapter = new WhereStayAdapter(getContext(), travelList);
                whereStayAdapter.setTravelSelectListener(this);
                binding.rvNearPlace.setAdapter(whereStayAdapter);
                scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        if (null != moreLink && moreLink.length() > 0) {
                            placeViewModel.loadNearPlace(moreLink);
                        }

                    }
                };
            }
            binding.rvNearPlace.addOnScrollListener(scrollListener);
        }


        binding.rvNearPlace.setNestedScrollingEnabled(false);

        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        if (null != nearItem) {
            showDialogLoading();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    placeViewModel.loadNearPlace(nearItem.getLink());
                }
            }, Constants.TimeDelay);

        }


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void update(Observable observable, Object o) {
        dimissDialogLoading();
        if (observable instanceof PlaceViewModel) {
            if (o != null) {
                if (o instanceof PlaceResponse) {
                    PlaceResponse response = (PlaceResponse) o;
                    moreLink = response.getData().getMore_link();
                    updateUI(response.getData());
                }
            } else {
            }
        }

    }


    @Override
    public void onSelectTravel(Travel travel) {
        int backStackEntry = mActivity.getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 2) {
            for (int i = backStackEntry - 2; i < backStackEntry - 1; i++) {
                mActivity.getSupportFragmentManager().popBackStackImmediate();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
        mActivity.setBundle(bundle);
        if (travel.getContent_type().equals(Constants.TypePlace.places) || travel.getContent_type().equals(Constants.TypePlace.centers)) {
            mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.restaurants)) {
            mActivity.switchFragment(SlideMenu.MenuType.DETAIL_EAT_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.hotels)) {
            mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_STAY_SCREEN);
        }

    }

    private void updateUI(ListPlace data) {
        dimissDialogLoading();
        travelList.addAll(data.getItems());
        if (nearItem.getContent_type().equals(Constants.TypePlace.places) || nearItem.getContent_type().equals(Constants.TypePlace.centers)) {
            whereGoAdapter.notifyDataSetChanged();
        } else if (nearItem.getContent_type().equals(Constants.TypePlace.restaurants)) {
            foodEatAdapter.notifyDataSetChanged();
        } else if (nearItem.getContent_type().equals(Constants.TypePlace.hotels)) {

            whereStayAdapter.notifyDataSetChanged();
        }
    }
}
