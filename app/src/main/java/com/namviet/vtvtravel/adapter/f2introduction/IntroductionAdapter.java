package com.namviet.vtvtravel.adapter.f2introduction;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse;

public class IntroductionAdapter extends PagerAdapter {
    private Context context;
    public IntroductionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_introduction,null);
        ImageView imageView =  view.findViewById(R.id.imgStep);

        switch (position){
            case 0:
                Glide.with(context).load(R.drawable.f2_img_welcome_1).into(imageView);
                break;
            case 1:
                Glide.with(context).load(R.drawable.f2_img_welcome_2).into(imageView);
                break;
            case 2:
                Glide.with(context).load(R.drawable.f2_img_welcome_3).into(imageView);
                break;
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public interface ClickItem{
        void onClickItem(AppVoucherResponse.Item item);
    }
}
