package com.namviet.vtvtravel.view.f2.f2oldbase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.travel.DetailWhatEatFragment;
import com.namviet.vtvtravel.view.fragment.travel.DetailWhereFragment;
import com.namviet.vtvtravel.view.fragment.travel.DetailWhereStayFragment;

public class TreeChatBotActivity extends MainActivity {

    private Travel travel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        travel =  getIntent().getParcelableExtra(Constants.IntentKey.DATA);
        if (WSConfig.Api.GET_PLACE.equals(travel.getContent_type())) {
            DetailWhereFragment detailWhereFragment  = new DetailWhereFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
            detailWhereFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, detailWhereFragment).commit();


        } else if (WSConfig.Api.GET_RESTAURANTS.equals(travel.getContent_type())) {
            DetailWhatEatFragment detailWhatEatFragment = new DetailWhatEatFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
            detailWhatEatFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, detailWhatEatFragment).commit();



        } else if (WSConfig.Api.GET_HOTELS.equals(travel.getContent_type())) {
            DetailWhereStayFragment detailWhereStayFragment = new DetailWhereStayFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
            detailWhereStayFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, detailWhereStayFragment).commit();


        } else if (WSConfig.Api.GET_CENTER.equals(travel.getContent_type())) {
            DetailWhereFragment detailWhereFragment  = new DetailWhereFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
            detailWhereFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, detailWhereFragment).commit();
        }
    }

    public static void startScreen(Context activity, Travel travel) {
        Intent intent = new Intent(activity, TreeChatBotActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, travel);
        activity.startActivity(intent);
    }
}
