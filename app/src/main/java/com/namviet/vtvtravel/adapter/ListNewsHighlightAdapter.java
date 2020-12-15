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
import com.namviet.vtvtravel.help.ItemGroupNew;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ListNewsHighlightAdapter extends RecyclerView.Adapter<BaseHolder> {
    private Context mContext;
    private List<News> newsList;
    private MainActivity mActivity;
    private NewsSelectListener newsSelectListener;
    private boolean isList;
    private List<ItemGroupNew> itemGroupNews;

    public void setNewsSelectListener(NewsSelectListener newsSelectListener) {
        this.newsSelectListener = newsSelectListener;
    }

    public ListNewsHighlightAdapter(Context mContext) {
        this.mContext = mContext;
        mActivity = (MainActivity) mContext;
    }

    public ListNewsHighlightAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
//        itemGroupNews = pagingList(newsList, 2);
    }

    public ListNewsHighlightAdapter(Context mContext, List<News> newsList, boolean isList) {
        this.mContext = mContext;
        this.newsList = newsList;
        this.isList = isList;
        //itemGroupNews = pagingList(newsList, 2);
    }



    @Override
    public int getItemViewType(int position) {
        if (newsList.get(position).isBanner()) {
            return R.layout.item_banner;
        } else {
            return R.layout.item_news_list_highlight;
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

        private ImageView ivPhoto2;
        private TextView tvTitle2;
        private TextView tvTime2;
        private TextView tvViewed2;
        private TextView tvShortDes2;

        public NewsHighlightHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvViewed = itemView.findViewById(R.id.tvViewed);
            tvShortDes = itemView.findViewById(R.id.tvShortDes);

//            ivPhoto2 = itemView.findViewById(R.id.ivPhoto2);
//            tvTitle2 = itemView.findViewById(R.id.tvTitle2);
//            tvTime2 = itemView.findViewById(R.id.tvTime2);
//            tvViewed2 = itemView.findViewById(R.id.tvViewed2);
//            tvShortDes2 = itemView.findViewById(R.id.tvShortDes2);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
//            final ItemGroupNew itemGroupNew = itemGroupNews.get(position);
//            if (itemGroupNew.getGroup().size() > 0) {
                News news = newsList.get(position);

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
//            }

//            if (itemGroupNew.getGroup().size() > 1) {
//                News news = itemGroupNew.getGroup().get(1);
//
//                setImageUrl(news.getLogo_url(), ivPhoto2);
//                tvTitle2.setText(news.getName());
//                if (isList) {
//                    tvShortDes2.setVisibility(View.GONE);
//                } else {
//                    tvShortDes2.setText(news.getShort_description());
//                }
//                tvTime2.setText(DateUtltils.timeToString(news.getCreated()));
//                tvViewed2.setText("" + news.getView_count());
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (null != newsSelectListener) {
//                            newsSelectListener.onSelectNews(news);
//                        }
//                    }
//                });
//            }
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
