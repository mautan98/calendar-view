package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.Utils;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class BannerDealAdapter extends PagerAdapter {

    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.f3_banner_deal_exp,
                    "Strategy"
            ),
            new Utils.LibraryObject(
                    R.drawable.img_deal_exp,
                    "Design"
            ),
            new Utils.LibraryObject(
                    R.drawable.f3_banner_deal_exp,
                    "Development"
            ),
            new Utils.LibraryObject(
                    R.drawable.img_deal_exp,
                    "Quality Assurance"
            )
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private boolean mIsTwoWay;

    public BannerDealAdapter(final Context context, final boolean isTwoWay) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIsTwoWay = isTwoWay;
    }

    @Override
    public int getCount() {
        return mIsTwoWay ? 6 : LIBRARIES.length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
            view = mLayoutInflater.inflate(R.layout.f3_item_banner_deal, container, false);
            Utils.setupItem(view, LIBRARIES[position]);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
