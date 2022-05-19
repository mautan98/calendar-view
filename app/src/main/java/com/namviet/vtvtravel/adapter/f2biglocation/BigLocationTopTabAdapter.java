package com.namviet.vtvtravel.adapter.f2biglocation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2biglocation.BigLocationResponse;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BigLocationTopTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private String regionId;

    private List<BigLocationResponse.Data.Region.Items> items;

    public BigLocationTopTabAdapter(List<BigLocationResponse.Data.Region.Items> items, Context context, String regionId,  ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.items = items;
        this.regionId = regionId;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_big_location_top_tab, parent, false);
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
        private TextView tvName;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String json = new Gson().toJson(items);
                        Type listType = new TypeToken<ArrayList<ItemHomeService.Item>>() {}.getType();
                        ArrayList<ItemHomeService.Item> yourList = new Gson().fromJson(json, listType);
                        SmallLocationActivity.startScreen(context, SmallLocationActivity.OpenType.LIST, yourList, items.get(position).getCode(),position, regionId);
//                        SmallLocationActivity.startScreen(context, items.get(position).getLink(), items.get(position).getCode(), SmallLocationActivity.OpenType.LIST, regionId);
                    } catch (Exception e) {
                        e.printStackTrace();
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


    public interface ClickItem {
        void onClickItem(Travel travel);
    }


}
