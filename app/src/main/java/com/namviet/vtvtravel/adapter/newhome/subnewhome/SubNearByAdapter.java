package com.namviet.vtvtravel.adapter.newhome.subnewhome;

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
import com.namviet.vtvtravel.model.newhome.ItemHomeService;

import java.util.ArrayList;
import java.util.List;

public class SubNearByAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<ItemHomeService.Item> items = new ArrayList<>();
    private Context context;
    private ClickItem clickItem;

    public SubNearByAdapter(List<ItemHomeService.Item> items, Context context, ClickItem clickItem) {
        this.items = items;
        this.context = context;
        this.clickItem = clickItem;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_home_near_place, parent, false);
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
        private ImageView imgBanner;
        private TextView tvTitle;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgBanner = itemView.findViewById(R.id.imgBanner);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(items.get(position));
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            tvTitle.setText(items.get(position).getName());
            Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);
            Glide.with(context).load(items.get(position).getBanner_url()).into(imgBanner);
        }
    }

    public interface ClickItem{
        void onClickItem(ItemHomeService.Item item);
    }


}
