package com.namviet.vtvtravel.view.fragment.f2biglocation;

import android.annotation.SuppressLint;
import androidx.core.content.ContextCompat;
import android.view.View;

import com.baseapp.utils.KeyboardUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.SearchAllLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.SearchTopLocationAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentSearchBigLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.TopLocationResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchBigLocationFragment extends BaseFragment<F2FragmentSearchBigLocationBinding> implements Observer {
    private SearchBigLocationViewModel viewModel;
    private SearchAllLocationAdapter searchAllLocationAdapter;
    private SearchTopLocationAdapter searchTopLocationAdapter;
    private List<Location> locationsMain = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_search_big_location;
    }

    @Override
    public void initView() {
        viewModel = new SearchBigLocationViewModel();
        getBinding().setSearchBigLocationViewModel(viewModel);
        viewModel.addObserver(this);

        viewModel.getTopLocation();
        viewModel.getAllLocation();
    }

    @Override
    public void initData() {
        searchAllLocationAdapter = new SearchAllLocationAdapter(mActivity, locations, new SearchAllLocationAdapter.ClickItem() {
            @Override
            public void onClick(Location location) {
                if (location.getId() != null && !location.getId().isEmpty()) {
                    KeyboardUtils.hideKeyboard(mActivity, getBinding().edtLocation);
                    addFragment(new BigLocationFragment(location.getId()));
                }
            }
        });
        getBinding().rclLocation.setAdapter(searchAllLocationAdapter);

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        handleSearch();
        getBinding().imgCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().edtLocation.setText("");
            }
        });

        getBinding().btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getLocation();
            }
        });
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideKeyboard(mActivity, getBinding().edtLocation);
                mActivity.onBackPressed();
            }
        });
        getBinding().btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchTopLocationAdapter != null && searchTopLocationAdapter.isShowAll()) {
                    searchTopLocationAdapter.setShowAll(false);
                    getBinding().tvReadMore.setText("XEM THÊM");
                } else if (searchTopLocationAdapter != null && !searchTopLocationAdapter.isShowAll()) {
                    searchTopLocationAdapter.setShowAll(true);
                    getBinding().tvReadMore.setText("THU GỌN");
                }
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof SearchBigLocationViewModel && null != o) {
            if (o instanceof AllLocationResponse) {
                AllLocationResponse response = (AllLocationResponse) o;
                locationsMain = response.getData();
            } else if (o instanceof TopLocationResponse) {
                TopLocationResponse response = (TopLocationResponse) o;
                searchTopLocationAdapter = new SearchTopLocationAdapter(mActivity, response.getData().getItems(), new SearchTopLocationAdapter.ClickItem() {
                    @Override
                    public void onClick(Location location) {
                        if (location.getId() != null && !location.getId().isEmpty()) {
                            KeyboardUtils.hideKeyboard(mActivity, getBinding().edtLocation);
                            addFragment(new BigLocationFragment(location.getId()));
                        }
                    }
                });
                getBinding().rclBigLocation.setAdapter(searchTopLocationAdapter);
            } else if (o instanceof LocationResponse) {
                LocationResponse response = (LocationResponse) o;
                getBinding().edtLocation.setText(response.getData().getName());
            }  else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

//    private List<Location> getList() {
//        List<Location> locations = new ArrayList<>();
//        for (int i = 0; i < 63; i++) {
//            Location location = new Location("Province " + i, "https://www.publicdomainpictures.net/pictures/320000/nahled/background-image.png");
//            locations.add(location);
//        }
//        return locations;
//    }

    @SuppressLint("CheckResult")
    private void handleSearch() {
        RxTextView.afterTextChangeEvents(getBinding().edtLocation)
                .skipInitialValue()
                .debounce(450, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewAfterTextChangeEvent -> {
                    if (getBinding().edtLocation.getText().toString().isEmpty()) {
                        getBinding().layoutBigLocation.setVisibility(View.VISIBLE);
                        getBinding().rclLocation.setVisibility(View.GONE);
                        getBinding().tvTitleLocation.setVisibility(View.VISIBLE);
                        getBinding().tvBorder.setVisibility(View.VISIBLE);
                    } else {
                        getBinding().layoutBigLocation.setVisibility(View.GONE);
                        getBinding().rclLocation.setVisibility(View.VISIBLE);
                        locations.clear();
                        for (int i = 0; i < locationsMain.size(); i++) {
                            if (F2Util.removeAccent(locationsMain.get(i).getName().toLowerCase()).contains(F2Util.removeAccent(getBinding().edtLocation.getText().toString().toLowerCase()))) {
                                locations.add(locationsMain.get(i));
                            }
                        }
                        searchAllLocationAdapter.notifyDataSetChanged();
                        getBinding().tvTitleLocation.setVisibility(View.GONE);
                        getBinding().tvBorder.setVisibility(View.GONE);
                    }

                });
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.SEARCH_BIG_LOCATION, TrackingAnalytic.ScreenTitle.SEARCH_BIG_LOCATION);
    }
}
