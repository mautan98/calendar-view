package com.namviet.vtvtravel.adapter.newhome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.model.newhome.Voucher;
import com.namviet.vtvtravel.view.fragment.PhotoViewFragment;
import com.namviet.vtvtravel.view.fragment.newhome.VoucherViewFragment;

import java.util.List;

public class VoucherAdapter extends FragmentStatePagerAdapter {
    private List<Voucher> voucherList;

    public VoucherAdapter(FragmentManager fm, List<Voucher> list) {
        super(fm);
        this.voucherList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return VoucherViewFragment.newInstance(voucherList.get(position));
    }

    @Override
    public int getCount() {
        return voucherList == null ? 0 : voucherList.size();
    }
}

