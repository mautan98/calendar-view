package com.namviet.vtvtravel.adapter.f2livetv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;

import java.util.List;

public class ChannelLiveTVDialogAdapter extends RecyclerView.Adapter<ChannelLiveTVDialogAdapter.MyViewHolder> {
    private Context context;
    private List<LiveTvResponse.Channel> channelList;
    private ClickButton clickButton;

    public ChannelLiveTVDialogAdapter(Context context, List<LiveTvResponse.Channel> channelList, ClickButton clickButton) {
        this.context = context;
        this.channelList = channelList;
        this.clickButton = clickButton;
    }

    @NonNull
    @Override
    public ChannelLiveTVDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChannelLiveTVDialogAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_channel_bottom_sheet_livetv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelLiveTVDialogAdapter.MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return channelList == null ? 0 : channelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgChannel;
        private ConstraintLayout layoutChannel;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgChannel = itemView.findViewById(R.id.imgChannel);
            layoutChannel = itemView.findViewById(R.id.layoutChannel);
        }

        public void onBind(int position) {
            Glide.with(context).load(channelList.get(position).getLogo_url()).into(imgChannel);
            layoutChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickButton.clickChannel(position);
                }
            });
        }
    }

    public interface ClickButton {
        void clickChannel(int position);
    }
}