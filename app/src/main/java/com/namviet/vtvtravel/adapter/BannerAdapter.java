package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.Banner;
import com.namviet.vtvtravel.model.News;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerHolder> {
    private Context mContext;
    private List<Banner> bannerList;

    public BannerAdapter(Context mContext, List<Banner> bannerList) {
        this.mContext = mContext;
        this.bannerList = bannerList;
    }

    @NonNull
    @Override
    public BannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_banner, parent, false);
        return new BannerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return bannerList == null ? 0 : bannerList.size();
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
            Banner banner = bannerList.get(position);
            webBanner.loadUrl(banner.getEmbed_link());
        }
    }
}
