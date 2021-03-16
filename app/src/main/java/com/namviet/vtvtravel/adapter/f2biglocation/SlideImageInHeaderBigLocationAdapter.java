package com.namviet.vtvtravel.adapter.f2biglocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f2.SlideImageActivity;

import java.util.ArrayList;
import java.util.List;

public class SlideImageInHeaderBigLocationAdapter extends PagerAdapter {
    private Context context;
    private List<String> strings;

    public SlideImageInHeaderBigLocationAdapter(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.f2_item_image_header_big_location, null);
        ImageView imageView = view.findViewById(R.id.imgAvatar2);

        Glide.with(context).load(strings.get(position)).into(imageView);
        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        try {
            return strings.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}
