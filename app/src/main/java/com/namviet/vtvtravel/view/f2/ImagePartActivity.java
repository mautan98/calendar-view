package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityImagePartBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.imagepart.ImagePartFragment;

public class ImagePartActivity extends BaseActivityNew<F2ActivityImagePartBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_image_part;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new ImagePartFragment();
    }

    public static void startScreen(Activity activity){
        Intent intent = new Intent(activity, ImagePartActivity.class);
        activity.startActivity(intent);
    }
}
