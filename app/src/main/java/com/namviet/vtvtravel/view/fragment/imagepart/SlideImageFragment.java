package com.namviet.vtvtravel.view.fragment.imagepart;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.imagepart.SlideImageInHighLightestImageAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentSlideImageBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;

import java.util.List;

public class SlideImageFragment extends BaseFragment<F2FragmentSlideImageBinding> {
    private List<String> imageList;
    private int position;
    private SlideImageInHighLightestImageAdapter slideImageInHighLightestImageAdapter;

    public SlideImageFragment() {
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_slide_image;
    }

    @Override
    public void initView() {
        slideImageInHighLightestImageAdapter = new SlideImageInHighLightestImageAdapter(mActivity, imageList, SlideImageInHighLightestImageAdapter.DETAIL_TYPE);
        getBinding().vpGallery.setAdapter(slideImageInHighLightestImageAdapter);
        try {
            String currentPosition = String.valueOf(position + 1);
            getBinding().tvPosition.setText(currentPosition + "/" + imageList.size());
            getBinding().vpGallery.setCurrentItem(position);
        } catch (Exception e) {
            getBinding().tvPosition.setText("0/0");
        }

        getBinding().vpGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    getBinding().tvPosition.setText((String.valueOf(position + 1) + "/" + imageList.size()));
                } catch (Exception e) {
                    getBinding().tvPosition.setText("0/0");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getBinding().imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

    @Override
    public void initData() {

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
