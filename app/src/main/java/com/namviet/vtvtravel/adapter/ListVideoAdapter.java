package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.VideoSelectListener;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;

public class ListVideoAdapter extends RecyclerView.Adapter<ListVideoAdapter.ItemVideoHolder> {
    private Context mContext;
    private List<Video> videoList;

    private VideoSelectListener videoSelectListener;

    public void setVideoSelectListener(VideoSelectListener videoSelectListener) {
        this.videoSelectListener = videoSelectListener;
    }


    public ListVideoAdapter(Context mContext, List<Video> videoList) {
        this.mContext = mContext;
        this.videoList = videoList;
    }

    public ListVideoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_video, parent, false);
        return new ItemVideoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVideoHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }

    public class ItemVideoHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvTitle;
        private TextView tvType;
        private TextView tvTime;
        private TextView tvView;

        public ItemVideoHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvType = itemView.findViewById(R.id.tvType);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvView = itemView.findViewById(R.id.tvView);
        }

        @Override
        public void bindItem(final int position) {
            super.bindItem(position);
            final Video video = videoList.get(position);
            setImageUrl(video.getLogo_url(), ivItem);
            tvTitle.setText(video.getName());
            tvType.setText(video.getCategory().getName());
            tvTime.setText(DateUtltils.timeToString(video.getCreated()));
            tvView.setText("" + video.getView_count());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != videoSelectListener) {
                        videoSelectListener.onSelectVideo(video, position, view);
//                        videoList.get(position).setView_count(video.getView_count()+1);
//                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void setVideoList(final List<Video> videoList) {
        this.videoList.addAll(videoList);
        notifyItemInserted(videoList.size());
        notifyItemRangeChanged(0, videoList.size());

    }
}
