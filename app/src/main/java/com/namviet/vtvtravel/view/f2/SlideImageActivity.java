package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivitySlideImageBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.imagepart.ImagePartFragment;
import com.namviet.vtvtravel.view.fragment.imagepart.SlideImageFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SlideImageActivity extends BaseActivityNew<F2ActivitySlideImageBinding> {
    private static final String IMAGE_LIST = "IMAGE_LIST";
    private static final String POSITION = "POSITION";

    private ArrayList<String> imageList;
    private int position;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_slide_image;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        imageList = getIntent().getStringArrayListExtra(IMAGE_LIST);
        position = getIntent().getIntExtra(POSITION, 0);
    }

    @Override
    public void doAfterOnCreate() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void afterSetContentView() {
        super.afterSetContentView();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        SlideImageFragment fragment = new SlideImageFragment();
        fragment.setImageList(imageList);
        fragment.setPosition(position);
        return fragment;
    }

    public static void startScreen(Context activity, ArrayList<String> imageList, int position) {
        Intent intent = new Intent(activity, SlideImageActivity.class);
        intent.putStringArrayListExtra(IMAGE_LIST, imageList);
        intent.putExtra(POSITION, position);
        activity.startActivity(intent);
    }
}
