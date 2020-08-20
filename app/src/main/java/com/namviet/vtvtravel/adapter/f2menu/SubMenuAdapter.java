package com.namviet.vtvtravel.adapter.f2menu;

import android.app.Activity;
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
import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.ImagePartActivity;
import com.namviet.vtvtravel.view.f2.MyGiftActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.view.f2.f2oldbase.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class SubMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_MENU_HEADER = 0;
    private static final int TYPE_ITEM_MENU_NORMAL = 1;
    private Context context;
    private ClickItem clickItem;

    private List<MenuItem> items;

    public SubMenuAdapter(List<MenuItem> items, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getCode().toUpperCase().contains("HEADER")) {
            return TYPE_ITEM_MENU_HEADER;
        } else if (items.get(position).getCode().toUpperCase().contains("FOOTER")) {
            return TYPE_ITEM_MENU_HEADER;
        } else {
            return TYPE_ITEM_MENU_NORMAL;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM_MENU_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_menu_header, parent, false);
            return new HeaderViewHolder(v);
        }
        if (viewType == TYPE_ITEM_MENU_NORMAL) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_menu_normal, parent, false);
            return new NormalViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM_MENU_HEADER) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
            if (getItemViewType(position) == TYPE_ITEM_MENU_NORMAL) {
                ((NormalViewHolder) holder).bindItem(position);
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
        private TextView tvName;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (items.get(position).getCode_type()){
                        case "APP_MAIN_HEADER_MY_GIFT":
                        case "APP_MAIN_FOOTER_SUPPORT":
                            MyGiftActivity.startScreen(context, (ArrayList<MenuItem>) items.get(position).getMenuChildren(), items.get(position).getName());
                            break;
                        case "APP_MAIN_FOOTER_SETTING":
                            SettingActivity.startScreen(context);
                            break;
                    }
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);
            tvName.setText(items.get(position).getName());

        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private int position;

        public NormalViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(items.get(position));
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);
            tvName.setText(items.get(position).getName());
        }
    }


    public interface ClickItem {
        void onClickItem(MenuItem menuItem);
    }


}
