package com.namviet.vtvtravel.adapter.newhome.subnewhome.tab;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;

import java.util.ArrayList;
import java.util.List;

public class TabVoucherNowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<ItemHomeService.Item> items = new ArrayList<>();
    private Context context;
    private int selectedItem = 0;
    private ClickTab clickTab;

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    public TabVoucherNowAdapter(int position, List<ItemHomeService.Item> items, Context context, ClickTab clickTab) {
        this.items = items;
        this.context = context;
        this.clickTab = clickTab;
        this.selectedItem = position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_home_tab_suggestion_location, parent, false);
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
        private TextView tvTitle;
        private LinearLayout rootView;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rootView = itemView.findViewById(R.id.rootView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = position;
                    clickTab.onClickTab(selectedItem);
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            if (position == selectedItem) {
                rootView.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_selected_round_suggestion_location));
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
            } else {
                rootView.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_unselected_round_suggestion_location));
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
            tvTitle.setText(items.get(position).getName());
            Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);
        }
    }


    public interface ClickTab{
        void onClickTab(int position);
    }

}
