package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;

import java.util.List;

public class SubSlideImageInHeaderAdapter extends PagerAdapter {
    private Context context;
    private List<String> strings;
    IOnSlideImageHeaderClick iOnSlideImageHeaderClick;
    public SubSlideImageInHeaderAdapter(Context context, List<String> strings,IOnSlideImageHeaderClick iOnSlideImageHeaderClick) {
        this.context = context;
        this.strings = strings;
        this.iOnSlideImageHeaderClick = iOnSlideImageHeaderClick;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.f2_item_image_home, null);
        ImageView imageView = view.findViewById(R.id.imgAvatar2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iOnSlideImageHeaderClick != null){
                    iOnSlideImageHeaderClick.onSlideImageHeaderClick(position);
                }
            }
        });
        Glide.with(context).load(strings.get(position)).into(imageView);
        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        try {
            return strings.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public interface IOnSlideImageHeaderClick{
        void onSlideImageHeaderClick(int postion);
    }
}
