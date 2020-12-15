package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2chat.ReviewChatFragment;

public class ReviewChatActivity extends BaseActivityNew {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_review_chat;
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
        return new ReviewChatFragment();
    }

    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, ReviewChatActivity.class);
        activity.startActivity(intent);
    }
}
