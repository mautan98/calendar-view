package com.namviet.vtvtravel.adapter.f2chat;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2chat.PostUserGuildResponse;

import java.util.List;

public class UserGuildTextAdapter extends RecyclerView.Adapter<UserGuildTextAdapter.ViewHolder> {
    private Context context;
    private List<PostUserGuildResponse.Data.Item> itemList;
    private ClickItem clickItem;

    public UserGuildTextAdapter(Context context, List<PostUserGuildResponse.Data.Item> itemList, ClickItem clickItem) {
        this.context = context;
        this.itemList = itemList;
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public UserGuildTextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_chat_recycle_register_vip, parent, false);
        return new UserGuildTextAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserGuildTextAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescription;
        private TextView tvLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLabel = itemView.findViewById(R.id.tvLabel);
        }

        public void onBind(int position) {
            if (position == itemList.size() - 1) {
                tvDescription.setOnClickListener(view -> clickItem.clickItem());
            }

            try {
                PostUserGuildResponse.Data.Item item = itemList.get(position);
                if ("button".equals(item.getType())) {
                    tvLabel.setVisibility(View.VISIBLE);
                    tvLabel.setText(item.getLabel());
                } else {
                    tvDescription.setText(item.getDescriptions());
                    tvLabel.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface ClickItem {
        void clickItem();
    }
}

