package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.model.ItemTravel;
import com.namviet.vtvtravel.view.MainActivity;

public class ItemTravelViewModel extends BaseObservable {
    private Context mContext;
    private ItemTravel itemTravel;

    public ItemTravelViewModel(Context mContext, ItemTravel itemTravel) {
        this.mContext = mContext;
        this.itemTravel = itemTravel;
    }

    public String getName() {
        return itemTravel.getName() + " (" + itemTravel.getTotal_count() + ")";
    }

    public String getIcon() {
        return itemTravel.getIcon();
    }

    @BindingAdapter("imageIcon")
    public static void iconSearch(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public void viewAll(View view) {
        MyApplication.getInstance().setTitleDetail(itemTravel.getName());
        MainActivity mainActivity = (MainActivity) mContext;
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_ITEM, itemTravel);
        mainActivity.setBundle(bundle);
        mainActivity.switchFragment(SlideMenu.MenuType.SHOW_ALL_SCREEN);
    }

    public void setItemTravel(ItemTravel itemTravel) {
        this.itemTravel = itemTravel;
    }

}
