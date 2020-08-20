package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityChatBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2chat.ChatFragment;

public class ChatActivity extends BaseActivityNew<F2ActivityChatBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_chat;
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
        return new ChatFragment();
    }

    public static void startScreen(Activity activity) {
        Intent intent = new Intent(activity, ChatActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_in_up, R.anim.activity_out_up);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in_up, R.anim.activity_out_up);
    }
}
