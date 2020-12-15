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
import com.namviet.vtvtravel.adapter.CategoryFoodAdapter;
import com.namviet.vtvtravel.adapter.FoodEatAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentFoodBinding;
import com.namviet.vtvtravel.listener.CitySelectListener;
import com.namviet.vtvtravel.listener.FilterSelectListener;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.Filter;
import com.namviet.vtvtravel.response.CategoryResponse;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.FilterData;
import com.namviet.vtvtravel.response.FilterResponse;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.dialog.CityDialogFragment;
import com.namviet.vtvtravel.view.dialog.FilterDialogFragment;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FoodFragment extends MainFragment implements Observer, CitySelectListener, FilterSelectListener, TravelSelectListener {
    private FragmentFoodBinding binding;

    private CategoryFoodAdapter categoryFoodAdapter;
    private FoodEatAdapter foodEatAdapter;
    private PlaceViewModel placeViewModel;
    private ArrayList<City> cityList;
    private ArrayList<FilterData> listFilterData;
    private List<Travel> travelList = new ArrayList<>();
    private City mCitySelect;
    private String moreLink;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food, container, false);
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
        binding.toolBar.tvTitle.setText("Ăn Gì?");
        binding.toolBar.ivSearch.setOnClickListener(this);
        binding.filData.tvFilter.setOnClickListener(this);
        binding.filData.tvCity.setOnClickListener(this);
        binding.toolBar.ivBack.setOnClickListener(this);
//        tvFilter.setOnClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rvFoodEat.setLayoutManager(gridLayoutManager);
        foodEatAdapter = new FoodEatAdapter(getContext(), travelList);
        foodEatAdapter.setTravelSelectListener(this);
        binding.rvFoodEat.setAdapter(foodEatAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (null != moreLink) {
                    placeViewModel.loadRestaurants(cityList.get(0), listFilterData, page);
                }

            }
        };
        binding.rvFoodEat.addOnScrollListener(scrollListener);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        showDialogLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                placeViewModel.loadCity();
                placeViewModel.loadFilter(Constants.TypeItemHome.WHAT_EAT);
            }
        }, Constants.TimeDelay);


//        placeViewModel.loadCategoryHighLight();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.filData.tvCity) {
            if (null != cityList) {
                CityDialogFragment cityDialogFragment = CityDialogFragment.newInstance(cityList);
                cityDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                cityDialogFragment.setCitySelectListener(this);
            }
        } else if (view == binding.filData.tvFilter) {
            if (null != listFilterData) {
                FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance(listFilterData);
                filterDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                filterDialogFragment.setFilterSelectListener(this);
            }
        }
    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof PlaceViewModel) {
            if (o != null) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (o instanceof PlaceResponse) {
                            PlaceResponse response = (PlaceResponse) o;
                            moreLink = response.getData().getMore_link();
                            travelList.addAll(response.getData().getItems());
                            foodEatAdapter.notifyDataSetChanged();
                        } else if (o instanceof CityResponse) {
                            CityResponse response = (CityResponse) o;
                            cityList = response.getData();
                            if (cityList.size() > 0) {
                                binding.filData.tvCity.setText(cityList.get(0).getName());
                            }
                            mCitySelect = cityList.get(0);
                            placeViewModel.loadRestaurants(cityList.get(0), listFilterData, 1);
                        } else if (o instanceof FilterResponse) {
                            FilterResponse response = (FilterResponse) o;
                            listFilterData = response.getData();
                        } else if (o instanceof CategoryResponse) {
                            CategoryResponse response = (CategoryResponse) o;
                            categoryFoodAdapter = new CategoryFoodAdapter(getContext(), response.getData());
                            binding.rvCategory.setAdapter(categoryFoodAdapter);
                        }  else if (o instanceof ResponseError) {
                            ResponseError responseError = (ResponseError) o;
                            showMessage(responseError.getMessage());
                        }
                    }
                });

            } else {

            }
        }
    }

    @Override
    public void onSelect(ArrayList<City> list, City city) {
        travelList.clear();
        mCitySelect = city;
        cityList = list;
        binding.filData.tvCity.setText(city.getName());
        placeViewModel.loadRestaurants(city, listFilterData, 1);
    }

    @Override
    public void onSelect(ArrayList<FilterData> list) {
        travelList.clear();
        listFilterData = list;
        placeViewModel.loadRestaurants(mCitySelect, listFilterData, 1);
    }

    @Override
    public void onSelectItem(Filter filter, String title) {

    }

    @Override
    public void onSelectTravel(Travel travel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_EAT_SCREEN);
    }


}
