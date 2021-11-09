package com.namviet.vtvtravel.view.f3.deal.view.dealdetail;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.PagerSnapHelper;

import com.google.android.material.appbar.AppBarLayout;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentDealItemDetailBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.view.f3.deal.adapter.RecyclerAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.DealAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.SubDealHeaderItemAdapter;
import com.namviet.vtvtravel.view.f3.deal.model.dealbycampaign.DealByCampaign;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.DealCampaignDetail;
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel;
import com.namviet.vtvtravel.view.fragment.newhome.NewHomeFragment;
import com.namviet.vtvtravel.viewmodel.f2travelnews.DetailNewsTravelViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class DealItemDetailFragment extends BaseFragment<FragmentDealItemDetailBinding> implements View.OnClickListener, Observer, DealAdapter.LoadData {
    private DealAdapter mDealAdapter;
    private SubDealHeaderItemAdapter adapterBanner;
    private ArrayList<String> dataBanner;
    private String url;
    private DealViewModel mDealViewModel;
    private String urlTab = "https://core-testing.vtvtravel.vn/api/v1/deals/byCampaigns?campaignId=105&isProcessing=0";
    private DealCampaignDetail dealCampaignDetail;
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
    public DealItemDetailFragment(String detailLink) {
        url = detailLink;
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
        dataBanner = new ArrayList<>();
        viewModel = new DetailNewsTravelViewModel();
        viewModel.addObserver(this);
        this.mDealViewModel = new DealViewModel();
        mDealViewModel.addObserver(this);
        mDealViewModel.getDealCampaignDetail(url);
        getBinding().imgBack.setOnClickListener(this);
        getBinding().imgSearch.setOnClickListener(this);
        getBinding().imgMenu.setOnClickListener(this);
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

    }



    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                mActivity.finish();
                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof DealCampaignDetail){
            dealCampaignDetail = (DealCampaignDetail) o;
            mDealAdapter = new DealAdapter(dealCampaignDetail,mActivity,this,DealItemDetailFragment.this);
            mDealAdapter.setILoadDataDeal(new DealAdapter.ILoadDataDeal() {
                @Override
                public void onLoadDataDeal(String url) {
                    mDealViewModel.getDealByCampaign(urlTab);
                }
            });
            getBinding().rcvDetailDeal.setAdapter(mDealAdapter);
            getBinding().tvTitle.setText(dealCampaignDetail.getData().getName());
            for (int i = 0; i < dealCampaignDetail.getData().getGalleryUri().size(); i++){
                dataBanner.add(dealCampaignDetail.getData().getGalleryUri().get(i));
                dataBanner.add(dealCampaignDetail.getData().getGalleryUri().get(i));
                dataBanner.add(dealCampaignDetail.getData().getGalleryUri().get(i));
            }
            initBanner();
        }
        else if(o instanceof DealByCampaign){
            if(dealCampaignDetail == null){
                dealCampaignDetail = new DealCampaignDetail();
            }
            DealByCampaign dealByCampaign = (DealByCampaign) o;
            dealCampaignDetail.setDealByCampaign(dealByCampaign);
            if (isFirstLoad) {
                mDealAdapter.notifyItemChanged(5);
            } else if (mIOnClickTabReloadData != null) {
                mIOnClickTabReloadData.onTabClick(RecyclerAdapter.TypeString.DEAL_HOME);
                isFirstLoad = true;
            }
        }
        else if(o instanceof ErrorResponse){
            mIOnClickTabReloadData.onTabClick(RecyclerAdapter.TypeString.DEAL_HOME);
            isFirstLoad = true;
        }
    }
    private void initBanner() {

        adapterBanner = new SubDealHeaderItemAdapter(mActivity, dataBanner, new SubDealHeaderItemAdapter.ClickItem() {
            @Override
            public void onClickItem(int position) {
                PopupBannerDealActivity.startScreen(mActivity,dataBanner,position);
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
}