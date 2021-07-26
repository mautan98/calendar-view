package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.namviet.vtvtravel.R;

import java.util.ArrayList;

/**
 * Created by 1 on 12/13/2017.
 */

public class AccountPageAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private ArrayList<Fragment> arrTab;



    public AccountPageAdapter(FragmentManager fm, Context context, ArrayList<Fragment> arrTab) {
        super(fm);
        this.arrTab = arrTab;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        return arrTab.get(position);
    }

    @Override
    public int getCount() {
        return arrTab.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.acc_video);
            case 1:
                return mContext.getString(R.string.acc_schedule);
            case 2:
                return mContext.getString(R.string.acc_tour);
        }
        return "";
    }
}
