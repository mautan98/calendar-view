package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.ItemFilter;

public class ItemFilterViewModel extends BaseObservable {
    private Context mContext;
    private ItemFilter itemFilter;

    public ItemFilterViewModel(Context mContext, ItemFilter itemFilter) {
        this.mContext = mContext;
        this.itemFilter = itemFilter;
    }

    public String getIcon() {
        return "";
    }

    public String getTitle() {
        return "";
    }

    public int getIconRes(){
        boolean b=false;
        if(b){
            return R.drawable.vt_checked;
        }else {
            return R.drawable.vt_check;
        }
    }

    @BindingAdapter("imageUrl")
    public static void filterIcon(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter("imageRes")
    public static void filterSelect(ImageView imageView, int res) {
        imageView.setImageResource(res);
    }

    public void setItemFilter(ItemFilter itemFilter) {
        this.itemFilter = itemFilter;
    }
}
