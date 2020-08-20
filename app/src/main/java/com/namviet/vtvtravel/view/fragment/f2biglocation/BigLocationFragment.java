package com.namviet.vtvtravel.view.fragment.f2biglocation;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.BigLocationTopTabAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.DetailBigLocationAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentBigLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2biglocation.BigLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.RegionResponse;
import com.namviet.vtvtravel.viewmodel.f2biglocation.BigLocationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BigLocationFragment extends BaseFragment<F2FragmentBigLocationBinding> implements Observer {
    private DetailBigLocationAdapter detailBigLocationAdapter;
    private BigLocationTopTabAdapter bigLocationTopTabAdapter;
    private List<BigLocationResponse.Data.Item> dataListMain = new ArrayList<>();
    private List<BigLocationResponse.Data.Region.Items> dataListTab = new ArrayList<>();
    private BigLocationViewModel viewModel;
    private BigLocationResponse.Data.Region region;
    private int scroll = 0;
    private String regionId;


    @SuppressLint("ValidFragment")
    public BigLocationFragment(String regionId) {
        this.regionId = regionId;
    }

    public BigLocationFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_big_location;
    }

    @Override
    public void initView() {
        viewModel = new BigLocationViewModel();
        getBinding().setBigLocationViewModel(viewModel);
        viewModel.addObserver(this);
        viewModel.getBigLocation(regionId);

//        viewModel.getLocation();
//        viewModel.getRegion("https://api1.travel.onex.vn/region/5555bee2c4a1608e6e9a1610");
    }

    @Override
    public void initData() {
        getBinding().rclContent.setHasFixedSize(true);
        getBinding().rclContent.setItemViewCacheSize(20);
        detailBigLocationAdapter = new DetailBigLocationAdapter(dataListMain, region, mActivity, null);
        getBinding().rclContent.setAdapter(detailBigLocationAdapter);

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scroll = scroll + dy;
                Log.e("scroll", scroll+"");
                if(scroll < 400){
                    getBinding().layoutPin.setBackgroundColor(Color.parseColor("#00000000"));
                }else {
                    getBinding().layoutPin.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
                }
            }
        });

        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().layoutChooseRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new SearchBigLocationFragment());
            }
        });

        getBinding().btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new SearchBigLocationFragment());
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        getBinding().shimmerMain.setVisibility(View.GONE);
        if (observable instanceof BigLocationViewModel && null != o) {
            if (o instanceof BigLocationResponse) {
                BigLocationResponse response = (BigLocationResponse) o;
                dataListMain.clear();
                dataListMain.addAll(response.getData().getListDataMain());
//                detailBigLocationAdapter.notifyDataSetChanged();
                detailBigLocationAdapter = new DetailBigLocationAdapter(dataListMain, response.getData().getRegion(), mActivity, null);
                getBinding().rclContent.setAdapter(detailBigLocationAdapter);
                getBinding().tvRegionNameTop.setText(response.getData().getRegion().getName());

            } else if (o instanceof LocationResponse) {
                LocationResponse response = (LocationResponse) o;
                LocationResponse response1 = (LocationResponse) o;
            } else if (o instanceof RegionResponse) {
                RegionResponse response = (RegionResponse) o;
                RegionResponse response1 = (RegionResponse) o;
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }
}
