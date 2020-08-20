package com.namviet.vtvtravel.adapter.f2chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;
import com.namviet.vtvtravel.response.f2chat.PostUserGuildResponse;

import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {
    private Context context;
    private List<PostUserGuildResponse.Data.Item> itemList;
    private ClickItem clickItem;

    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public DealAdapter(Context context, List<PostUserGuildResponse.Data.Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public DealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_chat_recycle_deal, parent, false);
        return new DealAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLabel;
        private WebView webViewContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.tvLabel);
            webViewContent = itemView.findViewById(R.id.webViewContent);
            webViewContent.getSettings().setJavaScriptEnabled(true);
        }

        public void onBind(int position) {
            PostUserGuildResponse.Data.Item item = itemList.get(position);
            tvLabel.setText(item.getLabel());
            tvLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getCode() != null && !"".equals(item.getCode())) {
                        clickItem.clickDealButton(item.getCode());
                    }
                }
            });
            webViewContent.loadData(item.getDescriptions(), "text/html; charset=utf-8", "UTF-8");
        }
    }

    public interface ClickItem {
        void clickDealButton(String url);
    }
}
