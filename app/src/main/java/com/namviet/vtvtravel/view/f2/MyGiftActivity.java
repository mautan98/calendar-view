package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityMyGiftBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.view.fragment.f2mygift.MyGiftFragment;

import java.util.ArrayList;
import java.util.List;

//
public class MyGiftActivity extends BaseActivityNew<F2ActivityMyGiftBinding> {
    private List<MenuItem> menuItems;
    private String title;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_my_gift;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        menuItems = (List<MenuItem>) getIntent().getSerializableExtra(Constants.IntentKey.DATA);
        title = getIntent().getStringExtra(Constants.IntentKey.CODE);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new MyGiftFragment(menuItems, title);
    }

    public static void startScreen(Context activity, ArrayList<MenuItem> menuItems, String title) {
        Intent intent = new Intent(activity, MyGiftActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, menuItems);
        intent.putExtra(Constants.IntentKey.CODE, title);
        activity.startActivity(intent);
    }
}
