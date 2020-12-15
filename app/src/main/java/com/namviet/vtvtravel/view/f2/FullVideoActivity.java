package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

public class FullVideoActivity extends BaseActivityNew implements VideoPlayerEvents.OnFullscreenListener {
    JWPlayerView jwPlayerView;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_full_video;
    }

    @Override
    public int getFrame() {
        return 0;
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void doAfterOnCreate() {
        String url = getIntent().getExtras().getString("URL_VIDEO");
//        String url = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
        jwPlayerView = findViewById(R.id.jwPlayer);
        jwPlayerView.addOnFullscreenListener(this);


        PlaylistItem pi = new PlaylistItem.Builder()
                .file(url)
                .build();
        jwPlayerView.load(pi);
        jwPlayerView.play();
        jwPlayerView.setFullscreen(true, false);
    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return null;
    }

    @Override
    protected void onDestroy() {
        jwPlayerView.onDestroy();
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        jwPlayerView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        jwPlayerView.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        jwPlayerView.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        jwPlayerView.onStart();
    }

    @Override
    public void onFullscreen(FullscreenEvent fullscreenEvent) {
        if (fullscreenEvent.getFullscreen()) {

        } else {
            finish();
        }
    }

    public static void openScreen(Context activity, String link) {
        Intent intent = new Intent(activity, FullVideoActivity.class);
        intent.putExtra("URL_VIDEO", link);
        activity.startActivity(intent);
    }

}