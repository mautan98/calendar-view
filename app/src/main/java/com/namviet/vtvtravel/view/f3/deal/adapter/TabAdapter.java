package com.namviet.vtvtravel.view.f3.deal.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {
    private Context context;
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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


//    public void setOnSelectView(TabLayout tabLayout, int position) {
//        TabLayout.Tab tab = tabLayout.getTabAt(position);
//        View selected = tab.getCustomView();
//        TextView iv_text = selected.findViewById(R.id.tvTitle);
//        View view = selected.findViewById(R.id.rootView);
//        iv_text.setTextColor(ContextCompat.getColor(context, R.color.color_4268F9));
//        view.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_selected_round_suggestion_location));
//        ImageView imageView = selected.findViewById(R.id.imgIcon);
//        imageView.setColorFilter(R.color.white);
//
//
//    }
//
//    public void setUnSelectView(TabLayout tabLayout, int position) {
//        TabLayout.Tab tab = tabLayout.getTabAt(position);
//        View selected = tab.getCustomView();
//        TextView iv_text = selected.findViewById(R.id.tvTitle);
//        View view = selected.findViewById(R.id.rootView);
//        iv_text.setTextColor(Color.parseColor("#8C8C8C"));
//        view.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_unselected_round_suggestion_location));
//        ImageView imageView = selected.findViewById(R.id.imgIcon);
//        imageView.setColorFilter(R.color.black);
//
//    }
}