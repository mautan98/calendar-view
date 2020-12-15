package com.namviet.vtvtravel.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.daimajia.slider.library.Travel;
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
