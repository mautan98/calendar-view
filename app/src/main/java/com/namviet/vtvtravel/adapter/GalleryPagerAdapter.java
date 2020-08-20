package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.ArrayList;

public class GalleryPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<String> photoList;
    private MainActivity mainActivity;

    public GalleryPagerAdapter(Context mContext, ArrayList<String> photoList) {
        this.mContext = mContext;
        this.photoList = photoList;
        mainActivity = (MainActivity) mContext;
    }

    @Override
    public int getCount() {
        return photoList == null ? 0 : photoList.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_slide_photo, null);
        ImageView itemSlidePhoto = view.findViewById(R.id.itemSlidePhoto);
        setImageUrl(photoList.get(position), itemSlidePhoto);
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setImageUrl(String ulrCs, final ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
//        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .asBitmap()
                .load(ulrCs)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        image.setImageBitmap(scaledBitmap(resource));

                    }
                });
    }

    private Bitmap scaledBitmap(Bitmap bitmapOriginal) {
        Bitmap scaledBitmap = null;
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            mainActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int widthScreen = metrics.widthPixels;
            int width = bitmapOriginal.getWidth();
            float scale = widthScreen / (float) width;
            float height = bitmapOriginal.getHeight() * scale;
            scaledBitmap = Bitmap.createScaledBitmap(bitmapOriginal, widthScreen, (int) height, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scaledBitmap;
    }
}
