package com.namviet.vtvtravel.holder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class BaseHolder extends RecyclerView.ViewHolder {
    public BaseHolder(View itemView) {
        super(itemView);
    }

    public void bindItem(int position) {

    }

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(itemView.getContext()).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);

    }
}
