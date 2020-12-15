package com.namviet.vtvtravel.view.fragment.travel;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.WhereGoAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentVoucherBinding;
import com.namviet.vtvtravel.listener.FilterSelectListener;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.Filter;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.response.FilterData;
import com.namviet.vtvtravel.response.FilterResponse;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.dialog.FilterDialogFragment;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class VoucherFragment extends MainFragment implements TravelSelectListener, Observer, FilterSelectListener {

    private FragmentVoucherBinding binding;
    private WhereGoAdapter whereGoAdapter;
    private PlaceViewModel placeViewModel;
    private ArrayList<City> cityList;
    private ArrayList<FilterData> listFilterData;
    private City mCitySelect;
    private List<Travel> travelList = new ArrayList<>();
    private String moreLink;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voucher, container, false);
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
        binding.toolBar.tvTitle.setText(getString(R.string.tv_voucher));
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.toolBar.ivSearch.setOnClickListener(this);
        binding.filData.tvFilter.setOnClickListener(this);
        binding.filData.tvCity.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvVoucher.setLayoutManager(layoutManager);
        whereGoAdapter = new WhereGoAdapter(getContext(), travelList);
        whereGoAdapter.setTravelSelectListener(this);
        binding.rvVoucher.setAdapter(whereGoAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (null != moreLink) {
                    placeViewModel.loadVoucher(listFilterData, page);
                }

            }
        };
        binding.rvVoucher.addOnScrollListener(scrollListener);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        showDialogLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                placeViewModel.loadVoucher(listFilterData, 1);
                placeViewModel.loadFilter(Constants.TypeItemHome.APP_VOUCHER);
            }
        }, Constants.TimeDelay);
    }

    @Override
    public void onSelectTravel(Travel travel) {
        News news = new News(travel.getId(), travel.getDetail_link(), travel.getContent_type());
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
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
                            whereGoAdapter.notifyDataSetChanged();
                        } else if (o instanceof FilterResponse) {
                            FilterResponse response = (FilterResponse) o;
                            listFilterData = response.getData();
                            if (listFilterData != null && listFilterData.size() == 0) {
                                binding.filData.tvFilter.setVisibility(View.GONE);
                            }
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
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.filData.tvFilter) {
            if (null != listFilterData) {
                FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance(listFilterData);
                filterDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                filterDialogFragment.setFilterSelectListener(this);
            }
        }
    }

    @Override
    public void onSelect(ArrayList<FilterData> list) {
        travelList.clear();
        listFilterData = list;
        placeViewModel.loadVoucher(listFilterData, 1);
    }

    @Override
    public void onSelectItem(Filter filter, String title) {

    }
}


