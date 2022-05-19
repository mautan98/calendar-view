package com.namviet.vtvtravel.view.f3.deal.view.mygift;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentMyGiftBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail.DealAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.F3MyGiftAdapter;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.DealCampaignDetail;
import com.namviet.vtvtravel.view.f3.deal.model.mygift.MyGift;
import com.namviet.vtvtravel.view.f3.deal.model.mygift.MyGiftResponse;
import com.namviet.vtvtravel.view.f3.deal.view.dealdetail.DealItemDetailFragment;
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyGiftFragment extends BaseFragment<FragmentMyGiftBinding> implements Observer {

    private ArrayList<MyGift> myGifts = new ArrayList<>();
    private F3MyGiftAdapter f3MyGiftAdapter;
    private DealViewModel dealViewModel;
    private int page = 0;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_my_gift;
    }


    @Override
    public void initView() {
        dealViewModel =  new DealViewModel();
        dealViewModel.addObserver(this);
        dealViewModel.getAllMyGift(page);
        showShimmerLoading();
    }

    public void getData(){
        dealViewModel.getAllMyGift(page);
    }

    @Override
    public void initData() {
        f3MyGiftAdapter = new F3MyGiftAdapter(mActivity, myGifts, new F3MyGiftAdapter.Data() {
            @Override
            public void onData(boolean isShow) {
                if(isShow){
                    getBinding().rllNoData.setVisibility(View.VISIBLE);
                }else {
                    getBinding().rllNoData.setVisibility(View.GONE);
                }
            }
        });
        getBinding().rcvMyGift.setAdapter(f3MyGiftAdapter);

        getBinding().rcvMyGift.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                Log.e("newState", newState+"");
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)) {
                    dealViewModel.getAllMyGift(page);
                }
            }
        });
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
        getBinding().rllNoData.findViewById(R.id.btn_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dealViewModel.getAllMyGift(page);
                showShimmerLoading();
            }
        });
    }

    @Override
    public void setObserver() {

    }

    private void showShimmerLoading() {
//        getBinding().rcvMyGift.setVisibility(View.GONE);
        getBinding().shimmerViewContainer.setVisibility(View.VISIBLE);
//        getBinding().rllEmpty.setVisibility(View.GONE);
    }

    private void hideShimmerLoading() {
//        getBinding().rcvMyGift.setVisibility(View.VISIBLE);
        getBinding().shimmerViewContainer.setVisibility(View.GONE);
//        if(size == 0){
//            getBinding().rllEmpty.setVisibility(View.VISIBLE);
//        }
//        else getBinding().rllEmpty.setVisibility(View.GONE);
    }

    @Override
    public void update(Observable observable, Object o) {
        hideShimmerLoading();
        try {
            if(observable instanceof DealViewModel){
                MyGiftResponse myGiftResponse = (MyGiftResponse) o;
//                myGifts.clear();
                if(myGiftResponse.getData().getMyGifts().size() > 0){
                    page = page + 1;
                    myGifts.addAll(myGiftResponse.getData().getMyGifts());
                }
                f3MyGiftAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
