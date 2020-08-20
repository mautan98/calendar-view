package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.VideoSelectListener;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.ArrayList;

public class PlaylistVideoAdapter extends RecyclerView.Adapter<PlaylistVideoAdapter.PlaylistVideoHolder> {
    private Context mContext;
    private ArrayList<Video> videoList;
    private VideoSelectListener videoSelectListener;
    private RecyclerView recyclerView;
    private boolean isFirst = true;

    public void setVideoSelectListener(VideoSelectListener videoSelectListener) {
        this.videoSelectListener = videoSelectListener;
    }

    public PlaylistVideoAdapter(Context mContext, ArrayList<Video> videoList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.videoList = videoList;
        this.recyclerView = recyclerView;
    }

    private int selectedItem = -1;

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PlaylistVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_playlist_video, parent, false);
        return new PlaylistVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistVideoHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }

    public class PlaylistVideoHolder extends BaseHolder {
        private ImageView imgCover;
        private TextView tvTitle;
        private TextView tvShortDes;
        private TextView tvType;
        private TextView tvTime;
        private TextView tvView;
        private RelativeLayout rlPlayArea;
        private RecyclerView rvTag;
        private View backgroundV;

        public PlaylistVideoHolder(View itemView) {
            super(itemView);
            rlPlayArea = itemView.findViewById(R.id.rlPlayArea);
            imgCover = itemView.findViewById(R.id.imgCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvShortDes = itemView.findViewById(R.id.tvShortDes);
            tvType = itemView.findViewById(R.id.tvType);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvView = itemView.findViewById(R.id.tvView);
            rvTag = itemView.findViewById(R.id.rvTag);
            backgroundV = itemView.findViewById(R.id.v_background);

            rvTag.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        }

        @Override
        public void bindItem(final int position) {
            super.bindItem(position);
            final Video video = videoList.get(position);
            setImageUrl(video.getLogo_url(), imgCover);
            tvTitle.setText(video.getName());
            tvType.setText(video.getCategory().getName());
            tvTime.setText(DateUtltils.timeToString(video.getCreated()));
            tvView.setText("" + video.getView_count());
            tvShortDes.setText(video.getShort_description());
            rlPlayArea.setVisibility(View.VISIBLE);
            if (null != video.getHash_tags()) {
                rvTag.setVisibility(View.VISIBLE);
                TagVideoAdapter tagVideoAdapter = new TagVideoAdapter(mContext, video.getHash_tags());
                rvTag.setAdapter(tagVideoAdapter);
            } else {
                rvTag.setVisibility(View.GONE);
            }

            rlPlayArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != videoSelectListener) {
                        videoSelectListener.onSelectVideo(video, position, rlPlayArea);
                        backgroundV.setBackgroundColor(mContext.getResources().getColor(R.color.alpha_white_tranfer));
                    }
                }
            });

            if (isFirst){
                backgroundV.setBackgroundColor(mContext.getResources().getColor(R.color.alpha_white_tranfer));
            } else {
                backgroundV.setBackgroundColor(mContext.getResources().getColor(R.color.alpha_white_tranfer_50));
            }

            if (position == selectedItem) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isFirst) {
                            recyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
                            isFirst = false;
                        } else {
                            recyclerView.findViewHolderForAdapterPosition(position).itemView.performClick();
                            backgroundV.setBackgroundColor(mContext.getResources().getColor(R.color.alpha_white_tranfer));
                        }
                    }
                }, 300);
            } else {
                backgroundV.setBackgroundColor(mContext.getResources().getColor(R.color.alpha_white_tranfer_50));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rlPlayArea.performClick();
                }
            });
        }
    }

}
