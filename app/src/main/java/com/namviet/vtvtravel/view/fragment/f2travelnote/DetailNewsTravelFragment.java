package com.namviet.vtvtravel.view.fragment.f2travelnote;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baseapp.activity.BaseActivity;
import com.like.LikeButton;
import com.like.OnLikeListener;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelnews.CommentInDetailTravelNewsAdapter;
import com.namviet.vtvtravel.adapter.travelnews.NearByInTravelDetailAdapter;
import com.namviet.vtvtravel.adapter.travelnews.RelationNewsInTravelDetailAdapter;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentDetailNewsTravelBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnCommentSuccessInTravelNews;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.TextJustification;
import com.namviet.vtvtravel.view.f2.CommentActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.ShareActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.view.fragment.share.ShareBottomDialog;
import com.namviet.vtvtravel.viewmodel.f2travelnews.DetailNewsTravelViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DetailNewsTravelFragment extends BaseFragment<F2FragmentDetailNewsTravelBinding> implements Observer {
    private DetailNewsTravelViewModel viewModel;
    private String detailLink;
    private CommentInDetailTravelNewsAdapter commentInDetailTravelNewsAdapter;
    private NearByInTravelDetailAdapter nearByInTravelDetailAdapter;
    private RelationNewsInTravelDetailAdapter relationNewsInTravelDetailAdapter;
    private DetailTravelNewsResponse detailTravelNewsResponse;
    private String urlDefault;
    private int count = 0;

    public DetailNewsTravelFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_news_travel;
    }

    @Override
    public void initView() {
        getBinding().rclComment.setNestedScrollingEnabled(false);
        getBinding().rclNearBy.setNestedScrollingEnabled(false);
//        getBinding().webViewContent.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        getBinding().webViewContent.getSettings().setJavaScriptEnabled(true);
        getBinding().webViewContentCache.getSettings().setJavaScriptEnabled(true);
//        getBinding().webViewContent.setInitialScale(1);      //webview page matches the screen size.
//        getBinding().webViewContent.getSettings().setLoadWithOverviewMode(true);
//        getBinding().webViewContent.getSettings().setUseWideViewPort(true);
//        getBinding().webViewContent.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }


    @Override
    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel = new DetailNewsTravelViewModel();

                getBinding().setDetailNewsTravelViewModel(viewModel);
                viewModel.addObserver(DetailNewsTravelFragment.this);
                viewModel.getDetailNewsTravel(detailLink);

            }
        }, 500);


//        Glide.with(mActivity).asGif().load(R.drawable.like_animation).into(getBinding().imgEffect);


    }

    @Override
    public void inject() {

    }

    private void getComment(String contentId) {
        viewModel.getComment(contentId);
    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().btnViewMoreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailTravelNewsResponse != null) {
                    CommentActivity.startScreen(mActivity, detailTravelNewsResponse, null);
                }
            }
        });

        getBinding().layoutWriteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailTravelNewsResponse != null) {
                    CommentActivity.startScreen(mActivity, detailTravelNewsResponse, null);
                }
            }
        });

        getBinding().btnShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    ShareBottomDialog shareBottomDialog = new ShareBottomDialog(new ShareBottomDialog.DoneClickShare() {
                        @Override
                        public void onDoneClickShare(boolean isVTVApp) {
                            if (isVTVApp) {
                                ShareActivity.startScreen(mActivity, detailTravelNewsResponse.getData().getTitle(), detailLink, detailTravelNewsResponse.getData().getLogo_url(), detailTravelNewsResponse.getData().getContent_type());
                            } else {
//                                String linkShare = WSConfig.HOST_LANDING + F2Util.genEndPointShareLink(Constants.ShareLinkType.NEWS, detailLink);
//                                F2Util.startSenDataText(mActivity, linkShare);
                                F2Util.startSenDataText(mActivity, detailTravelNewsResponse.getData().getLink_share());
                            }
                        }
                    });

                    shareBottomDialog.show(mActivity.getSupportFragmentManager(), null);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.SHARE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.NEWS_DETAIL, TrackingAnalytic.ScreenTitle.NEWS_DETAIL)
                            .setContent_id(detailTravelNewsResponse.getData().getId())
                            .setContent_type(detailTravelNewsResponse.getData().getContent_type())
                            .setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getBinding().imgHeart.setOnLikeListener(new OnLikeListener() {
            @Override

            public void liked(LikeButton likeButton) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickHeart();

                    }
                }, 100);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickHeart();
                    }
                }, 100);
            }
        });

