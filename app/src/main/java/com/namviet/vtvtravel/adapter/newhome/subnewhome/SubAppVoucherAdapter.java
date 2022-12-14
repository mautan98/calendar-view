package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse;

public class SubAppVoucherAdapter extends PagerAdapter {
    private Context context;
    private AppVoucherResponse appVoucherResponse;
    private ClickItem clickItem;
    private DataListener dataListener;

    public SubAppVoucherAdapter(Context context, AppVoucherResponse appVoucherResponse, ClickItem clickItem, DataListener dataListener) {
        this.context = context;
        this.appVoucherResponse = appVoucherResponse;
        this.clickItem = clickItem;
        this.dataListener = dataListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_home_voucher,null);
        ImageView imageView =  view.findViewById(R.id.image_cover);
        Glide.with(context).load(appVoucherResponse.getItems().get(position).getHomeUri()).placeholder(new ColorDrawable(ContextCompat.getColor(context, R.color.colorPrimary))).error(new ColorDrawable(ContextCompat.getColor(context, R.color.colorPrimary))).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.onClickItem(appVoucherResponse.getItems().get(position));
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        try {
            if(appVoucherResponse.getItems().size() > 0){
                dataListener.onHaveData(false);
            }else {
                dataListener.onHaveData(true);
            }
            return appVoucherResponse.getItems().size();
        } catch (Exception e) {
            dataListener.onHaveData(true);
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public interface ClickItem{
        void onClickItem(AppVoucherResponse.Item item);
    }

    public interface DataListener{
        public void onHaveData(boolean isShowNoDataView);
    }
}
