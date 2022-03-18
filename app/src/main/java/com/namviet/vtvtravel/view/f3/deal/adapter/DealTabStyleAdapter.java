package com.namviet.vtvtravel.view.f3.deal.adapter;


import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;

import java.util.ArrayList;
import java.util.List;

public class DealTabStyleAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public DealTabStyleAdapter(FragmentManager fm) {
        super(fm);
    }


    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public String getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }


    public void SetOnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tv_title);
//        View view = selected.findViewById(R.id.indicator);
//        view.setVisibility(View.VISIBLE);
        iv_text.setTextColor(Color.parseColor("#00918D"));

    }

    public void SetUnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tv_title);
//        View view = selected.findViewById(R.id.indicator);
//        view.setVisibility(View.INVISIBLE);
        iv_text.setTextColor(Color.parseColor("#101010"));

    }
}