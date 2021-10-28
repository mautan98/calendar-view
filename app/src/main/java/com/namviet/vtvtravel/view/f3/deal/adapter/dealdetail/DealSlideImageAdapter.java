package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.namviet.vtvtravel.R;

import java.util.List;

public class DealSlideImageAdapter extends PagerAdapter {
    private Context context;
    private List<String> strings;
    private ClickItem clickItem;


    public DealSlideImageAdapter(Context context, List<String> strings, ClickItem clickItem) {
        this.context = context;
        this.strings = strings;
        this.clickItem = clickItem;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.item_image_deal, null);
        ImageView imageView = view.findViewById(R.id.imgAvatar);

//        Glide.with(context).load(strings.get(position)).into(imageView);
        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
//        try {
//            return strings.size();
//        } catch (Exception e) {
//            return 10;
//        }


        return 10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public interface ClickItem {
        public void onClickItem();
    }
}
