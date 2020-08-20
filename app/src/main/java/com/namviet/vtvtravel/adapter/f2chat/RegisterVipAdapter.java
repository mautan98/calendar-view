package com.namviet.vtvtravel.adapter.f2chat;

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
import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;
import com.namviet.vtvtravel.response.f2chat.PostUserGuildResponse;

import java.util.List;

public class RegisterVipAdapter extends RecyclerView.Adapter<RegisterVipAdapter.ViewHolder> {
    private Context context;
    private List<PostUserGuildResponse.Data.Item> itemList;

    public RegisterVipAdapter(Context context, List<PostUserGuildResponse.Data.Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RegisterVipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_chat_recycle_register_vip, parent, false);
        return new RegisterVipAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterVipAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void onBind(int position) {
            PostUserGuildResponse.Data.Item item = itemList.get(position);
            tvDescription.setText(item.getDescriptions());
        }
    }
}

