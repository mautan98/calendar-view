package com.namviet.vtvtravel.adapter.f2search;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2searchmain.MainSearchResponse;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

import java.util.List;

public class DiscoveryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private List<MainSearchResponse.Item> items;

    public DiscoveryAdapter(List<MainSearchResponse.Item> items, Context context) {
        this.context = context;
        this.items = items;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_search_discovery, parent, false);
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

        private int position;
        private TextView tvTitle;
        private ImageView imgAvatar;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        SmallLocationActivity.startScreen(context, items.get(position).getLink(), items.get(position).getCode(), SmallLocationActivity.OpenType.LIST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            tvTitle.setText(items.get(position).getName());
            Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);

        }
    }



}
