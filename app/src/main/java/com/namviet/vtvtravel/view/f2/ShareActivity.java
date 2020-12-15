package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityShareBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.share.ContactShareFragment;


public class ShareActivity extends BaseActivityNew<F2ActivityShareBinding> {
    private String title;
    private String link;
    private String image;
    private String contentType;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_share;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        title = getIntent().getStringExtra(Constants.IntentKey.TITLE);
        link = getIntent().getStringExtra(Constants.IntentKey.LINK);
        image = getIntent().getStringExtra(Constants.IntentKey.IMAGE);
        contentType = getIntent().getStringExtra(Constants.IntentKey.CONTENT_TYPE);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new ContactShareFragment(title, link, image, contentType);
    }

    public static void startScreen(Context activity, String title, String link, String image, String contentType) {
        Intent intent = new Intent(activity, ShareActivity.class);
        intent.putExtra(Constants.IntentKey.TITLE, title);
        intent.putExtra(Constants.IntentKey.LINK, link);
        intent.putExtra(Constants.IntentKey.IMAGE, image);
        intent.putExtra(Constants.IntentKey.CONTENT_TYPE, contentType);
        activity.startActivity(intent);
    }
}
