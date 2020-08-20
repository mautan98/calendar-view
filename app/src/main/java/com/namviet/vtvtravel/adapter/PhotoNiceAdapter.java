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

public class PhotoNiceAdapter extends RecyclerView.Adapter<PhotoNiceAdapter.PhotoNiceHolder> {
    private Context mContext;
    private List<News> newsList;
    private NewsSelectListener newsSelectListener;

    public void setNewsSelectListener(NewsSelectListener newsSelectListener) {
        this.newsSelectListener = newsSelectListener;
    }

    public PhotoNiceAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public PhotoNiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_photo_nice, parent, false);
        return new PhotoNiceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoNiceHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public class PhotoNiceHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvItem;

        public PhotoNiceHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvItem = itemView.findViewById(R.id.tvItem);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final News news = newsList.get(position);
            setImageUrl(news.getLogo_url(), ivItem);
            tvItem.setText(news.getName());
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
