package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;
import android.net.UrlQuerySanitizer;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityImagePartBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.imagepart.HighLightestImagesFragment;
import com.namviet.vtvtravel.view.fragment.imagepart.ImagePartFragment;

public class ImagePartActivity extends BaseActivityNew<F2ActivityImagePartBinding> {
    private int type;
    private String galleryId;

    public class Type {
        public static final int LIST = 0;
        public static final int DETAIL = 1;
    }

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
        type = getIntent().getIntExtra(Constants.IntentKey.SCREEN_TYPE, Type.LIST);
        if(type == Type.DETAIL){
            String link = getIntent().getStringExtra(Constants.IntentKey.DATA);
            galleryId = new UrlQuerySanitizer(link).getValue("gallery_id");
        }
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        if(type == Type.LIST) {
            return new ImagePartFragment();
        }else {
            return new HighLightestImagesFragment(galleryId);
        }
    }

    public static void startScreen(Activity activity) {
        Intent intent = new Intent(activity, ImagePartActivity.class);
        activity.startActivity(intent);
    }

    public static void startScreen(Activity activity, int type, String galleryId) {
        Intent intent = new Intent(activity, ImagePartActivity.class);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, type);
        intent.putExtra(Constants.IntentKey.DATA, galleryId);
        activity.startActivity(intent);
    }
}
