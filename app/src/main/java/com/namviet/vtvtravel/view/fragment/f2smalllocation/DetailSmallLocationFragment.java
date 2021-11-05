package com.namviet.vtvtravel.view.fragment.f2smalllocation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.smalllocation.DetailSmallLocationAdapter;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentDetailSmallLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnReviewSuccess;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.ShareActivity;
import com.namviet.vtvtravel.view.f2.SlideImageActivity;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog;
import com.namviet.vtvtravel.view.fragment.share.ContactShareFragment;
import com.namviet.vtvtravel.view.fragment.share.ShareBottomDialog;
import com.namviet.vtvtravel.viewmodel.f2smalllocation.DetailSmallLocationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.widget.AppBarStateChangeListener;
import com.namviet.vtvtravel.widget.OnScrollToPositionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DetailSmallLocationFragment extends BaseFragment<F2FragmentDetailSmallLocationBinding> implements DetailSmallLocationAdapter.ClickItem, Observer {
    private DetailSmallLocationAdapter detailSmallLocationAdapter;
    private DetailSmallLocationViewModel viewModel;
    private List<DetailSmallLocationResponse.Data.Tab> tabs = new ArrayList<>();
    private List<GetReviewResponse.Data.Content> reviews = new ArrayList<>();
    private String detailLink;
    private int ratingPosition = -1;
    private int targetPostion = -1;
    private LinearLayoutManager contentLayoutManager;
    private AppBarStateChangeListener appBarStateChangeListener;
    private boolean changeTabFromScroll = false, changeTabFromClick = false;


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
        contentLayoutManager = new LinearLayoutManager(getContext());
        detailSmallLocationAdapter = new DetailSmallLocationAdapter(this, tabs, reviews, mActivity, this);
        getBinding().rclContent.setAdapter(detailSmallLocationAdapter);
        getBinding().rclContent.setLayoutManager(contentLayoutManager);

        appBarStateChangeListener = new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (State.COLLAPSED.equals(state)) {
                    if (targetPostion >= 0) {
                        contentLayoutManager.scrollToPositionWithOffset(targetPostion, 0);
                        targetPostion = -1;
                    }
                }
            }
        };
        getBinding().appBar.addOnOffsetChangedListener(appBarStateChangeListener);

        getBinding().rclContent.addOnScrollListener(new OnScrollToPositionListener(contentLayoutManager) {
            @Override
            protected void onPositionChange(RecyclerView recyclerView, int position) {
                if (changeTabFromClick) {
                    changeTabFromClick = false;
                    return;
                }
                changeTabFromScroll = true;
                TabLayout.Tab tab = getBinding().tabs.getTabAt(position);
                if (tab != null) {
                    tab.select();
                }
            }
        });
    }

    @Override
    public void initData() {
        viewModel = new DetailSmallLocationViewModel();
        getBinding().setDetailSmallLocationViewModel(viewModel);
        viewModel.addObserver(this);
        viewModel.getDetailSmallLocation(detailLink, false);
//        handleRecycleScroll();
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

                    ShareBottomDialog shareBottomDialog = new ShareBottomDialog(new ShareBottomDialog.DoneClickShare() {
                        @Override
                        public void onDoneClickShare(boolean isVTVApp) {
                            if (isVTVApp) {
                                ShareActivity.startScreen(mActivity, response.getData().getTabs().get(0).getName(), detailLink, response.getData().getBanner_url(), response.getData().getContent_type());
                            } else {
//                                String linkShare = WSConfig.HOST_LANDING + F2Util.genEndPointShareLink(Constants.ShareLinkType.PLACE, detailLink);
//                                F2Util.startSenDataText(mActivity, linkShare);
                                F2Util.startSenDataText(mActivity, response.getData().getLink_share());
                            }
                        }
                    });
                    shareBottomDialog.show(mActivity.getSupportFragmentManager(), null);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.SHARE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SMALL_LOCATION_DETAIL, TrackingAnalytic.ScreenTitle.SMALL_LOCATION_DETAIL)
                            .setContent_id(response.getData().getId())
                            .setContent_type(response.getData().getContent_type())
                            .setScreen_class(this.getClass().getName()));
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
                setOnSelectView(getBinding().tabs, tab.getPosition());
                if (changeTabFromScroll) {
                    changeTabFromScroll = false;
                    return;
                }
                if (!canScroll) {
                    canScroll = true;
                } else {
                    targetPostion = tab.getPosition();
                    if (!AppBarStateChangeListener.State.COLLAPSED.equals(appBarStateChangeListener.getCurrentState())) {
                        getBinding().appBar.setExpanded(false, true);
                    } else {
                        contentLayoutManager.scrollToPositionWithOffset(targetPostion, 0);
                        targetPostion = -1;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setUnSelectView(getBinding().tabs, tab.getPosition());
                if (!changeTabFromScroll) {
                    changeTabFromClick = true;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (!canScroll) {
                    canScroll = true;
                } else {
                    targetPostion = tab.getPosition();
                    if (!AppBarStateChangeListener.State.COLLAPSED.equals(appBarStateChangeListener.getCurrentState())) {
                        getBinding().appBar.setExpanded(false, true);
                    } else {
                        contentLayoutManager.scrollToPositionWithOffset(targetPostion, 0);
                        targetPostion = -1;
                    }
                }
            }
        });

        getBinding().imgHeart.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickHeart();
                    }
                }, 100);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickHeart();
                    }
                }, 100);
            }
        });

