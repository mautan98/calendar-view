package com.namviet.vtvtravel.view.f3.notification.adapter;


import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;
import com.ornach.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

public class NotificationTabAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public NotificationTabAdapter(FragmentManager fm) {
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


    public void setOnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        RichText iv_text = selected.findViewById(R.id.tvTitle);
        iv_text.setTextColor(Color.parseColor("#FFFFFF"));
        iv_text.setBackgroundColor(Color.parseColor("#00918D"));

    }

    public void setUnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        RichText iv_text = selected.findViewById(R.id.tvTitle);
        iv_text.setTextColor(Color.parseColor("#000000"));
        iv_text.setBackgroundColor(Color.parseColor("#ECECEC"));

    }
}