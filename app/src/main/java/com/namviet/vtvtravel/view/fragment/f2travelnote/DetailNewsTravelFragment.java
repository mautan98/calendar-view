package com.namviet.vtvtravel.view.fragment.f2travelnote;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelnews.CommentInDetailTravelNewsAdapter;
import com.namviet.vtvtravel.adapter.travelnews.NearByInTravelDetailAdapter;
import com.namviet.vtvtravel.adapter.travelnews.RelationNewInTravelNewsAdapter;
import com.namviet.vtvtravel.adapter.travelnews.RelationNewsInTravelDetailAdapter;
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentDetailNewsTravelBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnCommentSuccessInTravelNews;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2topexperience.SubTopExperienceResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.response.travelnews.PlaceNearByResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.TextJustification;
import com.namviet.vtvtravel.view.f2.CommentActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.ShareActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.TopExperienceActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.view.fragment.share.ShareBottomDialog;
import com.namviet.vtvtravel.view.fragment.topexperience.SubTopExperienceFragment;
import com.namviet.vtvtravel.viewmodel.f2topexperience.SubTopExperienceViewModel;
import com.namviet.vtvtravel.viewmodel.f2travelnews.DetailNewsTravelViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class DetailNewsTravelFragment extends BaseFragment<F2FragmentDetailNewsTravelBinding> implements Observer {
    private PublishSubject<Boolean> btnReadMoreStatus;


    private DetailNewsTravelViewModel viewModel;
    private String detailLink;
    private CommentInDetailTravelNewsAdapter commentInDetailTravelNewsAdapter;
    private NearByInTravelDetailAdapter nearByInTravelDetailAdapter;
    private RelationNewsInTravelDetailAdapter relationNewsInTravelDetailAdapter;
    private DetailTravelNewsResponse detailTravelNewsResponse;
    private String urlDefault;
    private int count = 0;

    private RelationNewInTravelNewsAdapter relationNewInTravelNewsAdapter;

    private SubTopExperienceViewModel subTopExperienceViewModel;
    private NearByInTravelDetailAdapter nearByPlaceAdapter;
    private NearByInTravelDetailAdapter relationPlaceAdapter;
    private List<Travel> travelsNearBy = new ArrayList<>();
    private List<Travel> travelsRelation = new ArrayList<>();

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


    private io.reactivex.Observer<Boolean> getObserverOfBtnShowMore(){
        return new io.reactivex.Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Boolean aBoolean) {
                if(aBoolean){
                    getBinding().btnReadMore.setVisibility(View.VISIBLE);
                }else {
                    getBinding().btnReadMore.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }


    @Override
    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnReadMoreStatus = PublishSubject.create();
                btnReadMoreStatus.subscribe(getObserverOfBtnShowMore());

                viewModel = new DetailNewsTravelViewModel();

                getBinding().setDetailNewsTravelViewModel(viewModel);
                viewModel.addObserver(DetailNewsTravelFragment.this);
                viewModel.getDetailNewsTravel(detailLink);



                subTopExperienceViewModel = new SubTopExperienceViewModel();
                subTopExperienceViewModel.addObserver(DetailNewsTravelFragment.this);


                nearByPlaceAdapter = new NearByInTravelDetailAdapter(mActivity, travelsNearBy, new NearByInTravelDetailAdapter.ClickItem() {
                    @Override
                    public void onClickItem(Travel travel) {
                        try {
                            SmallLocationActivity.startScreenDetail(mActivity, SmallLocationActivity.OpenType.DETAIL, travel.getDetail_link());
                        } catch ( Exception  e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void likeEvent(int position) {
                        try {
                            likeOrUnLike(travelsNearBy.get(position));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                getBinding().rclNearByPlace.setAdapter(nearByPlaceAdapter);

                relationPlaceAdapter = new NearByInTravelDetailAdapter(mActivity, travelsRelation, new NearByInTravelDetailAdapter.ClickItem() {
                    @Override
                    public void onClickItem(Travel travel) {
                        try {
                            SmallLocationActivity.startScreenDetail(mActivity, SmallLocationActivity.OpenType.DETAIL, travel.getDetail_link());
                        } catch ( Exception  e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void likeEvent(int position) {
                        try {
                            likeOrUnLike(travelsRelation.get(position));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                getBinding().rclRelationPlace.setAdapter(relationPlaceAdapter);

                getBinding().rclNearByPlace.setNestedScrollingEnabled(false);
                getBinding().rclRelationPlace.setNestedScrollingEnabled(false);
                getBinding().rclRelation.setNestedScrollingEnabled(false);

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

        getBinding().btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getBinding().rclRelationPlace.getVisibility() == View.VISIBLE) {
                    TopExperienceActivity.startScreenFromTravelNews(mActivity, detailTravelNewsResponse.getData().getRelatedPlaces(), TopExperienceActivity.Type.RELATION_EXPERIENCE);
                }else {
                    TopExperienceActivity.startScreenFromTravelNews(mActivity, detailTravelNewsResponse.getData().getPlaceNearBy(), TopExperienceActivity.Type.NEAR_BY_EXPERIENCE);
                }
            }
        });

        getBinding().btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new RelationNewsFragment(detailTravelNewsResponse.getData().getRelatedNews()));
            }
        });
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
                    viewModel.getPlaceNearBy(detailTravelNewsResponse.getData().getRelatedNews().getApi_link());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                genPlaceView(detailTravelNewsResponse.getData().getRelatedPlaces().getApi_link(), detailTravelNewsResponse.getData().getPlaceNearBy().getApi_link());
                btnReadMoreStatus.onNext(true);


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


//                    nearByInTravelDetailAdapter = new NearByInTravelDetailAdapter(mActivity, detailTravelNewsResponse.getData().getNearBy().getItems(), new NearByInTravelDetailAdapter.ClickItem() {
//                        @Override
//                        public void onClickItem(Travel travel) {
//                            try {
//                                SmallLocationActivity.startScreenDetail(mActivity, SmallLocationActivity.OpenType.DETAIL, travel.getDetail_link());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void likeEvent(int position) {
//                            try {
//                                Account account = MyApplication.getInstance().getAccount();
//                                Travel travel = detailTravelNewsResponse.getData().getNearBy().getItems().get(position);
//                                if (null != account && account.isLogin()) {
//                                    viewModel.likeEvent(travel.getId(), travel.getContent_type());
//                                    try {
//                                        TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.NEWS_DETAIL, TrackingAnalytic.ScreenTitle.NEWS_DETAIL)
//                                                .setContent_type(travel.getContent_type())
//                                                .setContent_id(travel.getId())
//                                                .setScreen_class(this.getClass().getName()));
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    if (travel.isLiked()) {
//                                        travel.setLiked(false);
//                                    } else {
//                                        travel.setLiked(true);
//                                    }
//                                    nearByInTravelDetailAdapter.notifyItemChanged(position);
//                                } else {
//                                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                    getBinding().rclNearBy.setAdapter(nearByInTravelDetailAdapter);
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
            } else if(o instanceof PlaceNearByResponse){
                PlaceNearByResponse placeNearByResponse = (PlaceNearByResponse) o;
//                getBinding().rclRelation.
                relationNewInTravelNewsAdapter = new RelationNewInTravelNewsAdapter(mActivity, placeNearByResponse.getData().getPlaces(), new RelationNewInTravelNewsAdapter.ClickItem() {
                    @Override
                    public void onClickItem(Travel travel) {
                        TravelNewsActivity.openScreenDetail(mActivity, TravelNewsActivity.OpenType.DETAIL, travel.getDetail_link());
                    }
                });

                getBinding().rclRelation.setAdapter(relationNewInTravelNewsAdapter);
            }else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        } else if(observable instanceof SubTopExperienceViewModel && null != o){
            if (o instanceof SubTopExperienceResponse) {
                SubTopExperienceResponse response = (SubTopExperienceResponse) o;
                if(response.getType() == 0){
                    travelsRelation.clear();
                    travelsRelation.addAll(response.getData().getItems());
                    relationPlaceAdapter.notifyDataSetChanged();
                }else {
                    travelsNearBy.clear();
                    travelsNearBy.addAll(response.getData().getItems());
                    nearByPlaceAdapter.notifyDataSetChanged();
                }
            }
        }
    }


    private void likeOrUnLike(Travel travel){
        try {
            Account account = MyApplication.getInstance().getAccount();
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
//                if (travel.isLiked()) {
//                    travel.setLiked(false);
//                } else {
//                    travel.setLiked(true);
//                }
//                nearByInTravelDetailAdapter.notifyItemChanged(position);
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
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


    private void genPlaceView(String detailLink1, String detailLink2) {
        try {

            subTopExperienceViewModel.getSubTopExperience(detailLink1, 0);
            subTopExperienceViewModel.getSubTopExperience(detailLink2, 1);

            View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style_in_travel_news, null);
            TextView tvHome = tabHome.findViewById(R.id.tvTitle);
            tvHome.setText("Địa điểm liên quan");
            tvHome.setTextColor(Color.parseColor("#101010"));
            View view = tabHome.findViewById(R.id.indicator);
            view.setVisibility(View.VISIBLE);
            getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setCustomView(tabHome));

            View tabPlace = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style_in_travel_news, null);
            TextView tvPlace = tabPlace.findViewById(R.id.tvTitle);
            tvPlace.setText("Địa điểm gần bạn");
            tvPlace.setTextColor(Color.parseColor("#00918D"));
            View view1 = tabPlace.findViewById(R.id.indicator);
            view1.setVisibility(View.INVISIBLE);
            getBinding().tabLayout.addTab(getBinding().tabLayout.newTab().setCustomView(tabPlace));
            getBinding().tabLayout.addOnTabSelectedListener(OnTabSelectedListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private TabLayout.OnTabSelectedListener OnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            setOnSelectView(getBinding().tabLayout, c);
            if(tab.getPosition() == 0){
                getBinding().rclRelationPlace.setVisibility(View.VISIBLE);
                getBinding().rclNearByPlace.setVisibility(View.GONE);
            }else {
                getBinding().rclRelationPlace.setVisibility(View.GONE);
                getBinding().rclNearByPlace.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            setUnSelectView(getBinding().tabLayout, c);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    public void setOnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tvTitle);
        View view = selected.findViewById(R.id.indicator);
        view.setVisibility(View.VISIBLE);
        iv_text.setTextColor(Color.parseColor("#00918D"));

    }

    public void setUnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tvTitle);
        View view = selected.findViewById(R.id.indicator);
        view.setVisibility(View.INVISIBLE);
        iv_text.setTextColor(Color.parseColor("#101010"));

    }


}
