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

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private Context context;
    private List<GetUserGuildResponse.Data.Item> itemList;

    public BookingAdapter(Context context, List<GetUserGuildResponse.Data.Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_chat_recycle_booking, parent, false);
        return new BookingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescription;
        private TextView tvLabel;
        private ImageView imgUserGuild;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLabel = itemView.findViewById(R.id.tvLabel);
            imgUserGuild = itemView.findViewById(R.id.imgUserGuild);
        }

        public void onBind(int position) {
            GetUserGuildResponse.Data.Item item = itemList.get(position);
            tvDescription.setText(item.getDescriptions());
            tvLabel.setText(item.getLabel());
            Glide.with(context).load(item.getIcon()).into(imgUserGuild);
        }
    }
}
