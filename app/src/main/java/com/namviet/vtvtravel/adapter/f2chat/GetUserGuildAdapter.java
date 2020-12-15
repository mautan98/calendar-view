package com.namviet.vtvtravel.adapter.f2chat;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;

import java.util.List;

public class GetUserGuildAdapter extends RecyclerView.Adapter<GetUserGuildAdapter.ViewHolder> {
    private Context context;
    private List<GetUserGuildResponse.Data.Item> itemList;
    private ClickItem clickItem;

    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public GetUserGuildAdapter(Context context, List<GetUserGuildResponse.Data.Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public GetUserGuildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_chat_recycle_get_user_guild, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        private LinearLayout layoutGetUserGuild;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLabel = itemView.findViewById(R.id.tvLabel);
            imgUserGuild = itemView.findViewById(R.id.imgUserGuild);
            layoutGetUserGuild = itemView.findViewById(R.id.layoutGetUserGuild);
        }

        public void onBind(int position) {
            GetUserGuildResponse.Data.Item item = itemList.get(position);
            tvDescription.setText(item.getDescriptions());
            tvLabel.setText(item.getLabel());
            Glide.with(context).load(item.getIcon()).into(imgUserGuild);
            layoutGetUserGuild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItem.getCode(item.getCode());
                }
            });
        }
    }

    public interface ClickItem {
        void getCode(String code);
    }
}
