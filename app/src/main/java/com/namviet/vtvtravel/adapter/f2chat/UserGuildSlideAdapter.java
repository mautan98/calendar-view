package com.namviet.vtvtravel.adapter.f2chat;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2chat.PostUserGuildResponse;

import java.util.List;

public class UserGuildSlideAdapter extends RecyclerView.Adapter<UserGuildSlideAdapter.ViewHolder> {
    private Context context;
    private List<PostUserGuildResponse.Data.Item> itemList;
    private ClickItem clickItem;
    private RecyclerView recyclerView;

    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public UserGuildSlideAdapter(Context context, List<PostUserGuildResponse.Data.Item> itemList, RecyclerView recyclerView) {
        this.context = context;
        this.itemList = itemList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public UserGuildSlideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_chat_recycle_deal, parent, false);
        return new UserGuildSlideAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserGuildSlideAdapter.ViewHolder holder, int position) {
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
                        clickItem.clickDealButton(item.getId());
                    }
                }
            });
            webViewContent.loadData(item.getDescriptions(), "text/html; charset=utf-8", "UTF-8");
            webViewContent.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    recyclerView.requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
        }
    }

    public interface ClickItem {
        void clickDealButton(String url);
    }
}
