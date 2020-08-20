package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.model.ItemTravel;
import com.namviet.vtvtravel.view.MainActivity;

public class ItemMenuViewModel extends BaseObservable {
    private Context mContext;
    private ItemMenu itemMenu;

    public ItemMenuViewModel(Context mContext, ItemMenu itemMenu) {
        this.mContext = mContext;
        this.itemMenu = itemMenu;
    }

    @BindingAdapter("imageUrl")
    public static void menuIcon(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public void onItemClick(View view) {
//        MainActivity mainActivity = (MainActivity) mContext;
//        if (itemMenu.getType().equals(Constants.TypeMenu.NEAR_BY)) {
//            mainActivity.switchFragment(SlideMenu.MenuType.NEAR_SCREEN);
//        } else if (itemMenu.getType().equals(Constants.TypeMenu.CALL)) {
//            mainActivity.call();
//        } else {
//            Bundle bundle = new Bundle();
//            ItemTravel itemTravel = new ItemTravel(itemMenu.getType(), 0, itemMenu.getName(), itemMenu.getIcon_url(), itemMenu.getLink());
//            bundle.putParcelable(Constants.IntentKey.KEY_ITEM, itemTravel);
//            mainActivity.setBundle(bundle);
//            mainActivity.switchFragment(SlideMenu.MenuType.SHOW_ALL_SCREEN);
//        }
//        MyApplication.getInstance().setTitleDetail(itemMenu.getName());
    }

    public String getMenuName() {
        return itemMenu.getName();
    }


    public String getMenuIcon() {
        return itemMenu.getIcon_url();
    }

    public void setItemMenu(ItemMenu itemMenu) {
        this.itemMenu = itemMenu;
        notifyChange();
    }
}
