package com.namviet.vtvtravel.view.f3.deal.view.dealdetail;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentDealItemDetailBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.DealAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.SubDealHeaderItemAdapter;
import com.namviet.vtvtravel.viewmodel.f2travelnews.DetailNewsTravelViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import me.relex.circleindicator.CircleIndicator2;


public class DealItemDetailFragment extends BaseFragment<FragmentDealItemDetailBinding> implements View.OnClickListener, Observer, DealAdapter.LoadData {
    private DealAdapter mDealAdapter;
    private List<Object> data ;
    private SubDealHeaderItemAdapter adapterBanner;
    private List<Integer> dataBanner;
    public DealItemDetailFragment() {
        // Required empty public constructor
    }
    private DetailNewsTravelViewModel viewModel;
    @SuppressLint("ValidFragment")
    public DealItemDetailFragment(String detailLink) {

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
        viewModel = new DetailNewsTravelViewModel();
        viewModel.addObserver(this);
        getBinding().imgBack.setOnClickListener(this);
        getBinding().imgSearch.setOnClickListener(this);
        getBinding().imgMenu.setOnClickListener(this);
        data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        mDealAdapter = new DealAdapter(data,mActivity,this);
        getBinding().rcvDetailDeal.setAdapter(mDealAdapter);
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
        initBanner();

    }

    private void initBanner() {
        dataBanner = new ArrayList<>();
        dataBanner.add(R.drawable.img_deal_exp);
        dataBanner.add(R.drawable.f2_banner_intro_offline);
        dataBanner.add(R.drawable.img_deal_exp);
        dataBanner.add(R.drawable.f2_banner_intro_offline);
        adapterBanner = new SubDealHeaderItemAdapter(mActivity, dataBanner);
        getBinding().recyclerBanner.setAdapter(adapterBanner);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(getBinding().recyclerBanner);
        getBinding().indicator.attachToRecyclerView(getBinding().recyclerBanner, pagerSnapHelper);
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
        if (o instanceof CommentResponse) {
            CommentResponse response = (CommentResponse) o;
            List<CommentResponse.Data.Comment> comments = response.getData().getContent();
//            if (comments == null) {
//            } else if (comments.size() >= 3) {
//                getBinding().layoutViewAllComment.setVisibility(View.VISIBLE);
//                getBinding().tvCommentLeft.setText("Xem tất cả " + comments.size() + " bình luận");
//            } else {
//                getBinding().layoutViewAllComment.setVisibility(View.GONE);
//            }
            Log.e("xxx", "update: "+response.getData().getContent().size() );
            data.set(5,response);
            mDealAdapter.notifyItemChanged(5);
        }
    }

    @Override
    public void onLoadDataComment(String id) {
        String idTest = "5ed9efe18e9c70833d8b45d6";
        viewModel.getComment(idTest);
    }
}