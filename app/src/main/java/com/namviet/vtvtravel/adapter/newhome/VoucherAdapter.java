package com.namviet.vtvtravel.adapter.newhome;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.namviet.vtvtravel.model.newhome.Voucher;
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

