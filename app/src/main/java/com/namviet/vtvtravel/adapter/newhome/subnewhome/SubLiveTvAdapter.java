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
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;

import java.util.ArrayList;
import java.util.List;

public class SubLiveTvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<LiveTvResponse.Channel> items = new ArrayList<>();
    private Context context;
    private ButtonClick buttonClick;

    public SubLiveTvAdapter(List<LiveTvResponse.Channel> items, Context context, ButtonClick buttonClick) {
        this.items = items;
        this.context = context;
        this.buttonClick = buttonClick;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_home_live_tv, parent, false);
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
        try {
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);

        }

        public void bindItem(int position) {
            imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClick.clickChannel(position);
                }
            });

            try {
                Glide.with(context).load(items.get(position).getLogo_url()).into(imgAvatar);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (position == 1)
                imgAvatar.performClick();
        }
    }

    public interface ButtonClick {
        void clickChannel(int position);
    }


}
