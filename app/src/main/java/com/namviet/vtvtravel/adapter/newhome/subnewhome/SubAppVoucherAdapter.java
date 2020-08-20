package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse;

public class SubAppVoucherAdapter extends PagerAdapter {
    private Context context;
    private AppVoucherResponse appVoucherResponse;
    private ClickItem clickItem;

    public SubAppVoucherAdapter(Context context, AppVoucherResponse appVoucherResponse, ClickItem clickItem) {
        this.context = context;
        this.appVoucherResponse = appVoucherResponse;
        this.clickItem = clickItem;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_home_voucher,null);
        ImageView imageView =  view.findViewById(R.id.image_cover);
        Glide.with(context).load(appVoucherResponse.getItems().get(position).getAvatarUri()).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
            return appVoucherResponse.getItems().size();
        } catch (Exception e) {
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
}
