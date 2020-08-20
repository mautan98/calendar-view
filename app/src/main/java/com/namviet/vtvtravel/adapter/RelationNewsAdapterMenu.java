package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.model.News;

import java.util.List;

public class RelationNewsAdapterMenu extends RecyclerView.Adapter<RelationNewsAdapterMenu.RelationNewsHolder> {
    private Context mContext;
    private List<News> newsList;

    private NewsSelectListener newsSelectListener;

    public void setNewsSelectListener(NewsSelectListener newsSelectListener) {
        this.newsSelectListener = newsSelectListener;
    }

    public RelationNewsAdapterMenu(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public RelationNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_relation_news_menu, parent, false);
        return new RelationNewsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RelationNewsHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public class RelationNewsHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvTitle;
        private TextView tvAddress;

        public RelationNewsHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final News news = newsList.get(position);
            setImageUrl(news.getLogo_url(), ivItem);
            tvAddress.setVisibility(View.GONE);
            tvTitle.setText(news.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != newsSelectListener) {
                        newsSelectListener.onSelectNews(news);
                    }
                }
            });
        }
    }
}
