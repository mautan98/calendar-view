package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.namviet.vtvtravel.model.NearItem;
import com.namviet.vtvtravel.view.fragment.nofity.SystemFragment;

import java.util.List;

public class NotifyPageAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private List<NearItem> arrTab;


    public NotifyPageAdapter(FragmentManager fm, Context context, List<NearItem> arrTab) {
        super(fm);
        this.arrTab = arrTab;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return SystemFragment.newInstance(arrTab.get(position));
    }

    @Override
    public int getCount() {
        return arrTab.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrTab.get(position).getName();
    }
}
