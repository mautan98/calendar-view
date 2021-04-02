package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityTopExperienceBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.response.newhome.ItemAppExperienceResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.view.fragment.topexperience.MainPageTopExperienceFragment;

public class TopExperienceActivity extends BaseActivityNew<F2ActivityTopExperienceBinding> {
    private DetailTravelNewsResponse.Data.PlaceNearBy placeNearBy;
    private int type = 0;
    public static final String TYPE_KEY = "type_key";
    public static final String PLACE_KEY = "place_key";
    public class Type{
        public static final int TOP_EXPERIENCE = 0;
        public static final int RELATION_EXPERIENCE = 1;
        public static final int NEAR_BY_EXPERIENCE = 2;
    }
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
        type = getIntent().getIntExtra(TYPE_KEY, 0);
        itemHomeService = (ItemHomeService) getIntent().getSerializableExtra(Constants.IntentKey.DATA);
        placeNearBy = (DetailTravelNewsResponse.Data.PlaceNearBy) getIntent().getSerializableExtra(PLACE_KEY);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        if(type == 0){
            return new MainPageTopExperienceFragment(itemHomeService, MainPageTopExperienceFragment.Type.FROM_HOME);
        }else {
            return new MainPageTopExperienceFragment(placeNearBy, MainPageTopExperienceFragment.Type.FROM_TRAVEL_NEWS);
        }

    }

    public static void startScreen(Context activity, ItemHomeService itemHomeService, int type){
        Intent intent = new Intent(activity, TopExperienceActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, itemHomeService);
        intent.putExtra(TYPE_KEY, type);
        activity.startActivity(intent);
    }

    public static void startScreenFromTravelNews(Context activity, DetailTravelNewsResponse.Data.PlaceNearBy placeNearBy,  int type){
        Intent intent = new Intent(activity, TopExperienceActivity.class);
        intent.putExtra(TYPE_KEY, type);
        intent.putExtra(PLACE_KEY, placeNearBy);
        activity.startActivity(intent);
    }
}
