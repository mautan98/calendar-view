package com.namviet.vtvtravel.view.f3.deal.view.dealdetail;

import android.annotation.SuppressLint;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentViewImageBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.DealSlideImageAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.DealSmallImageAdapter;

import java.util.List;

public class ViewImageFragment extends BaseFragment<FragmentViewImageBinding> {
    private DealSlideImageAdapter dealSlideImageAdapter;
    private DealSmallImageAdapter dealSmallImageAdapter;
    private List<String> urls;
    @SuppressLint("ValidFragment")
    public ViewImageFragment(List<String> detailLink) {
        urls = detailLink;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_view_image;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        dealSlideImageAdapter = new DealSlideImageAdapter(mActivity, urls, new DealSlideImageAdapter.ClickItem() {
            @Override
            public void onClickItem() {

            }
        });
        getBinding().vpContent.setAdapter(dealSlideImageAdapter);

        dealSmallImageAdapter = new DealSmallImageAdapter(mActivity,urls);
        getBinding().rclContent.setAdapter(dealSmallImageAdapter);
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
}
