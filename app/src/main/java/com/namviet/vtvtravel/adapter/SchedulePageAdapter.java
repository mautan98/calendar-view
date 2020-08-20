package com.namviet.vtvtravel.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.namviet.vtvtravel.view.fragment.home.SchedulePlayFragment;
import com.namviet.vtvtravel.view.fragment.home.ScheduleShoppingFragment;
import com.namviet.vtvtravel.view.fragment.home.ScheduleEatFragment;

import java.util.ArrayList;

public class SchedulePageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragmentList;

    public SchedulePageAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);

    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
