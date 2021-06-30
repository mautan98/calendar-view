package com.namviet.vtvtravel.adapter.travelnews;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.JustifyContent;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.ultils.TextJustification;

import java.util.ArrayList;
import java.util.List;

public class RelationNewsInTravelDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<Travel> travels;

    public RelationNewsInTravelDetailAdapter(Context context, List<Travel> travels, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.travels = travels;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_relation_news, parent, false);
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
            return travels.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private int position;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        clickItem.onClickItem(travels.get(position));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            Travel travel = travels.get(position);
            tvName.setText(travel.getName());
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                TextJustification.justify(tvName);
            }
        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);
        void likeEvent(int position);
    }
}
