package com.namviet.vtvtravel.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.ultils.TimeUtilities;


/**
 * Created by admin on 10/20/2016.
 */
public class MyVideoContent extends LinearLayout implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, SeekBar.OnSeekBarChangeListener {

    private MyVideoView vdGameQuiz;
    private ImageView ivPlayVideo;
    private TextView tvVideoTimeCurrent;
    private SeekBar sbTimeVideo;
    private TextView tvVideoTimeTotal;
    private ImageView ivExpVideo;
    private ProgressBar prLoading;
    private ImageView ivPoster;
    private Handler myHandler = new Handler();
    private boolean isFullscreen;
    private ShowFull mShowFull;

    public MyVideoContent(Context context) {
        super(context);
        initLayout();
    }

    public MyVideoContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public MyVideoContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        inflate(getContext(), R.layout.video_content, this);
        vdGameQuiz = findViewById(R.id.vd_game_quiz);
        ivPlayVideo = findViewById(R.id.iv_play_video);
        tvVideoTimeCurrent = findViewById(R.id.tv_video_time_current);
        sbTimeVideo = findViewById(R.id.sb_time_video);
        tvVideoTimeTotal = findViewById(R.id.tv_video_time_total);
        ivExpVideo = findViewById(R.id.iv_exp_video);
        prLoading = findViewById(R.id.pr_loading);
        ivPoster = findViewById(R.id.ivPoster);
        sbTimeVideo.setProgress(0);
        sbTimeVideo.setMax(100);
        ivPlayVideo.setOnClickListener(this);
        ivExpVideo.setOnClickListener(this);
        sbTimeVideo.setOnSeekBarChangeListener(this);

    }

    public void setIvPoster(String url) {
        setImageUrl(url, ivPoster);
    }

    public void initVideo(String url) {
        Uri video = Uri.parse(url);
        prLoading.setVisibility(VISIBLE);
        vdGameQuiz.setVideoURI(video);
        vdGameQuiz.setOnPreparedListener(this);
        vdGameQuiz.setOnCompletionListener(this);
        vdGameQuiz.setOnErrorListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == ivExpVideo) {
//            mShowFull.onFull();
        } else if (view == ivPlayVideo) {
            if (vdGameQuiz.isPlaying()) {
                vdGameQuiz.pause();
                ivPlayVideo.setImageResource(R.mipmap.audio_pause);
            } else {
                vdGameQuiz.start();
                ivPlayVideo.setImageResource(R.mipmap.audio_play);
            }
        }

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        ivPlayVideo.setImageResource(R.mipmap.audio_pause);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        vdGameQuiz.start();
        prLoading.setVisibility(GONE);
        ivPoster.setVisibility(GONE);
        ivPlayVideo.setImageResource(R.mipmap.audio_play);
        myHandler.postDelayed(UpdateSongTime, 100);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            long totalDuration = vdGameQuiz.getDuration();
            long currentDuration = vdGameQuiz.getCurrentPosition();
            tvVideoTimeTotal.setText("" + TimeUtilities.milliSecondsToTimer(totalDuration));
            tvVideoTimeCurrent.setText("" + TimeUtilities.milliSecondsToTimer(currentDuration));
            int progress = (TimeUtilities.getProgressPercentage(currentDuration, totalDuration));
            sbTimeVideo.setProgress(progress);
            myHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        myHandler.removeCallbacks(UpdateSongTime);
        int totalDuration = vdGameQuiz.getDuration();
        int currentPosition = TimeUtilities.progressToTimer(seekBar.getProgress(), totalDuration);
        sbTimeVideo.setProgress(currentPosition);
        vdGameQuiz.seekTo(currentPosition);

        myHandler.postDelayed(UpdateSongTime, 100);
    }

    public void pauseVideo() {
        if (vdGameQuiz.isPlaying()) {
            vdGameQuiz.pause();
            ivPlayVideo.setImageResource(R.mipmap.audio_pause);
        }
        myHandler.removeCallbacks(UpdateSongTime);
    }

    public void playVideo() {
        myHandler.removeCallbacks(UpdateSongTime);
        if (!vdGameQuiz.isPlaying()) {
            vdGameQuiz.start();
            ivPlayVideo.setImageResource(R.mipmap.audio_play);
        }
        myHandler.postDelayed(UpdateSongTime, 100);

    }

    public void stopVideo() {
        myHandler.removeCallbacks(UpdateSongTime);
        vdGameQuiz.stopPlayback();
    }

    public void setmShowFull(ShowFull mShowFull) {
        this.mShowFull = mShowFull;
    }

    public interface ShowFull {
        void onFull();
    }

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(getContext()).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);

    }

}
