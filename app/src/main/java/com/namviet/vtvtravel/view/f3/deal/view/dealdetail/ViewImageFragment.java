package com.namviet.vtvtravel.view.f3.deal.view.dealdetail;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

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
    private int positionSelected;
    @SuppressLint("ValidFragment")
    public ViewImageFragment(List<String> detailLink, int i) {
        urls = detailLink;
        positionSelected = i;
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
        dealSlideImageAdapter = new DealSlideImageAdapter(mActivity, urls);
        getBinding().vpContent.setAdapter(dealSlideImageAdapter);
        dealSmallImageAdapter = new DealSmallImageAdapter(mActivity,urls);
        getBinding().rclContent.setAdapter(dealSmallImageAdapter);
        dealSmallImageAdapter.setClickSubItem(new DealSmallImageAdapter.ClickSubItem() {
            @Override
            public void onClickSubItem(int position) {
                getBinding().vpContent.setCurrentItem(position);
            }
        });
        getBinding().vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dealSmallImageAdapter.position_selected = position;
                dealSmallImageAdapter.notifyDataSetChanged();
                getBinding().tvCount.setText((position+1)+"/"+urls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if(urls.size()>0){
            getBinding().tvCount.setText(1+"/"+urls.size());
        }
        else getBinding().tvCount.setText(0+"/"+0);
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        getBinding().vpContent.setCurrentItem(positionSelected);
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
