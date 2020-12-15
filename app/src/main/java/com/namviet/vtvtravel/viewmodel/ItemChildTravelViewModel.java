package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.BR;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.MainActivity;

public class ItemChildTravelViewModel extends BaseObservable {
    private Context mContext;
    private Travel travel;
    private String category;

    public ItemChildTravelViewModel(Context mContext, Travel travel) {
        this.mContext = mContext;
        this.travel = travel;

    }

    public String getName() {
        return travel.getName();

    }

    public String getPhoto() {
        return travel.getLogo_url();
    }

    public String getTime() {
        return DateUtltils.timeToString(travel.getCreated());
    }

    public String getAddress() {
        return travel.getAddress();
    }

    public String getViewed() {
        return "" + travel.getView_count();
    }

    public boolean getCheckTime() {
        return travel.getCreated() == null ? false : true;
    }

    public boolean getCheckViewed() {
        return travel.getView_count() == null ? false : true;
    }

    public boolean getCheckAdd() {
        if (travel.getAddress() != null && !travel.getAddress().equals("")) {
            return true;
        } else {
            return false;
        }

    }


    @BindingAdapter("imageUrl")
    public static void imagePhoto(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);

    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    public void onItemClick(View view) {
        MyApplication.getInstance().setTitleDetail(category);
        MainActivity mainActivity = (MainActivity) mContext;
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_ITEM, travel);
        mainActivity.setBundle(bundle);
        if (travel.getType().equals(Constants.TypeMenu.FOOD_TRAVEL) || travel.getType().equals(Constants.TypeMenu.PLACE_TRAVEL) || travel.getType().equals(Constants.TypeMenu.WEATHER_TRAVEL) || travel.getType().equals(Constants.TypeMenu.VIDEO)) {
//            mainActivity.switchFragment(SlideMenu.MenuType.VIDEO_VIEW_SCREEN);
        } else {
            mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_SCREEN);
        }

    }

    public void setTravel(Travel travel) {
        this.travel = travel;
        notifyPropertyChanged(BR.itemTravel);
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
