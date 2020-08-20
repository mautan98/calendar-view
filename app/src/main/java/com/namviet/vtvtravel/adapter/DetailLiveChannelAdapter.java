package com.namviet.vtvtravel.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.DetailLiveChannel;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.TimeUtilities;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.travel.DetailLiveChannelFragment;

import java.util.List;

public class DetailLiveChannelAdapter extends RecyclerView.Adapter<DetailLiveChannelAdapter.MyViewHolder> {
    private List<DetailLiveChannel> mDetailLiveChannelList;
    private MainActivity mMainActivity;
    private DetailLiveChannelFragment mDetailLiveChannelFragment;

    public DetailLiveChannelAdapter(List<DetailLiveChannel> detailLiveChannels, MainActivity mainActivity, DetailLiveChannelFragment detailLiveChannelFragment) {
        this.mDetailLiveChannelList = detailLiveChannels;
        this.mMainActivity = mainActivity;
        this.mDetailLiveChannelFragment = detailLiveChannelFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_live_channel, parent, false);
        return new MyViewHolder(itemView);
    }

    private long mStartTimeItem;
    private long mEndTimeItem;
    private long mCurrentTime;

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DetailLiveChannel detailLiveChannel = mDetailLiveChannelList.get(position);

        String time = DateUtltils.timeToString2(Long.valueOf(detailLiveChannel.getStart_time()));
        holder.mTimeDetailLiveChannelTxt.setText(time);
        //holder.mStatusDetailLiveChannelTxt.setText("Live");
        holder.mTitleDetailLiveChannelTxt.setText(detailLiveChannel.getTopic());
        holder.mDesDetailLiveChannelTxt.setText(detailLiveChannel.getName());

        mCurrentTime = DateUtltils.currentTime();
        mStartTimeItem = Long.parseLong(detailLiveChannel.getStart_time());
        mEndTimeItem = Long.parseLong(detailLiveChannel.getEnd_time());
        if (mStartTimeItem < mCurrentTime && mCurrentTime < mEndTimeItem) {
            holder.mTimeDetailLiveChannelTxt.setTextColor(Color.RED);
            holder.mStatusDetailLiveChannelTxt.setText("Live");
            holder.mStatusDetailLiveChannelTxt.setVisibility(View.VISIBLE);
            holder.mStatusDetailLiveChannelTxt.setTextColor(Color.RED);
            holder.mTitleDetailLiveChannelTxt.setTextColor(Color.RED);
            holder.mDesDetailLiveChannelTxt.setTextColor(Color.RED);
        } else {
            holder.mTimeDetailLiveChannelTxt.setTextColor(R.color.md_grey_900);
            holder.mStatusDetailLiveChannelTxt.setVisibility(View.INVISIBLE);
            holder.mStatusDetailLiveChannelTxt.setTextColor(R.color.md_grey_900);
            holder.mTitleDetailLiveChannelTxt.setTextColor(R.color.md_grey_900);
            holder.mDesDetailLiveChannelTxt.setTextColor(R.color.md_grey_900);
        }
    }

    @Override
    public int getItemCount() {
        return mDetailLiveChannelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTimeDetailLiveChannelTxt;
        private TextView mStatusDetailLiveChannelTxt;
        private TextView mTitleDetailLiveChannelTxt;
        private TextView mDesDetailLiveChannelTxt;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTimeDetailLiveChannelTxt = itemView.findViewById(R.id.txt_time_detail_live_channel);
            mStatusDetailLiveChannelTxt = itemView.findViewById(R.id.txt_status_detail_live_channel);
            mTitleDetailLiveChannelTxt = itemView.findViewById(R.id.txt_title_detail_live_channel);
            mDesDetailLiveChannelTxt = itemView.findViewById(R.id.txt_des_detail_live_channel);
        }
    }
}
