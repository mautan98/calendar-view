package com.namviet.vtvtravel.view.fragment.travel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.LiveChannelAdapter;
import com.namviet.vtvtravel.help.LiveChannelEvent;
import com.namviet.vtvtravel.model.LiveChannel;
import com.namviet.vtvtravel.model.channel.LiveChannelData;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.LiveChannelViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class LiveChannelFragment extends MainFragment implements LiveChannelAdapter.OnItemLiveChannelClick {
    private LiveChannelViewModel mLiveChannelViewModel;
    private List<LiveChannel> mLiveChannelList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LiveChannelAdapter mAdapter;
    private ImageView mBackLiveChannelImv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_channel, container, false);

        mBackLiveChannelImv = (ImageView) view.findViewById(R.id.imv_back_live_channel);
        mBackLiveChannelImv.setOnClickListener(view1 -> mActivity.onBackPressed());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_live_channel);
        mAdapter = new LiveChannelAdapter(mLiveChannelList, mActivity, this);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLiveChannelViewModel = ViewModelProviders.of(this).get(LiveChannelViewModel.class);
        mLiveChannelViewModel.init();
        mLiveChannelViewModel.getMovies().observe(this, new Observer<LiveChannelData>() {
            @Override
            public void onChanged(@Nullable LiveChannelData liveChannelData) {
                assert liveChannelData != null;
                mLiveChannelList.addAll(liveChannelData.getData());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void listener(LiveChannel liveChannel) {
        EventBus.getDefault()
                .post(new LiveChannelEvent(liveChannel));
        mActivity.onBackPressed();
    }
}
