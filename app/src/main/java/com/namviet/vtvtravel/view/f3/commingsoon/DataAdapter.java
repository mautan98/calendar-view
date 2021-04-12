package com.namviet.vtvtravel.view.f3.commingsoon;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DataAdapter {
    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, int imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
//                .apply(new RequestOptions().circleCrop())
                .into(view);
    }
}
