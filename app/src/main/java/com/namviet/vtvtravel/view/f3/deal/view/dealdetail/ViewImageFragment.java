package com.namviet.vtvtravel.view.f3.deal.view.dealdetail;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentViewImageBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.DealSlideImageAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.DealSmallImageAdapter;

public class ViewImageFragment extends BaseFragment<FragmentViewImageBinding> {
    private DealSlideImageAdapter dealSlideImageAdapter;
    private DealSmallImageAdapter dealSmallImageAdapter;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_view_image;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        dealSlideImageAdapter = new DealSlideImageAdapter(mActivity, null, new DealSlideImageAdapter.ClickItem() {
            @Override
            public void onClickItem() {

            }
        });
        getBinding().vpContent.setAdapter(dealSlideImageAdapter);

        dealSmallImageAdapter = new DealSmallImageAdapter(mActivity);
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
