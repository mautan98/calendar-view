package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.List;

public class ListNewsHighlightAdapterMenu extends RecyclerView.Adapter<BaseHolder> {
    private Context mContext;
    private List<News> newsList;
    private MainActivity mActivity;
    private NewsSelectListener newsSelectListener;
    private boolean isList;

    public void setNewsSelectListener(NewsSelectListener newsSelectListener) {
        this.newsSelectListener = newsSelectListener;
    }

    public ListNewsHighlightAdapterMenu(Context mContext) {
        this.mContext = mContext;
        mActivity = (MainActivity) mContext;
    }

    public ListNewsHighlightAdapterMenu(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    public ListNewsHighlightAdapterMenu(Context mContext, List<News> newsList, boolean isList) {
        this.mContext = mContext;
        this.newsList = newsList;
        this.isList = isList;
    }


    @Override
    public int getItemViewType(int position) {
        if (newsList.get(position).isBanner()) {
            return R.layout.item_banner;
        } else {
            return R.layout.item_news_list_highlight_menu;
        }
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        if (viewType == R.layout.item_banner) {
            return new BannerHolder(v);
        } else {
            return new NewsHighlightHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public class NewsHighlightHolder extends BaseHolder {
        private ImageView ivPhoto;
        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvViewed;
        private TextView tvShortDes;

        public NewsHighlightHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvViewed = itemView.findViewById(R.id.tvViewed);
            tvShortDes = itemView.findViewById(R.id.tvShortDes);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final News news = newsList.get(position);
            setImageUrl(news.getLogo_url(), ivPhoto);
            tvTitle.setText(news.getName());
            if (isList) {
                tvShortDes.setVisibility(View.GONE);
            } else {
                tvShortDes.setText(news.getShort_description());
            }
            tvTime.setText(DateUtltils.timeToString(news.getCreated()));
            tvViewed.setText("" + news.getView_count());

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

    public class BannerHolder extends BaseHolder {
        private WebView webBanner;

        public BannerHolder(View itemView) {
            super(itemView);
            webBanner = itemView.findViewById(R.id.itemBanner);
            webBanner.getSettings().setJavaScriptEnabled(true);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            News news = newsList.get(position);
            webBanner.loadUrl(news.getLogo_url());
        }
    }

}
