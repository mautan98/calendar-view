package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.R;

public class TwoItemSlider extends BaseSliderView {

    public TwoItemSlider(Context context) {
        super(context);
    }


    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_two_item, null);

        ImageView ivItem2 = v.findViewById(R.id.ivItem2);
        TextView tvDescription2 = v.findViewById(R.id.tvDescription2);

        ImageView ivItem3 = v.findViewById(R.id.ivItem3);
        TextView tvDescription3 = v.findViewById(R.id.tvDescription3);

        bindTwoEventAndShow(v,ivItem2,tvDescription2,ivItem3,tvDescription3);
        return v;
    }
}
