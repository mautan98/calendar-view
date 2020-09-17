package com.namviet.vtvtravel.adapter.f2livetv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;

import java.util.List;

public class ChannelLiveTVAdapter extends RecyclerView.Adapter<ChannelLiveTVAdapter.MyViewHolder> {
    private Context context;
    private List<LiveTvResponse.Channel> channelList;
    private ClickButton clickButton;
    private int positionSelected = 0;

    public ChannelLiveTVAdapter(Context context, List<LiveTvResponse.Channel> channelList, ClickButton clickButton, int positionSelected) {
        this.context = context;
        this.channelList = channelList;
        this.clickButton = clickButton;
        this.positionSelected = positionSelected;
    }

    public void setPositionSelected(int positionSelected){
        try {
            this.positionSelected = positionSelected;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ChannelLiveTVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChannelLiveTVAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_channel, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelLiveTVAdapter.MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return channelList == null ? 0 : channelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgChannel;
        private View viewBg;
        private int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgChannel = itemView.findViewById(R.id.imgChannel);
            viewBg = itemView.findViewById(R.id.viewBg);
        }

        public void onBind(int position) {
            this.position = position;
            if (position == positionSelected) {
                viewBg.setVisibility(View.VISIBLE);
            } else {
                viewBg.setVisibility(View.GONE);
            }

            Glide.with(context).load(channelList.get(position).getLogo_url()).into(imgChannel);
            imgChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionSelected = position;
                    notifyDataSetChanged();
                    clickButton.clickChannel(position);
                }
            });
        }
    }

    public interface ClickButton {
        void clickChannel(int position);
    }
}