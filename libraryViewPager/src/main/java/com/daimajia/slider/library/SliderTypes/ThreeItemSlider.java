package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.R;

public class ThreeItemSlider extends BaseSliderView {

    public ThreeItemSlider(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_three_item, null);
        ImageView ivItem1=v.findViewById(R.id.ivItem1);
        TextView tvDescription1=v.findViewById(R.id.tvDescription1);

        ImageView ivItem2=v.findViewById(R.id.ivItem2);
        TextView tvDescription2=v.findViewById(R.id.tvDescription2);

        ImageView ivItem3=v.findViewById(R.id.ivItem3);
        TextView tvDescription3=v.findViewById(R.id.tvDescription3);

        bindThreeEventAndShow(v,ivItem1,tvDescription1,ivItem2,tvDescription2,ivItem3,tvDescription3);
        return v;
    }
}
