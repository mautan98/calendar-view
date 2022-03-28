package com.namviet.vtvtravel.view.f3.deal.view.mygift;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentMyGiftBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.F3MyGiftAdapter;

public class MyGiftFragment extends BaseFragment<FragmentMyGiftBinding> {
    int size =1;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_my_gift;
    }
    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        showShimmerLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideShimmerLoading();
                F3MyGiftAdapter f3MyGiftAdapter = new F3MyGiftAdapter(mActivity,null,null);
                getBinding().rcvMyGift.setAdapter(f3MyGiftAdapter);
                f3MyGiftAdapter.notifyDataSetChanged();
            }
        },1000);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
        getBinding().imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "Search!", Toast.LENGTH_SHORT).show();
            }
        });
        getBinding().btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }

    @Override
    public void setObserver() {

    }
    private void showShimmerLoading(){
        getBinding().rcvMyGift.setVisibility(View.GONE);
        getBinding().shimmerViewContainer.setVisibility(View.VISIBLE);
        getBinding().rllEmpty.setVisibility(View.GONE);
    }
    private void hideShimmerLoading(){
        getBinding().rcvMyGift.setVisibility(View.VISIBLE);
        getBinding().shimmerViewContainer.setVisibility(View.GONE);
        if(size == 0){
            getBinding().rllEmpty.setVisibility(View.VISIBLE);
        }
        else getBinding().rllEmpty.setVisibility(View.GONE);
    }

}
