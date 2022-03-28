package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;

import java.util.List;

public class DealSlideImageAdapter extends PagerAdapter {
    private Context context;
    private List<String> strings;

    public DealSlideImageAdapter(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.item_image_deal, null);
        ImageView imageView = view.findViewById(R.id.imgAvatar);
        setImage(strings.get(position),imageView);
        container.addView(view);


        return view;
    }
    public void setImage(String url, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).thumbnail(0.2f).into(image);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
//        try {
//            return strings.size();
//        } catch (Exception e) {
//            return 10;
//        }


        return strings != null ? strings.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}
