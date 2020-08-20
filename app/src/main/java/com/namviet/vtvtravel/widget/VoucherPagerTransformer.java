package com.namviet.vtvtravel.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

public class VoucherPagerTransformer implements ViewPager.PageTransformer {

    private int maxTranslateOffsetX;
    private ViewPager viewPager;

    private float offset = -1;
    private float paddingLeft;
    private float minScale;
    private float minAlpha;

    public VoucherPagerTransformer(float paddingLeft, float minScale, float minAlpha) {
        this.paddingLeft = paddingLeft;
        this.minAlpha = minAlpha;
        this.minScale = minScale;
//        this.maxTranslateOffsetX = dp2px(context, 180);
    }

    public void transformPage(View page, float position) {
        final float normalizedposition = Math.abs(Math.abs(position) - 1);
        page.setScaleX(normalizedposition / 2 + 0.5f);
        page.setScaleY(normalizedposition / 2 + 0.5f);
    }

    /**
     * dp和像素转换
     */
    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

}
