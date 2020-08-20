package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.daimajia.slider.library.NearDistance;
import com.namviet.vtvtravel.BR;
import com.namviet.vtvtravel.R;

public class ItemNearDisViewModel extends BaseObservable {
    private Context mContext;
    private NearDistance nearDistance;

    public ItemNearDisViewModel(Context mContext, NearDistance nearDistance) {
        this.mContext = mContext;
        this.nearDistance = nearDistance;
    }

    public String getName() {
        return nearDistance.getLabel();
    }

    public void setNearDistance(NearDistance nearDistance) {
        this.nearDistance = nearDistance;
        notifyPropertyChanged(BR.itemNearDis);


    }

    public boolean getCheck() {
        return nearDistance.isChecked();

    }

    @BindingAdapter({"android:src"})
    public static void setImageSrc(ImageView imageView, boolean isCheck) {
        if (isCheck) {
            imageView.setImageResource(R.drawable.vt_checked);
        } else {
            imageView.setImageResource(R.drawable.vt_check);
        }

    }


}
