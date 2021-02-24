package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityTravelNewsBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2travelnote.DetailNewsTravelFragment;
import com.namviet.vtvtravel.view.fragment.f2travelnote.TravelNewsFragment;

import java.util.Arrays;

public class TravelNewsActivity extends BaseActivityNew<F2ActivityTravelNewsBinding> {
    public static final String DATA = "data";
    private boolean isTravelNews = true;
    private String detailLink;
    private int screenType;

//    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741";
    private static final String AD_UNIT_ID = "ca-app-pub-4768094881665076/6869444301";
    private FrameLayout adContainerView;
    private AdView adView;

    private InterstitialAd mInterstitialAd;


    public class OpenType {
        public static final int LIST = 0;
        public static final int DETAIL = 1;

    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_travel_news;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        screenType = getIntent().getIntExtra(Constants.IntentKey.SCREEN_TYPE, SmallLocationActivity.OpenType.LIST);
        if (screenType == SmallLocationActivity.OpenType.LIST) {
            isTravelNews = getIntent().getBooleanExtra(DATA, true);
        }else {
            detailLink = getIntent().getStringExtra(Constants.IntentKey.DETAIL_LINK);
        }
    }

    @Override
    public void doAfterOnCreate() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("323D3E715DE154B6B9929289FFA35B5E")).build());

        adContainerView = findViewById(R.id.ad_view_container);

        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });



        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error

                mInterstitialAd = null;
            }
        });



    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        if(screenType == OpenType.LIST) {
            return new TravelNewsFragment(isTravelNews);
        }else {
            DetailNewsTravelFragment detailNewsTravelFragment = new DetailNewsTravelFragment();
            detailNewsTravelFragment.setDetailLink(detailLink);
            return detailNewsTravelFragment;
        }
    }

    public static void openScreen(Activity activity, boolean isTravelNews, int screenType) {
        Intent intent = new Intent(activity, TravelNewsActivity.class);
        intent.putExtra(DATA, isTravelNews);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        activity.startActivity(intent);
    }

    public static void openScreenDetail(Activity activity, int screenType, String detailLink ) {
        Intent intent = new Intent(activity, TravelNewsActivity.class);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        intent.putExtra(Constants.IntentKey.DETAIL_LINK, detailLink);
        activity.startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (mInterstitialAd != null) {
//            mInterstitialAd.show(this);
//        } else {
//            Log.d("TAG", "The interstitial ad wasn't ready yet.");
//        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void loadBanner() {
        // Create an ad request.
        adView = new AdView(this);
        adView.setAdUnitId(AD_UNIT_ID);
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationBannerAdSizeWithWidth(this, adWidth);
    }

}
