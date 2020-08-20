package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.help.ItemGroupNew;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.model.News;

import java.util.ArrayList;
import java.util.List;

public class RelationNewsAdapter extends RecyclerView.Adapter<RelationNewsAdapter.RelationNewsHolder> {
    private Context mContext;
    private List<News> newsList;
    private List<ItemGroupNew> itemGroupNews;

    private NewsSelectListener newsSelectListener;

    public void setNewsSelectListener(NewsSelectListener newsSelectListener) {
        this.newsSelectListener = newsSelectListener;
    }

    public RelationNewsAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
        itemGroupNews = pagingList(newsList, 2);
    }

    @NonNull
    @Override
    public RelationNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_relation_news, parent, false);
        return new RelationNewsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RelationNewsHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return itemGroupNews == null ? 0 : itemGroupNews.size();
    }

    public class RelationNewsHolder extends BaseHolder {
        private LinearLayout llItem;
        private LinearLayout llItem2;
        private ImageView ivItem;
        private TextView tvTitle;
        private TextView tvAddress;
        private ImageView ivItem2;
        private TextView tvTitle2;
        private TextView tvAddress2;

        public RelationNewsHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            ivItem2 = itemView.findViewById(R.id.ivItem2);
            tvTitle2 = itemView.findViewById(R.id.tvTitle2);
            tvAddress2 = itemView.findViewById(R.id.tvAddress2);
            llItem = itemView.findViewById(R.id.llItem);
            llItem2 = itemView.findViewById(R.id.llItem2);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final ItemGroupNew itemGroupNew = itemGroupNews.get(position);
            if (itemGroupNew.getGroup().size() > 0) {
                News news = itemGroupNew.getGroup().get(0);
                setImageUrl(news.getLogo_url(), ivItem);
                tvAddress.setVisibility(View.GONE);
                tvTitle.setText(news.getName());
                llItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != newsSelectListener) {
                            newsSelectListener.onSelectNews(news);
                        }
                    }
                });
            }
            if (itemGroupNew.getGroup().size() > 1) {
                News news = itemGroupNew.getGroup().get(1);
                setImageUrl(news.getLogo_url(), ivItem2);
                tvAddress2.setVisibility(View.GONE);
                tvTitle2.setText(news.getName());
                llItem2.setOnClickListener(new View.OnClickListener() {
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

    private List<ItemGroupNew> pagingList(List<News> data, int number) {
        if (null != data) {
            int surplus = data.size() % number;
            int page;
            List<ItemGroupNew> itemGroups = new ArrayList<>();
            if (surplus > 0) {
                page = data.size() / number + 1;
                if (page > 0) {
                    for (int i = 1; i <= page; i++) {
                        List<News> listOfGroup = new ArrayList<>();
                        int start = (i - 1) * number;
                        if (i < page) {
                            for (int j = start; j < start + number; j++) {
                                listOfGroup.add(data.get(j));
                            }
                        } else {
                            for (int k = start; k < data.size(); k++) {
                                listOfGroup.add(data.get(k));
                            }
                        }
                        itemGroups.add(new ItemGroupNew(listOfGroup));
                    }
                }
            } else {
                page = data.size() / number;
                if (page > 0) {
                    for (int i = 1; i <= page; i++) {
                        List<News> listOfGroup = new ArrayList<>();
                        int start = (i - 1) * number;
                        for (int j = start; j < start + number; j++) {
                            listOfGroup.add(data.get(j));
                        }
                        itemGroups.add(new ItemGroupNew(listOfGroup));
                    }
                }
            }
            return itemGroups;
        }
        return null;
    }
}
