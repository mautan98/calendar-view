package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.BR;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.NearType;

public class ItemNearTypeViewModel extends BaseObservable {
    private Context mContext;
    private NearType nearType;

    public ItemNearTypeViewModel(Context mContext, NearType nearType) {
        this.mContext = mContext;
        this.nearType = nearType;
    }

    public String getName() {
        return nearType.getName();
    }

    public String getIcon() {
        return nearType.getIcon_url();
    }

    public boolean getCheck() {
        return nearType.isCheck();
    }

    @BindingAdapter("imageIcon")
    public static void iconType(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter("android:background")
    public static void bgImage(ImageView imageView, boolean isCheck) {
        if (isCheck) {
            imageView.setBackgroundResource(R.drawable.bg_circel_type);
        } else {
            imageView.setBackgroundResource(R.drawable.bg_circel_tyle_selected);
        }
    }

    public void setNearType(NearType nearType) {
        this.nearType = nearType;
        notifyPropertyChanged(BR.itemNearType);
    }

    public void onItemClick() {

    }
}
