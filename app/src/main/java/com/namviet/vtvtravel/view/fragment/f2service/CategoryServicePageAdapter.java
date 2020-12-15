package com.namviet.vtvtravel.view.fragment.f2service;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import java.util.List;

public class CategoryServicePageAdapter extends FragmentStatePagerAdapter {
    private List<Service> list;


    public CategoryServicePageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<Service> list) {
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {

        return ServiceTabFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
