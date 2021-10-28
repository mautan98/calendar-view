 package com.namviet.vtvtravel.view.f3.deal.view.dealhome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.newhome.NewHomeAdapter;
import com.namviet.vtvtravel.databinding.FragmentDealHomeChildBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3Header1Adapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3Header1Adapter2;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3Header2Adapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3Header2Adapter2;
import com.namviet.vtvtravel.view.f3.deal.adapter.RecyclerAdapter;
import com.namviet.vtvtravel.view.f3.deal.model.Block;
import com.namviet.vtvtravel.view.f3.deal.model.OnClickTabHeader1;
import com.namviet.vtvtravel.view.f3.deal.model.OnClickTabHeader2;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel;
import com.namviet.vtvtravel.view.fragment.newhome.NewHomeFragment;
import com.namviet.vtvtravel.widget.PreCachingLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

 public class DealHomeChildFragment extends BaseFragment<FragmentDealHomeChildBinding> implements Observer, RecyclerAdapter.IOnTabHotClick {


    private RecyclerAdapter adapter;
    private F3Header1Adapter2 f3Header1Adapter2;
    private F3Header2Adapter2 f3Header2Adapter2;
    private RecyclerView rclHeader1;
    private RecyclerView rclHeader2;
     private DealViewModel mDealViewModel;
     private boolean isFirtLoad = true;
    private ArrayList<Block> blocksMenuHeader1 = new ArrayList<>();
    private ArrayList<Block> blocksMenuHeader2 = new ArrayList<>();
     private NewHomeFragment.IOnClickTabReloadData mIOnClickTabReloadData;
     public void setmIOnClickTabReloadData(NewHomeFragment.IOnClickTabReloadData mIOnClickTabReloadData) {
         isFirtLoad = false;
         this.mIOnClickTabReloadData = mIOnClickTabReloadData;
     }
    private String urlDeal = "https://core-testing.vtvtravel.vn/api/v1/deals/home?size=1&page=0";
     public DealHomeChildFragment() {
     }



     public void setData(ArrayList<Block> blocksMenuHeader1, ArrayList<Block> blocksMenuHeader2){
         this.blocksMenuHeader1 = blocksMenuHeader1;
         this.blocksMenuHeader2 = blocksMenuHeader2;
         this.mDealViewModel = new DealViewModel();
         mDealViewModel.addObserver(this);
     }

     @Override
    public int getLayoutRes() {
        return R.layout.fragment_deal_home_child;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        f3Header1Adapter2 = new F3Header1Adapter2(0, blocksMenuHeader1, mActivity, new F3Header1Adapter.ClickTab() {
            @Override
            public void onClickTab(int position) {
                Log.e("xxx", "onTab1Click: "+blocksMenuHeader1.get(position).getCode() );
                adapter.setPositionSelected1(position);
                adapter.notifyItemChanged(0);
                // refresh flag
                blocksMenuHeader1.get(0).setDataLoaded(false);
                adapter.notifyItemChanged(1);
            }
        }, true);
        f3Header2Adapter2 = new F3Header2Adapter2(0, blocksMenuHeader2, mActivity, new F3Header2Adapter.ClickTab() {
            @Override
            public void onClickTab(int position) {
                Log.e("xxx", "onTab2Click: "+blocksMenuHeader2.get(position).getCode() );
                adapter.setPositionSelected2(position);
                adapter.notifyItemChanged(0);
//                blocksMenuHeader1.get(0).setDataLoaded(false);
//                adapter.notifyItemChanged(1);

            }
        }, false);
        adapter = new RecyclerAdapter();
        adapter.setData(ItemGenerator.demoList(), mActivity, blocksMenuHeader1, blocksMenuHeader2,this);
        adapter.setILoadDataDeal(new RecyclerAdapter.ILoadDataDeal() {
            @Override
            public void onLoadDataDeal(String url) {
                Log.e("xxx", "onLoadDataDeal: "+url );
                mDealViewModel.getDeal(urlDeal);
            }
        });
        adapter.setiOnTabHotClick(this);
        StickyLayoutManager layoutManager = new TopSnappedStickyLayoutManager(mActivity, adapter);
        layoutManager.elevateHeaders(false); // Default elevation of 5dp
        // You can also specify a specific dp for elevation
//        layoutManager.elevateHeaders(10);
        getBinding().recyclerView.setLayoutManager(layoutManager);
        getBinding().recyclerView.setAdapter(adapter);

        layoutManager.setStickyHeaderListener(new StickyHeaderListener() {
            @Override
            public void headerAttached(View headerView, int adapterPosition) {
                Log.d("Listener", "Attached with position: " + adapterPosition);
                if(adapterPosition == 0){
                    rclHeader1 = headerView.findViewById(R.id.rcv_tab_header1);
                    rclHeader1.setAdapter(f3Header1Adapter2);
                }else if(adapterPosition == 2){
                    rclHeader2 = headerView.findViewById(R.id.rcv_tab_header1);
                    rclHeader2.setAdapter(f3Header2Adapter2);
                }
            }

            @Override
            public void headerDetached(View headerView, int adapterPosition) {
                Log.d("Listener", "Detached with position: " + adapterPosition);
            }
        });
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onClickTabHeader1(OnClickTabHeader1 onClickTabHeader1){
        f3Header1Adapter2.setSelectedItem(onClickTabHeader1.getPosition());
//        mDealViewModel.getDeal(urlDeal);
//        mDealViewModel.getDeal(blocksMenuHeader1.get(onClickTabHeader1.getPosition()).getLink());

    }

    @Subscribe
    public void onClickTabHeader2(OnClickTabHeader2 onClickTabHeader1){
        f3Header2Adapter2.setSelectedItem(onClickTabHeader1.getPosition());
    }

     @Override
     public void update(Observable observable, Object o) {
         if (o instanceof DealResponse) {
             blocksMenuHeader1.get(0).setDealResponse((DealResponse) o);
             if (isFirtLoad) {
                 adapter.notifyItemChanged(1);
             } else if (mIOnClickTabReloadData != null) {
                 mIOnClickTabReloadData.onTabClick(RecyclerAdapter.TypeString.DEAL_HOME);
                 isFirtLoad = true;
             }
         }
     }

     @Override
     public void onTab1Click(int position) {
         f3Header1Adapter2.setSelectedItem(position);
         blocksMenuHeader1.get(0).setDataLoaded(false);
         Log.e("xxx", "onTab1Click: "+blocksMenuHeader1.get(position).getCode() );
      //   mDealViewModel.getDeal(urlDeal);
         adapter.notifyItemChanged(1);
     }
     @Override
     public void onTab2Click(int position) {
         f3Header2Adapter2.setSelectedItem(position);
      //   Log.e("xxx", "onTab2Click: "+blocksMenuHeader2.get(position).getCode() );
        // adapter.notifyItemChanged(1);
        // mDealViewModel.getDeal(urlDeal);
     }
 }
