package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityTravelNewsBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2travelnote.DetailNewsTravelFragment;
import com.namviet.vtvtravel.view.fragment.f2travelnote.TravelNewsFragment;

public class TravelNewsActivity extends BaseActivityNew<F2ActivityTravelNewsBinding> {
    public static final String DATA = "data";
    private boolean isTravelNews = true;
    private String detailLink;
    private int screenType;

    public class OpenType {
        public static final int LIST = 0;
        public static final int DETAIL = 1;

    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_travel_news;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        screenType = getIntent().getIntExtra(Constants.IntentKey.SCREEN_TYPE, SmallLocationActivity.OpenType.LIST);
        if (screenType == SmallLocationActivity.OpenType.LIST) {
            isTravelNews = getIntent().getBooleanExtra(DATA, true);
        }else {
            detailLink = getIntent().getStringExtra(Constants.IntentKey.DETAIL_LINK);
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
        if(screenType == OpenType.LIST) {
            return new TravelNewsFragment(isTravelNews);
        }else {
            DetailNewsTravelFragment detailNewsTravelFragment = new DetailNewsTravelFragment();
            detailNewsTravelFragment.setDetailLink(detailLink);
            return detailNewsTravelFragment;
        }
    }

    public static void openScreen(Activity activity, boolean isTravelNews, int screenType) {
        Intent intent = new Intent(activity, TravelNewsActivity.class);
        intent.putExtra(DATA, isTravelNews);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        activity.startActivity(intent);
    }

    public static void openScreenDetail(Activity activity, int screenType, String detailLink ) {
        Intent intent = new Intent(activity, TravelNewsActivity.class);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        intent.putExtra(Constants.IntentKey.DETAIL_LINK, detailLink);
        activity.startActivity(intent);
    }
}