//        getBinding().imgHeart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Account account = MyApplication.getInstance().getAccount();
//                    if (null != account && account.isLogin()) {
//                        viewModel.likeEvent(detailTravelNewsResponse.getData().getId(), detailTravelNewsResponse.getData().getContent_type());
//                        try {
//                            TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.NEWS_DETAIL, TrackingAnalytic.ScreenTitle.NEWS_DETAIL)
//                                    .setContent_type(detailTravelNewsResponse.getData().getContent_type())
//                                    .setContent_id(detailTravelNewsResponse.getData().getId())
//                                    .setScreen_class(this.getClass().getName()));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        if (detailTravelNewsResponse.getData().isLiked()) {
//                            detailTravelNewsResponse.getData().setLiked(false);
////                            getBinding().imgHeart.setImageResource(R.drawable.f2_ic_gray_heart);
//                            getBinding().imgHeart.setLiked(false);
//                        } else {
//                            detailTravelNewsResponse.getData().setLiked(true);
////                            getBinding().imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
//                            getBinding().imgHeart.setLiked(true);
//                        }
//                    } else {
//                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    private void clickHeart() {
        try {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                viewModel.likeEvent(detailTravelNewsResponse.getData().getId(), detailTravelNewsResponse.getData().getContent_type());
                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.NEWS_DETAIL, TrackingAnalytic.ScreenTitle.NEWS_DETAIL)
                            .setContent_type(detailTravelNewsResponse.getData().getContent_type())
                            .setContent_id(detailTravelNewsResponse.getData().getId())
                            .setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (detailTravelNewsResponse.getData().isLiked()) {
                    detailTravelNewsResponse.getData().setLiked(false);
//                            getBinding().imgHeart.setImageResource(R.drawable.f2_ic_gray_heart);
                    getBinding().imgHeart.setLiked(false);

                    try {
                        getBinding().tvLikeCount.setText(String.valueOf(Integer.parseInt(getBinding().tvLikeCount.getText().toString()) - 1));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    detailTravelNewsResponse.getData().setLiked(true);
//                            getBinding().imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
                    getBinding().imgHeart.setLiked(true);
                    try {
                        getBinding().tvLikeCount.setText(String.valueOf(Integer.parseInt(getBinding().tvLikeCount.getText().toString()) + 1));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof DetailNewsTravelViewModel && null != o) {
            if (o instanceof DetailTravelNewsResponse) {

                getBinding().shimmerMain.setVisibility(View.GONE);
                detailTravelNewsResponse = (DetailTravelNewsResponse) o;

                try {
                    getBinding().tvLikeCount.setText(detailTravelNewsResponse.getData().getLikeCount());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (detailTravelNewsResponse.getData().isLiked()) {
//                        getBinding().imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
                        getBinding().imgHeart.setLiked(true);
                    } else {
//                        getBinding().imgHeart.setImageResource(R.drawable.f2_ic_gray_heart);
                        getBinding().imgHeart.setLiked(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getComment(detailTravelNewsResponse.getData().getId());
                urlDefault =  detailTravelNewsResponse.getData().getDescription();
                getBinding().webViewContent.loadDataWithBaseURL("", urlDefault, "text/html", "UTF-8", null);
                getBinding().webViewContentCache.loadDataWithBaseURL("", urlDefault, "text/html", "UTF-8", null);
                getBinding().webViewContentCache.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url)
                    {
                        count ++;
                        if(count > 1){
                            getBinding().webViewContentCache.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    if(view.getHeight() >0)
                                        getBinding().nsView.scrollTo(0, getBinding().webViewContentCache.getHeight()+ getBinding().lnlHeader.getHeight());
                                }
                            });

                        }
                    }
                });

                getBinding().webViewContent.setWebViewClient(new CustomWebViewClient());
                try {
                    try {
                        relationNewsInTravelDetailAdapter = new RelationNewsInTravelDetailAdapter(mActivity, detailTravelNewsResponse.getData().getRelations().getItems(), new RelationNewsInTravelDetailAdapter.ClickItem() {
                            @Override
                            public void onClickItem(Travel travel) {
                                TravelNewsActivity.openScreenDetail(mActivity, TravelNewsActivity.OpenType.DETAIL, travel.getDetail_linkV2());
                            }

                            @Override
                            public void likeEvent(int position) {

                            }
                        });
                        getBinding().rclRelationNews.setAdapter(relationNewsInTravelDetailAdapter);


                        if (detailTravelNewsResponse.getData().getRelations().getItems().size() != 0) {
                            getBinding().rclRelationNews.setVisibility(View.VISIBLE);
                        } else {
                            getBinding().rclRelationNews.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        getBinding().rclRelationNews.setVisibility(View.GONE);
                        e.printStackTrace();
                    }


                    nearByInTravelDetailAdapter = new NearByInTravelDetailAdapter(mActivity, detailTravelNewsResponse.getData().getNearBy().getItems(), new NearByInTravelDetailAdapter.ClickItem() {
                        @Override
                        public void onClickItem(Travel travel) {
                            try {
                                SmallLocationActivity.startScreenDetail(mActivity, SmallLocationActivity.OpenType.DETAIL, travel.getDetail_link());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void likeEvent(int position) {
                            try {
                                Account account = MyApplication.getInstance().getAccount();
                                Travel travel = detailTravelNewsResponse.getData().getNearBy().getItems().get(position);
                                if (null != account && account.isLogin()) {
                                    viewModel.likeEvent(travel.getId(), travel.getContent_type());
                                    try {
                                        TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.NEWS_DETAIL, TrackingAnalytic.ScreenTitle.NEWS_DETAIL)
                                                .setContent_type(travel.getContent_type())
                                                .setContent_id(travel.getId())
                                                .setScreen_class(this.getClass().getName()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (travel.isLiked()) {
                                        travel.setLiked(false);
                                    } else {
                                        travel.setLiked(true);
                                    }
                                    nearByInTravelDetailAdapter.notifyItemChanged(position);
                                } else {
                                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    getBinding().rclNearBy.setAdapter(nearByInTravelDetailAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getBinding().tvTitle.setText(detailTravelNewsResponse.getData().getName());
                getBinding().tvCategory.setText(detailTravelNewsResponse.getData().getTitle());
                try {
                    if (detailTravelNewsResponse.getData().getShort_description() == null || detailTravelNewsResponse.getData().getShort_description().isEmpty()) {
                        getBinding().tvSapo.setVisibility(View.GONE);
                    } else {
                        getBinding().tvSapo.setVisibility(View.VISIBLE);
                        getBinding().tvSapo.setText(detailTravelNewsResponse.getData().getShort_description());
                        try {
                            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                                TextJustification.justify(getBinding().tvSapo);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    getBinding().tvSapo.setVisibility(View.GONE);
                }

                try {
                    getBinding().tvDate.setText("" + DateUtltils.timeToString(Long.valueOf(detailTravelNewsResponse.getData().getCreated())));
                    getBinding().tvAuthor.setText(detailTravelNewsResponse.getData().getAuthor());

                    double value = Double.parseDouble(detailTravelNewsResponse.getData().getView_count());
                    if (value > 1000) {
                        double finalValue = Math.round(value / 1000 * 10.0) / 10.0;
                        getBinding().tvView.setText(finalValue + "k");
                    } else {
                        getBinding().tvView.setText(detailTravelNewsResponse.getData().getView_count());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.NEWS_DETAIL, TrackingAnalytic.ScreenTitle.NEWS_DETAIL)
                            .setScreen_class(this.getClass().getName())
                            .setContent_id(detailTravelNewsResponse.getData().getId())
                            .setCategory_tree_code(detailTravelNewsResponse.getData().getCategory_tree_code())
                            .setCategory_tree_name(detailTravelNewsResponse.getData().getCategory_tree_name())
                            .setContent_type("news"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (o instanceof CommentResponse) {
                CommentResponse response = (CommentResponse) o;
                List<CommentResponse.Data.Comment> comments = response.getData().getContent();
                if (comments == null) {
                    getBinding().layoutViewAllComment.setVisibility(View.GONE);
                } else if (comments.size() >= 3) {
                    getBinding().layoutViewAllComment.setVisibility(View.VISIBLE);
                    getBinding().tvCommentLeft.setText("Xem tất cả " + comments.size() + " bình luận");
                } else {
                    getBinding().layoutViewAllComment.setVisibility(View.GONE);
                }
                commentInDetailTravelNewsAdapter = new CommentInDetailTravelNewsAdapter(mActivity, response.getData().getContent(), new CommentInDetailTravelNewsAdapter.ClickItem() {
                    @Override
                    public void onClickItem(CommentResponse.Data.Comment comment) {

                    }

                    @Override
                    public void onClickReply(CommentResponse.Data.Comment comment) {
                        CommentActivity.startScreen(mActivity, detailTravelNewsResponse, comment.getId());
                    }
                });
                getBinding().rclComment.setAdapter(commentInDetailTravelNewsAdapter);
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }


    class CustomWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try {
                if(view.getOriginalUrl().contains("#")){
                    String index = view.getOriginalUrl().substring(view.getOriginalUrl().indexOf("#")+1);
                    String mUrl = urlDefault.substring(0,urlDefault.lastIndexOf(index));
                    getBinding().webViewContentCache.loadDataWithBaseURL("", mUrl, "text/html", "UTF-8", null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            getBinding().shimmerViewContainer.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("webviewww", "0");
            getBinding().shimmerViewContainer.setVisibility(View.GONE);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.e("webviewww", "1");

        }

        @Override
        public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
            Log.e("webviewww", "2");
            return super.onRenderProcessGone(view, detail);


        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCommentSuccessInTravelNews(OnCommentSuccessInTravelNews onCommentSuccessInTravelNews) {
        getComment(detailTravelNewsResponse.getData().getId());
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.NEWS_DETAIL, TrackingAnalytic.ScreenTitle.NEWS_DETAIL);
    }


}
