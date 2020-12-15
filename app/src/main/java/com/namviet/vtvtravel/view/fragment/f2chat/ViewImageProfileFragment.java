package com.namviet.vtvtravel.view.fragment.f2chat;

import android.annotation.SuppressLint;
import android.view.View;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2LayoutViewImageProfileBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;

public class ViewImageProfileFragment extends BaseFragment<F2LayoutViewImageProfileBinding> implements View.OnClickListener {
    private String urlImage;
    public ViewImageProfileFragment() {}
    @SuppressLint("ValidFragment")
    public ViewImageProfileFragment(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_layout_view_image_profile;
    }

    @Override
    public void initView() {
        try {
            if (!"".equals(urlImage)) {
                Glide.with(mActivity).load(urlImage).into(getBinding().imgProfile);
            } else {
                getBinding().imgProfile.setImageResource(R.drawable.f2_defaut_user);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().imgBack.setOnClickListener(this);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                mActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.VIEW_IMAGE_PROFILE, TrackingAnalytic.ScreenTitle.VIEW_IMAGE_PROFILE);
    }
}
