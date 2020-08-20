package com.namviet.vtvtravel.adapter.imagepart;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse;
import com.namviet.vtvtravel.view.f2.SlideImageActivity;

import java.util.ArrayList;
import java.util.List;

public class SlideImageInHighLightestImageAdapter extends PagerAdapter {
    private Context context;
    private List<String> strings;
    private String type;
    public static final String LIST_TYPE = "LIST_TYPE";
    public static final String DETAIL_TYPE = "DETAIL_TYPE";

    public SlideImageInHighLightestImageAdapter(Context context, List<String> strings, String type) {
        this.context = context;
        this.strings = strings;
        this.type = type;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        if (type.equals(LIST_TYPE)) {
            view = LayoutInflater.from(context).inflate(R.layout.f2_item_image_part_image, null);
            ImageView imageView = view.findViewById(R.id.imgAvatar);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlideImageActivity.startScreen((Activity) context, (ArrayList<String>) strings, position);
                }
            });

            Glide.with(context).load(strings.get(position)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.f2_item_image_part_image_2, null);
            ImageView imageView = view.findViewById(R.id.imgAvatar2);

            Glide.with(context).load(strings.get(position)).into(imageView);
            container.addView(view);
        }

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
}