//        getBinding().imgHeart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Account account = MyApplication.getInstance().getAccount();
//                    if (null != account && account.isLogin()) {
//                        viewModel.likeEvent(response.getData().getId(), response.getData().getContent_type());
//                        if (response.getData().isLiked()) {
//                            response.getData().setLiked(false);
////                            getBinding().imgHeart.setImageResource(R.drawable.f2_ic_white_border_heart);
//                            getBinding().imgHeart.setLiked(false);
//                        } else {
//                            response.getData().setLiked(true);
////                            getBinding().imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
//                            getBinding().imgHeart.setLiked(true);
//                        }
//
//
//                        try {
//                            TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SMALL_LOCATION_DETAIL, TrackingAnalytic.ScreenTitle.SMALL_LOCATION_DETAIL)
//                                    .setContent_id(response.getData().getId())
//                                    .setContent_type(response.getData().getContent_type())
//                                    .setScreen_class(this.getClass().getName()));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    private void clickHeart() {
        try {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                viewModel.likeEvent(response.getData().getId(), response.getData().getContent_type());
                if (response.getData().isLiked()) {
                    response.getData().setLiked(false);
//                            getBinding().imgHeart.setImageResource(R.drawable.f2_ic_white_border_heart);
                    getBinding().imgHeart.setLiked(false);
                } else {
                    response.getData().setLiked(true);
//                            getBinding().imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
                    getBinding().imgHeart.setLiked(true);
                }


                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SMALL_LOCATION_DETAIL, TrackingAnalytic.ScreenTitle.SMALL_LOCATION_DETAIL)
                            .setContent_id(response.getData().getId())
                            .setContent_type(response.getData().getContent_type())
                            .setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setObserver() {

    }


    private void genTab(DetailSmallLocationResponse detailSmallLocationResponse) {
//        getBinding().tabs.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package));
        try {
            ratingPosition = -1;
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
                if (DetailSmallLocationAdapter.TypeString.TYPE_ITEM_RATING.equals(detailSmallLocationResponse.getData().getTabs().get(i).getCode())) {
                    ratingPosition = i;
                }
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

    @Override
    public void onClickViewRating() {
        if (ratingPosition < 0) {
            return;
        }
        TabLayout.Tab tab = getBinding().tabs.getTabAt(ratingPosition);
        if (tab != null) {
            tab.select();
        }
    }

    private DetailSmallLocationResponse response;

    @Override
    public void onClickWriteReview() {
        String name = "";
        try {
            name = response.getData().getTabs().get(0).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addFragment(new WriteReviewFragment(response.getData().getId(), response.getData().getContent_type(), name));
    }

    @Override
    public void onClickImage(int position, List<String> listImage) {
        try {
            SlideImageActivity.startScreen( mActivity, (ArrayList<String>) listImage, position);
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
                    if (!response.isCanReload()) {
                        addRecentView(response);
                        tabs.clear();
                        tabs.addAll(response.getData().getTabs());
                        detailSmallLocationAdapter.setContentType(response.getData().getContent_type());
                        detailSmallLocationAdapter.notifyDataSetChanged();
                        genTab(response);
                        setDataForSomeView(response);
                        viewModel.getReview(response.getData().getId(), null);
                    } else {
                        tabs.clear();
                        tabs.addAll(response.getData().getTabs());
                        try {
                            for (int i = 0; i < this.response.getData().getTabs().size(); i++) {
                                if (this.response.getData().getTabs().get(i).getCode().equals(DetailSmallLocationAdapter.TypeString.TYPE_ITEM_RATING)) {
                                    detailSmallLocationAdapter.notifyItemChanged(i);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SMALL_LOCATION_DETAIL, TrackingAnalytic.ScreenTitle.SMALL_LOCATION_DETAIL)
                                .setScreen_class(this.getClass().getName())
                                .setContent_id(response.getData().getId())
                                .setCategory_tree_code(response.getData().getTabs().get(0).getCategory_tree_code())
                                .setCategory_tree_name(response.getData().getTabs().get(0).getCategory_tree_name())
                                .setContent_type(response.getData().getContent_type()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (response.getData().isLiked()) {
//                        getBinding().imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
                        getBinding().imgHeart.setLiked(true);
                    } else {
//                        getBinding().imgHeart.setImageResource(R.drawable.f2_ic_white_border_heart);
                        getBinding().imgHeart.setLiked(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (o instanceof GetReviewResponse) {
                try {
                    GetReviewResponse response = (GetReviewResponse) o;
                    reviews.clear();
                    reviews.addAll(response.getData().getContent());
                    try {
                        for (int i = 0; i < this.response.getData().getTabs().size(); i++) {
                            if (this.response.getData().getTabs().get(i).getCode().equals(DetailSmallLocationAdapter.TypeString.TYPE_ITEM_RATING)) {
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

    private void addRecentView(DetailSmallLocationResponse detailSmallLocationResponse) {
        try {
            detailSmallLocationResponse.setDetailLink(detailLink);
            ArrayList<DetailSmallLocationResponse> detailSmallLocationResponses;
            String json = PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.RECENT_VIEW, "");
            if (json.isEmpty()) {
                detailSmallLocationResponses = new ArrayList<>();
                detailSmallLocationResponses.add(detailSmallLocationResponse);
                PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.RECENT_VIEW, new Gson().toJson(detailSmallLocationResponses));
            } else {
                detailSmallLocationResponses = new Gson().fromJson(json,
                        new TypeToken<ArrayList<DetailSmallLocationResponse>>() {
                        }.getType());

                for (int i = 0; i < detailSmallLocationResponses.size(); i++) {
                    if (detailSmallLocationResponse.getData().getId().equals(detailSmallLocationResponses.get(i).getData().getId())) {
                        detailSmallLocationResponses.remove(1);
                        break;
                    }
                }

                if (detailSmallLocationResponses.size() >= 10) {
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
    public void onReloadReview(OnReviewSuccess onReviewSuccess) {
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

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.SMALL_LOCATION_DETAIL, TrackingAnalytic.ScreenTitle.SMALL_LOCATION_DETAIL);
    }

//    private boolean canHandleScroll = true;
//    private void handleRecycleScroll(){
//        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                try {
//                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
//                    int visiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
//
//                    if(visiblePosition != -1){
//                        try {
//                            canScroll = false;
//                            TabLayout.Tab newTab =  getBinding().tabs.getTabAt(visiblePosition);
//                            newTab.select();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

}
