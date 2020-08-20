package com.namviet.vtvtravel.view.fragment.f2smalllocation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;

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
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
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
        viewModel.getDetailSmallLocation(detailLink);
    }

    @Override
    public void initData() {
        detailSmallLocationAdapter = new DetailSmallLocationAdapter(tabs, reviews,  mActivity, this);
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

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

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
        getBinding().tabs.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package));
        for (int i = 0; i < detailSmallLocationResponse.getData().getTabs().size(); i++) {
            getBinding().tabs.addTab(getBinding().tabs.newTab().setText(detailSmallLocationResponse.getData().getTabs().get(i).getTitle()));
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
        addFragment(new WriteReviewFragment(response.getData().getId()));
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
                    addRecentView(response);
                    tabs.clear();
                    tabs.addAll(response.getData().getTabs());
                    detailSmallLocationAdapter.setContentType(response.getData().getContent_type());
                    detailSmallLocationAdapter.notifyDataSetChanged();
                    genTab(response);
                    setDataForSomeView(response);
                    viewModel.getReview(response.getData().getId(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }if (o instanceof GetReviewResponse) {
                try {
                    GetReviewResponse response = (GetReviewResponse) o;
                    reviews.clear();
                    reviews.addAll(response.getData().getContent());
                    detailSmallLocationAdapter.notifyDataSetChanged();
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
            }else {
                detailSmallLocationResponses  = new Gson().fromJson(json,
                        new TypeToken<ArrayList<DetailSmallLocationResponse>>() {}.getType());
            }
            detailSmallLocationResponses.add(detailSmallLocationResponse);
            PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.RECENT_VIEW, new Gson().toJson(detailSmallLocationResponses));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

    }

}
