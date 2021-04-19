package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityChatBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.f2event.OnReOpenChatScreen;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.f2chat.ChatFragmentV2;
import com.namviet.vtvtravel.view.fragment.f2chat.ReviewChatFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        return new ChatFragmentV2();
//        return new ReviewChatFragment();
    }

    public void initFragment2(){
        try {
            getSupportFragmentManager().beginTransaction().replace(getFrame(), new ChatFragmentV2()).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onReOpenChatScreen(OnReOpenChatScreen onReOpenChatScreen) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                ChatActivity.startScreen(MainActivity.this);
                initFragment2();
            }
        }, 500);
    }

    public static void startScreen(Activity activity) {
        Intent intent = new Intent(activity, ChatActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_in_up, R.anim.activity_out_up);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_in_top);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
