package com.namviet.vtvtravel.view.fragment.f2smalllocation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.smalllocation.DetailSmallLocationAdapter;
import com.namviet.vtvtravel.adapter.smalllocation.SmallLocationAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentDetailSmallLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnReviewSuccess;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.f2.SlideImageActivity;
import com.namviet.vtvtravel.view.fragment.imagepart.SlideImageFragment;
import com.namviet.vtvtravel.viewmodel.f2smalllocation.DetailSmallLocationViewModel;
import com.namviet.vtvtravel.viewmodel.f2smalllocation.SmallLocationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.namviet.vtvtravel.ultils.F2Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DetailSmallLocationFragment extends BaseFragment<F2FragmentDetailSmallLocationBinding> implements DetailSmallLocationAdapter.ClickItem, Observer {
    private DetailSmallLocationAdapter detailSmallLocationAdapter;
    private DetailSmallLocationViewModel viewModel;
    private List<DetailSmallLocationResponse.Data.Tab> tabs = new ArrayList<>();
    private List<GetReviewResponse.Data.Content> reviews = new ArrayList<>();
    private String detailLink;


    @SuppressLint("ValidFragment")
    public DetailSmallLocationFragment(String detailLink) {
        this.detailLink = detailLink;
    }

    public DetailSmallLocationFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_small_location;
    }

    @Override
    public void initView() {
        viewModel = new DetailSmallLocationViewModel();
        getBinding().setDetailSmallLocationViewModel(viewModel);
        viewModel.addObserver(this);
        viewModel.getDetailSmallLocation(detailLink, false);
    }

    @Override
    public void initData() {
        detailSmallLocationAdapter = new DetailSmallLocationAdapter(this, tabs, reviews,  mActivity, this);
        getBinding().rclContent.setAdapter(detailSmallLocationAdapter);
    }

    @Override
    public void inject() {

    }

    private void setDataForSomeView(DetailSmallLocationResponse response) {
        F2Util.loadImageToImageView(mActivity, response.getData().getBanner_url(), getBinding().imgBanner);
    }

    private boolean canScroll = false;

    @Override
    public void setClickListener() {
        getBinding().btnShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    F2Util.startSenDataText(mActivity, response.getData().getLink_share());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.SHARE, TrackingAnalytic.getDefault().setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!canScroll) {
                    canScroll = true;
                } else {
                    getBinding().appBar.setExpanded(false, true);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getBinding().rclContent.smoothScrollToPosition(tab.getPosition());
                    }
                }, 500);


                setOnSelectView(getBinding().tabs, tab.getPosition());



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setUnSelectView(getBinding().tabs, tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void setObserver() {

    }


    private void genTab(DetailSmallLocationResponse detailSmallLocationResponse) {
//        getBinding().tabs.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package));
        try {
            for (int i = 0; i < detailSmallLocationResponse.getData().getTabs().size(); i++) {
//                getBinding().tabs.addTab(getBinding().tabs.newTab().setText(detailSmallLocationResponse.getData().getTabs().get(i).getTitle()));

                View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style, null);
                TextView tvHome = tabHome.findViewById(R.id.tvTitle);
                tvHome.setText((detailSmallLocationResponse.getData().getTabs().get(i).getTitle()));
                if (i == 0) {
                    tvHome.setTextColor(Color.parseColor("#00918D"));
                } else {
                    tvHome.setTextColor(Color.parseColor("#101010"));
                }
                View view = tabHome.findViewById(R.id.indicator);
                if (i == 0) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
                getBinding().tabs.addTab(getBinding().tabs.newTab().setCustomView(tabHome));
    //            getBinding().tabs.addTab(getBinding().tabs.newTab().setText(detailSmallLocationResponse.getData().getTabs().get(i).getTitle()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickItem(Travel travel) {

    }

    @Override
    public void onClickRating() {
        addFragment(new RatingSmallLocationFragment(response));
    }

    private DetailSmallLocationResponse response;
    @Override
    public void onClickWriteReview() {
        addFragment(new WriteReviewFragment(response.getData().getId(), response.getData().getContent_type()));
    }

    @Override
    public void onClickImage(int position, List<String> listImage) {
        try {
            SlideImageActivity.startScreen(mActivity, (ArrayList<String>) listImage, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickLinkShare() {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.getData().getLink_share()));
            getContext().startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        getBinding().shimmerMain.setVisibility(View.GONE);
        hideLoading();
        if (observable instanceof DetailSmallLocationViewModel && null != o) {
            if (o instanceof DetailSmallLocationResponse) {
                try {
                    response = (DetailSmallLocationResponse) o;
                    if(!response.isCanReload()) {
                        addRecentView(response);
                        tabs.clear();
                        tabs.addAll(response.getData().getTabs());
                        detailSmallLocationAdapter.setContentType(response.getData().getContent_type());
                        detailSmallLocationAdapter.notifyDataSetChanged();
                        genTab(response);
                        setDataForSomeView(response);
                        viewModel.getReview(response.getData().getId(), null);
                    }else {
                        tabs.clear();
                        tabs.addAll(response.getData().getTabs());
                        try {
                            for (int i = 0; i < this.response.getData().getTabs().size(); i++) {
                                if(this.response.getData().getTabs().get(i).getCode().equals(DetailSmallLocationAdapter.TypeString.TYPE_ITEM_RATING)){
                                    detailSmallLocationAdapter.notifyItemChanged(i);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }if (o instanceof GetReviewResponse) {
                try {
                    GetReviewResponse response = (GetReviewResponse) o;
                    reviews.clear();
                    reviews.addAll(response.getData().getContent());
                    try {
                        for (int i = 0; i < this.response.getData().getTabs().size(); i++) {
                            if(this.response.getData().getTabs().get(i).getCode().equals(DetailSmallLocationAdapter.TypeString.TYPE_ITEM_RATING)){
                                detailSmallLocationAdapter.notifyItemChanged(i);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }
    private void addRecentView(DetailSmallLocationResponse detailSmallLocationResponse){
        try {
            detailSmallLocationResponse.setDetailLink(detailLink);
            ArrayList<DetailSmallLocationResponse> detailSmallLocationResponses;
            String json = PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.RECENT_VIEW, "");
            if(json.isEmpty()){
                detailSmallLocationResponses = new ArrayList<>();
                detailSmallLocationResponses.add(detailSmallLocationResponse);
                PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.RECENT_VIEW, new Gson().toJson(detailSmallLocationResponses));
            }else {
                detailSmallLocationResponses  = new Gson().fromJson(json,
                        new TypeToken<ArrayList<DetailSmallLocationResponse>>() {}.getType());

                for (int i = 0; i < detailSmallLocationResponses.size(); i++) {
                    if(detailSmallLocationResponse.getData().getId().equals(detailSmallLocationResponses.get(i).getData().getId())){
                        return;
                    }
                }

                if(detailSmallLocationResponses.size() >=10){
                    detailSmallLocationResponses.remove(0);
                }

                detailSmallLocationResponses.add(detailSmallLocationResponse);
                PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.RECENT_VIEW, new Gson().toJson(detailSmallLocationResponses));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onReloadReview(OnReviewSuccess onReviewSuccess){
        viewModel.getReview(response.getData().getId(), null);
        viewModel.getDetailSmallLocation(detailLink, true);
    }



    public void setOnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tvTitle);
        View view = selected.findViewById(R.id.indicator);
        view.setVisibility(View.VISIBLE);
        iv_text.setTextColor(Color.parseColor("#00918D"));

    }

    public void setUnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tvTitle);
        View view = selected.findViewById(R.id.indicator);
        view.setVisibility(View.INVISIBLE);
        iv_text.setTextColor(Color.parseColor("#101010"));

    }

}
