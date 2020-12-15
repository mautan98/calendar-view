package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.SupportMapFragment;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityDisplayMarkerForWebviewBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.help.MySupportMapFragment;
import com.namviet.vtvtravel.view.fragment.displaymapforwebview.DisplayMarkerForWebviewFragment;

//
public class DisplayMarkerForMapActivity extends BaseActivityNew<F2ActivityDisplayMarkerForWebviewBinding> {
    private String link;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_display_marker_for_webview;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        link = getIntent().getStringExtra(Constants.IntentKey.LINK);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new DisplayMarkerForWebviewFragment(link);
    }

    public static void startScreen(Context activity, String link) {
        Intent intent = new Intent(activity, DisplayMarkerForMapActivity.class);
        intent.putExtra(Constants.IntentKey.LINK, link);
        activity.startActivity(intent);
    }
}
