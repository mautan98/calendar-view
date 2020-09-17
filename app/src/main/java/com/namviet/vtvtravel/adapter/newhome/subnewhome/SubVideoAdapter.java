package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.newhome.AppVideoResponse;
import com.namviet.vtvtravel.view.f2.DetailVideoActivity;

import java.util.List;

public class SubVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    private Context context;
    private List<AppVideoResponse.Item> itemList;

    public SubVideoAdapter(Context context, List<AppVideoResponse.Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_app_video, parent, false);
            return new HeaderViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView imvBanner;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imvBanner = itemView.findViewById(R.id.imvBanner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Video video = new Gson().fromJson(new Gson().toJson(itemList.get(position)), Video.class);
                    DetailVideoActivity.startScreen(context, video);
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            tvTitle.setText(itemList.get(position).getName());
            setImage(itemList.get(position).getLogo_url(), imvBanner);
        }

        public void setImage(String url, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).thumbnail(0.2f).into(image);
        }
    }


}
