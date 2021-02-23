package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityTravelNewsBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2travelnote.DetailNewsTravelFragment;
import com.namviet.vtvtravel.view.fragment.f2travelnote.TravelNewsFragment;

public class TravelNewsActivity extends BaseActivityNew<F2ActivityTravelNewsBinding> {
    private InterstitialAd mInterstitialAd;
    public static final String DATA = "data";
    private boolean isTravelNews = true;
    private String detailLink;
    private int screenType;
    private AdView adView;


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
        MobileAds.initialize(this);
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("323D3E715DE154B6B9929289FFA35B5E").build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("323D3E715DE154B6B9929289FFA35B5E").build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
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

    private AdSize getAdSize() {
        return AdSize.FULL_BANNER;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mInterstitialAd.show();
    }
}
