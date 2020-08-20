package com.namviet.vtvtravel.listener;

import android.view.View;

import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.model.Video;

public interface VideoSelectListener {
    void onSelectVideo(Video video,int position,View view);
}
