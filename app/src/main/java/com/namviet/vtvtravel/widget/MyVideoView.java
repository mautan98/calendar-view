package com.namviet.vtvtravel.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.VideoView;

import java.lang.reflect.Method;

/**
 * Created by admin on 10/21/2016.
 */
public class MyVideoView extends VideoView {
    private int width;
    private int height;
    private Context context;
    private VideoSizeChangeListener listener;
    private boolean isFullscreen;

    public MyVideoView(Context context) {
        super(context);
        init(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setScreenSize();
    }

    /**
     * calculate real screen size
     */
    private void setScreenSize() {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= 17) {
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            width = realMetrics.widthPixels;
            height = realMetrics.heightPixels;

        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                width = (Integer) mGetRawW.invoke(display);
                height = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                width = display.getWidth();
                height = display.getHeight();
            }

        } else {
            width = display.getWidth();
            height = display.getHeight();
        }
        if (width > height) {
            int temp = width;
            width = height;
            height = temp;
        }
    }
    public void setVideoSizeChangeListener(VideoSizeChangeListener listener) {
        this.listener = listener;
    }

    public interface VideoSizeChangeListener {
        void onFullScreen();
        void onNormalSize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setSize(height, width);
            if (listener != null) listener.onFullScreen();
            isFullscreen = true;
        } else {
            setSize(width, width * 9 / 16);
            if (listener != null) listener.onNormalSize();
            isFullscreen = false;
        }
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    private void setSize(int w, int h) {
        setMeasuredDimension(w, h);
        getHolder().setFixedSize(w, h);
    }
}
