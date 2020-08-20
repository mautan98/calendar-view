package com.namviet.vtvtravel.adapter.f2video;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.comment.SubCommentAdapter;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.DetailVideoActivity;
import com.namviet.vtvtravel.view.f2.TagVideoActivity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SubVideoAdapter extends RecyclerView.Adapter<SubVideoAdapter.HeaderViewHolder> {
    private Context context;
    private List<Video> videos;
    private ClickItem clickItem;

    public SubVideoAdapter(Context context, List<Video> videos, ClickItem clickItem) {
        this.videos = videos;
        this.context = context;
        this.clickItem = clickItem;
    }


    @NonNull
    @Override
    public SubVideoAdapter.HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_video, parent, false);

        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubVideoAdapter.HeaderViewHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        try {
            return videos.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rclTag;
        private TextView tvTitle, tvType, tvDate, tvView;
        private ImageView imgBanner;
        private ImageView btnShare;
        private TagVideoAdapter tagVideoAdapter;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            rclTag = itemView.findViewById(R.id.rclTag);
            imgBanner = itemView.findViewById(R.id.imgBanner);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvType = itemView.findViewById(R.id.tvType);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvView = itemView.findViewById(R.id.tvView);
            btnShare = itemView.findViewById(R.id.btnShare);
            rclTag = itemView.findViewById(R.id.rclTag);
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context);
            rclTag.setLayoutManager(flexboxLayoutManager);
        }

        public void bindItem(int position) {
            try {
                Glide.with(context).load(videos.get(position).getLogo_url()).into(imgBanner);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int value = videos.get(position).getView_count();
            if (value > 1000) {
                int finalValue = Math.round(value / 1000 * 10) / 10;
                tvView.setText(String.valueOf(finalValue) + "k");
            } else {
                tvView.setText(String.valueOf(value));
            }
            tvDate.setText(DateUtltils.timeToString(videos.get(position).getCreated()));
            tvTitle.setText(videos.get(position).getName());
            tvType.setText(videos.get(position).getCategory().getName());


            tagVideoAdapter = new TagVideoAdapter(context, videos.get(position).getHash_tags(), new TagVideoAdapter.ClickItemTagVideo() {
                @Override
                public void onClickItemTagVideo(String tag) {

                    TagVideoActivity.startScreen((Activity) context, tag);
                }
            });
            rclTag.setAdapter(tagVideoAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailVideoActivity.startScreen((Activity) context, videos.get(position));
                }
            });
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    F2Util.startSenDataText((Activity) context, videos.get(position).getLink_share());
                }
            });
        }
    }


    public interface ClickItem {
        void onClickItem(Video video);
    }


}
