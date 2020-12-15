package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.model.News;

import java.util.List;

public class SlidePhotoAdapter extends PagerAdapter {

    private Context mContext;
    private List<News> newsList;
    private NewsSelectListener newsSelectListener;

    public void setNewsSelectListener(NewsSelectListener newsSelectListener) {
        this.newsSelectListener = newsSelectListener;
    }

    public SlidePhotoAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList == null ? 0 : newsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_slideshow, container, false);
        ImageView ivItem = view.findViewById(R.id.ivItem);
        TextView tvName = view.findViewById(R.id.tvName);
        tvName.setText(newsList.get(position).getName());
        setImageUrl(newsList.get(position).getLogo_url(), ivItem);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != newsSelectListener) {
                    newsSelectListener.onSelectNews(newsList.get(position));
                }
            }
        });
        return view;
    }

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
