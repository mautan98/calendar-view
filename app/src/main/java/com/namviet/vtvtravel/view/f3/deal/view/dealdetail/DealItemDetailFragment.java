package com.namviet.vtvtravel.view.f3.deal.view.dealdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.FragmentDealItemDetailBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2booking.DataHelpCenter;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.MyGiftActivity;
import com.namviet.vtvtravel.view.f3.deal.adapter.RecyclerAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.DealAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.SubDealHeaderItemAdapter;

import com.namviet.vtvtravel.view.f3.deal.event.BackToDeal;
import com.namviet.vtvtravel.view.f3.deal.event.FinishDeal;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.DealCampaignDetail;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealHomeActivity;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealMenuDialog;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealSubcribeFragment;
import com.namviet.vtvtravel.view.f3.deal.view.mygift.NewMyGiftActivity;
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel;
import com.namviet.vtvtravel.view.fragment.newhome.NewHomeFragment;
import com.namviet.vtvtravel.viewmodel.f2travelnews.DetailNewsTravelViewModel;
import com.ornach.richtext.RichText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class DealItemDetailFragment extends BaseFragment<FragmentDealItemDetailBinding> implements View.OnClickListener, Observer, DealAdapter.LoadData {
    private DealAdapter mDealAdapter;
    private SubDealHeaderItemAdapter adapterBanner;
    private ArrayList<String> dataBanner;
    private String idDetail;
    private boolean isCampaign;
    private boolean isFromHome;
    private DealViewModel mDealViewModel;
    private DealCampaignDetail dealCampaignDetail;
    private RichText tvMyGif;

    public DealItemDetailFragment() {
        // Required empty public constructor
    }

    private boolean isFirstLoad = true;
    private NewHomeFragment.IOnClickTabReloadData mIOnClickTabReloadData;

    public void setIOnClickTabReloadData(NewHomeFragment.IOnClickTabReloadData mIOnClickTabReloadData) {
        isFirstLoad = false;
        this.mIOnClickTabReloadData = mIOnClickTabReloadData;
    }

    private DetailNewsTravelViewModel viewModel;

    @SuppressLint("ValidFragment")
    public DealItemDetailFragment(String id, boolean isCampaign, boolean isFromHome) {
        this.idDetail = id;
        this.isCampaign = isCampaign;
        this.isFromHome = isFromHome;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_deal_item_detail;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
//        getBinding().rllContent.setVisibility(View.GONE);
        getBinding().shimmerViewContainer.setVisibility(View.VISIBLE);
        getBinding().shimmerViewContainer.startShimmer();
        dataBanner = new ArrayList<>();
        viewModel = new DetailNewsTravelViewModel();
        viewModel.addObserver(this);
        this.mDealViewModel = new DealViewModel();
        mDealViewModel.addObserver(this);
        if (isCampaign) {
            mDealViewModel.getDealCampaignDetail(idDetail);
        } else {
            mDealViewModel.getDealDetail(idDetail);
        }

        getBinding().imgBack.setOnClickListener(this);
        getBinding().imgSearch.setOnClickListener(this);
        getBinding().appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    getBinding().tvTitle.setVisibility(View.GONE);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    getBinding().tvTitle.setVisibility(View.VISIBLE);
                } else {
                    getBinding().tvTitle.setVisibility(View.GONE);
                }
            }
        });
        getBinding().tvMyGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    NewMyGiftActivity.startScreen(mActivity);
                } else {
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }

            }
        });

    }


    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealMenuDialog dealMenuDialog = new DealMenuDialog();
                dealMenuDialog.setFullScreen(true);
                dealMenuDialog.setClickListener(new DealMenuDialog.Click() {
                    @Override
                    public void onClickRule() {
                    }

                    @Override
                    public void onClickSubscribeDeal() {
                        DealSubcribeFragment dealSubcribeFragment = new DealSubcribeFragment();
                        dealSubcribeFragment.setLocation(1);
                        dealSubcribeFragment.setFullScreen(true);
                        addFragment(dealSubcribeFragment);
                    }

                    @Override
                    public void onClickHelpCenter() {
                        DataHelpCenter dataHelpCenter = new Gson().fromJson(F2Util.loadJSONFromAsset(mActivity, "helpcenter_pro"), DataHelpCenter.class);
                        MyGiftActivity.startScreen(mActivity, dataHelpCenter.getItemMenus(), dataHelpCenter.getName());
                    }

                    @Override
                    public void onClickGoDealHome() {
                        if (isFromHome) {
                            try {
                                //  DetailDealWebviewActivity.startScreen(context, homeServiceResponse.getData().get(position).getLink_home_deal());
                                mActivity.onBackPressed();
                                DealHomeActivity.Companion.startScreen(mActivity);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            mActivity.onBackPressed();
                            EventBus.getDefault().post(new BackToDeal());
                        }
                    }

                    @Override
                    public void onClickGoHome() {
                        mActivity.onBackPressed();
                        EventBus.getDefault().post(new FinishDeal());
                    }
                });
                addFragment(dealMenuDialog);
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mActivity.finish();
                break;
        }
    }

    private void stopLoading() {
        getBinding().shimmerViewContainer.stopShimmer();
        getBinding().shimmerViewContainer.setVisibility(View.GONE);
        getBinding().rllContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (o instanceof DealCampaignDetail) {
            dealCampaignDetail = (DealCampaignDetail) o;
            mDealAdapter = new DealAdapter(dealCampaignDetail, mActivity, this, DealItemDetailFragment.this);
            mDealAdapter.setILoadDataDeal(new DealAdapter.ILoadDataDeal() {
                @Override
                public void onLoadDataDeal(String status, String rewardStatus) {
                    mDealViewModel.getDealByCampaign(status, idDetail, rewardStatus);
                }
            });
            try {
                getBinding().rcvDetailDeal.setAdapter(mDealAdapter);
                getBinding().tvTitle.setText(dealCampaignDetail.getData().getName());
                for (int i = 0; i < dealCampaignDetail.getData().getGalleryUri().size(); i++) {
                    dataBanner.add(dealCampaignDetail.getData().getGalleryUri().get(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            initBanner();
            stopLoading();

        } else if (o instanceof DealResponse) {
            if (dealCampaignDetail == null) {
                dealCampaignDetail = new DealCampaignDetail();
            }
            DealResponse dealByCampaign = (DealResponse) o;
            dealCampaignDetail.setDealByCampaign(dealByCampaign);
            if (isFirstLoad) {
                mDealAdapter.notifyItemChanged(5);
            } else if (mIOnClickTabReloadData != null) {
                mIOnClickTabReloadData.onTabClick(RecyclerAdapter.TypeString.DEAL_HOME);
                isFirstLoad = true;
            }
            stopLoading();
        } else if (o instanceof ErrorResponse) {
            mIOnClickTabReloadData.onTabClick(RecyclerAdapter.TypeString.DEAL_HOME);
            isFirstLoad = true;
            stopLoading();
        }
    }

    private void initBanner() {

        adapterBanner = new SubDealHeaderItemAdapter(mActivity, dataBanner, new SubDealHeaderItemAdapter.ClickItem() {
            @Override
            public void onClickItem(int position) {
                PopupBannerDealActivity.startScreen(mActivity, dataBanner, position);
            }
        });
        getBinding().recyclerBanner.setAdapter(adapterBanner);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(getBinding().recyclerBanner);
        getBinding().indicator.attachToRecyclerView(getBinding().recyclerBanner, pagerSnapHelper);
    }

    @Override
    public void onLoadDataComment(String id) {
        String idTest = "5ed9efe18e9c70833d8b45d6";
        viewModel.getComment(idTest);
    }

    @Override
    public void onTabSubDealClick(int position) {

    }

    @Subscribe
    public void onLoginSuccess(OnLoginSuccessAndUpdateUserView onLoginSuccessAndUpdateUserView) {
        showLoading();
        if (isCampaign) {
            mDealViewModel.getDealCampaignDetail(idDetail);
        } else {
            mDealViewModel.getDealDetail(idDetail);
        }
    }
}