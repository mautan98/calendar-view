package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityTopExperienceBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.view.fragment.nearbyexperience.MainNearbyExperienceFragment;
import com.namviet.vtvtravel.view.fragment.topexperience.MainPageTopExperienceFragment;

public class NearbyExperienceActivity extends BaseActivityNew<F2ActivityTopExperienceBinding> {
    private ItemHomeService itemHomeService;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_top_experience;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        itemHomeService = (ItemHomeService) getIntent().getSerializableExtra(Constants.IntentKey.DATA);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new MainNearbyExperienceFragment(itemHomeService);
    }

    public static void startScreen(Context activity, ItemHomeService itemHomeService){
        Intent intent = new Intent(activity, NearbyExperienceActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, itemHomeService);
        activity.startActivity(intent);
    }
}
