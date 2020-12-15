package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;

import java.util.ArrayList;
import java.util.List;

public class SubLiveTvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<LiveTvResponse.Channel> items = new ArrayList<>();
    private Context context;
    private ButtonClick buttonClick;
    private int positionSelected = 0;

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
        private View viewBg;
        private int position;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            viewBg = itemView.findViewById(R.id.viewBg);
        }

        public void bindItem(int position) {
            this.position = position;
            try {
                if(position == positionSelected){
                    Glide.with(context).load(items.get(position).getLogo_url()).into(imgAvatar);
    //                viewBg.setVisibility(View.VISIBLE);
                }else {
                    Glide.with(context).load(items.get(position).getLogo_disabled()).into(imgAvatar);
    //                viewBg.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Glide.with(context).load(items.get(position).getLogo_url()).into(imgAvatar);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionSelected = position;
                    notifyDataSetChanged();
                    buttonClick.clickChannel(position);
                }
            });

//            try {
//                Glide.with(context).load(items.get(position).getLogo_url()).into(imgAvatar);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            if (position == 0)
//                imgAvatar.performClick();
        }
    }

    public interface ButtonClick {
        void clickChannel(int position);
    }


}
