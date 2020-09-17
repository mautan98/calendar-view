package com.namviet.vtvtravel.view.fragment.f2travelnote;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelnews.CommentInDetailTravelNewsAdapter;
import com.namviet.vtvtravel.adapter.travelnews.NearByInTravelDetailAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentDetailNewsTravelBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnCommentSuccessInTravelNews;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.CommentActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
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
    private DetailTravelNewsResponse detailTravelNewsResponse;

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
                    F2Util.startSenDataText(mActivity, detailTravelNewsResponse.getData().getLink_share());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.SHARE, TrackingAnalytic.getDefault().setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
                getComment(detailTravelNewsResponse.getData().getId());
                getBinding().webViewContent.loadDataWithBaseURL("", detailTravelNewsResponse.getData().getDescription(), "text/html", "UTF-8", null);
                getBinding().webViewContent.setWebViewClient(new CustomWebViewClient());
                try {
                    nearByInTravelDetailAdapter = new NearByInTravelDetailAdapter(mActivity, detailTravelNewsResponse.getData().getNearBy().getItems(), new NearByInTravelDetailAdapter.ClickItem() {
                        @Override
                        public void onClickItem(Travel travel) {
                            try {
                                SmallLocationActivity.startScreenDetail(mActivity, SmallLocationActivity.OpenType.DETAIL, travel.getDetail_link());
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
                    if(detailTravelNewsResponse.getData().getShort_description() == null || detailTravelNewsResponse.getData().getShort_description().isEmpty()){
                        getBinding().tvSapo.setVisibility(View.GONE);
                    }else {
                        getBinding().tvSapo.setVisibility(View.VISIBLE);
                        getBinding().tvSapo.setText(detailTravelNewsResponse.getData().getShort_description());
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
            getBinding().shimmerViewContainer.setVisibility(View.GONE);
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
    public void onCommentSuccessInTravelNews(OnCommentSuccessInTravelNews onCommentSuccessInTravelNews){
        getComment(detailTravelNewsResponse.getData().getId());
    }
}
