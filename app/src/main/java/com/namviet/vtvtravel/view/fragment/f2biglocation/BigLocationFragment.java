package com.namviet.vtvtravel.view.fragment.f2biglocation;

import android.annotation.SuppressLint;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.BigLocationTopTabAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.DetailBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.ParentDetailBigLocationAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentBigLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2biglocation.BigLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.PartBigLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.RegionResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.viewmodel.f2biglocation.BigLocationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BigLocationFragment extends BaseFragment<F2FragmentBigLocationBinding> implements Observer, ParentDetailBigLocationAdapter.CallRequest {
    private DetailBigLocationAdapter detailBigLocationAdapter;
    private ParentDetailBigLocationAdapter parentDetailBigLocationAdapter;
    private BigLocationTopTabAdapter bigLocationTopTabAdapter;
    private List<BigLocationResponse.Data.Item> dataListMain = new ArrayList<>();
    private List<BigLocationResponse.Data.Item> dataListWhere = new ArrayList<>();
    private List<BigLocationResponse.Data.Item> dataListStay = new ArrayList<>();
    private List<BigLocationResponse.Data.Item> dataListEat = new ArrayList<>();
    private List<BigLocationResponse.Data.Item> dataListPlay = new ArrayList<>();

    private BigLocationResponse.Data.Item travelTip;
    private BigLocationResponse.Data.Item video;

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
        parentDetailBigLocationAdapter = new ParentDetailBigLocationAdapter(dataListMain, region, mActivity, viewModel, dataListWhere, dataListStay, dataListEat, dataListPlay, travelTip, video, this);
//        detailBigLocationAdapter = new DetailBigLocationAdapter(dataListMain, region, mActivity, null, viewModel);
        getBinding().rclContent.setAdapter(parentDetailBigLocationAdapter);

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


                for (int i  = 0; i < dataListMain.size(); i++){
                    switch (i){
                        case 0:
                        case 1:
                            dataListWhere.add(dataListMain.get(i));
                            break;

                        case 2:
                        case 3:
                            dataListStay.add(dataListMain.get(i));
                            break;
                        case 4:
                        case 5:
                            dataListEat.add(dataListMain.get(i));
                            break;
                        case 6:
                        case 7:
                            dataListPlay.add(dataListMain.get(i));
                            break;
                        case 8:
                            travelTip = dataListMain.get(i);
                            break;
                        case 9:
                            video = dataListMain.get(i);
                            break;
                    }
                }


//                detailBigLocationAdapter = new DetailBigLocationAdapter(dataListMain, response.getData().getRegion(), mActivity, null, viewModel);
                parentDetailBigLocationAdapter = new ParentDetailBigLocationAdapter(dataListMain, response.getData().getRegion(), mActivity, viewModel, dataListWhere, dataListStay, dataListEat, dataListPlay, travelTip, video, this);
                getBinding().rclContent.setAdapter(parentDetailBigLocationAdapter);
                getBinding().tvRegionNameTop.setText(response.getData().getRegion().getName());
                try {
                    viewModel.loadWeather(response.getData().getRegion().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (o instanceof LocationResponse) {
                LocationResponse response = (LocationResponse) o;
                LocationResponse response1 = (LocationResponse) o;
            }else if (o instanceof PartBigLocationResponse) {
                PartBigLocationResponse response = (PartBigLocationResponse) o;
                switch (response.getCodeToSplit()){
                    case "APP_PLACE_HIGH_LIGHT":
                    case "APP_WHERE_GO":
                        for (int i = 0; i < dataListWhere.size(); i++) {
                            if(dataListWhere.get(i).getCode().equals(response.getCodeToSplit())){
                                dataListWhere.get(i).setItems(response.getItems());
                                parentDetailBigLocationAdapter.notifyDataSetChanged();
                            }
                        }
                        break;

                    case "APP_TOP_HOTEL":
                    case "APP_WHERE_STAY":
                        for (int i = 0; i < dataListStay.size(); i++) {
                            if(dataListStay.get(i).getCode().equals(response.getCodeToSplit())){
                                dataListStay.get(i).setItems(response.getItems());
                                parentDetailBigLocationAdapter.notifyDataSetChanged();
                            }
                          }
                        break;

                    case "APP_TOP_RESTAURANT":
                    case "APP_WHAT_EAT":
                        for (int i = 0; i < dataListEat.size(); i++) {
                            if(dataListEat.get(i).getCode().equals(response.getCodeToSplit())){
                                dataListEat.get(i).setItems(response.getItems());
                                parentDetailBigLocationAdapter.notifyDataSetChanged();
                            }
                        }
                        break;

                    case "APP_TOP_PLAY":
                    case "APP_WHAT_PLAY":
                        for (int i = 0; i < dataListPlay.size(); i++) {
                            if(dataListPlay.get(i).getCode().equals(response.getCodeToSplit())){
                                dataListPlay.get(i).setItems(response.getItems());
                                parentDetailBigLocationAdapter.notifyDataSetChanged();
                            }
                        }
                        break;

                    case "APP_TRAVEL_TIP":
                        travelTip.setItems(response.getItems());
                        parentDetailBigLocationAdapter.notifyDataSetChanged();
                        break;

                    case "APP_VIDEO_HIGH_LIGHT":
                        video.setItems(response.getItems());
                        parentDetailBigLocationAdapter.notifyDataSetChanged();
                        break;
                        
                }
            } else if (o instanceof RegionResponse) {
                RegionResponse response = (RegionResponse) o;
                RegionResponse response1 = (RegionResponse) o;
            }else if (o instanceof WeatherResponse) {
                WeatherResponse response = (WeatherResponse) o;
                parentDetailBigLocationAdapter.setWeatherResponse(response);
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.BIG_LOCATION, TrackingAnalytic.ScreenTitle.BIG_LOCATION);
    }

    @Override
    public void onCallRequest(String link, String code) {
        viewModel.getPartBigLocation(link, code);
        Log.e("linkkk", link);
        Log.e("linkkkCode", code);
    }
}
