package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Travel;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.view.fragment.PhotoViewFragment;

import java.util.List;

public class SlideMomentAdapter extends FragmentStatePagerAdapter {
    private List<Travel> travelList;

    public SlideMomentAdapter(FragmentManager fm, List<Travel> list) {
        super(fm);
        this.travelList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoViewFragment.newInstance(travelList.get(position));
    }

    @Override
    public int getCount() {
        return travelList == null ? 0 : travelList.size();
    }
}
