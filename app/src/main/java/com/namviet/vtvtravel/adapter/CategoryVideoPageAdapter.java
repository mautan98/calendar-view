package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.namviet.vtvtravel.response.CategoryItem;
import com.namviet.vtvtravel.view.fragment.home.VideoTabFragment;

import java.util.List;


public class CategoryVideoPageAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private List<CategoryItem> list;


    public CategoryVideoPageAdapter(FragmentManager fm, Context context, List<CategoryItem> list) {
        super(fm);
        this.list = list;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        return VideoTabFragment.newInstance(list.get(position));
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
