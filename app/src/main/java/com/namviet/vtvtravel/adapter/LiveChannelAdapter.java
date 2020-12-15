package com.namviet.vtvtravel.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.LiveChannel;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.List;

public class LiveChannelAdapter extends RecyclerView.Adapter<LiveChannelAdapter.MyViewHolder>{
    private List<LiveChannel> mLiveChannelList;
    private MainActivity mMainActivity;
    private OnItemLiveChannelClick mOnItemLiveChannelClick;

    public LiveChannelAdapter(List<LiveChannel> moviesList, MainActivity mainActivity, OnItemLiveChannelClick mOnItemLiveChannelClick) {
        this.mLiveChannelList = moviesList;
        this.mMainActivity = mainActivity;
        this.mOnItemLiveChannelClick = mOnItemLiveChannelClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_channel, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LiveChannel liveChannel = mLiveChannelList.get(position);
        String urlBanner = liveChannel.getBanner_url();
        String urlLogo = liveChannel.getLogo_url();
        Glide.with(mMainActivity)
                .load(urlBanner)
                .into(holder.mBannerLiveChannelImv);
        Glide.with(mMainActivity)
                .load(urlLogo)
                .into(holder.mLogoLiveChannelImv);

        holder.mItemLiveChannelLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemLiveChannelClick.listener(mLiveChannelList.get(position));
            }
        });

    }

    public interface OnItemLiveChannelClick{
        void listener(LiveChannel liveChannel);
    }
    
    @Override
    public int getItemCount() {
        return mLiveChannelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView mBannerLiveChannelImv;
        private ImageView mLogoLiveChannelImv;
        private LinearLayout mItemLiveChannelLinear;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBannerLiveChannelImv = (ImageView) itemView.findViewById(R.id.imv_banner_live_channel);
            mLogoLiveChannelImv = (ImageView) itemView.findViewById(R.id.imv_logo_live_channel);
            mItemLiveChannelLinear = (LinearLayout) itemView.findViewById(R.id.linear_item_live_channel);
        }
    }
}
