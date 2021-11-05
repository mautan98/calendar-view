package com.namviet.vtvtravel.view.fragment.f2smalllocation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.smalllocation.RatingAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentSmallLocationRatingBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnReviewSuccess;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog;
import com.namviet.vtvtravel.viewmodel.f2review.ReviewViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class RatingSmallLocationFragment extends BaseFragment<F2FragmentSmallLocationRatingBinding> implements Observer, RatingAdapter.ClickItem {

    private RatingAdapter ratingAdapter;
    private ReviewViewModel viewModel;
    private List<GetReviewResponse.Data.Content> contentList = new ArrayList<>();
    private DetailSmallLocationResponse detailSmallLocationResponse;

    @SuppressLint("ValidFragment")
    public RatingSmallLocationFragment(DetailSmallLocationResponse detailSmallLocationResponse) {
        this.detailSmallLocationResponse = detailSmallLocationResponse;
    }


    public RatingSmallLocationFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_small_location_rating;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        viewModel = new ReviewViewModel();
        getBinding().setReviewViewModel(viewModel);
        viewModel.addObserver(this);

        ratingAdapter = new RatingAdapter(contentList, mActivity, this, false);
        getBinding().rclRating.setAdapter(ratingAdapter);

        viewModel.getReview(detailSmallLocationResponse.getData().getId(), null);

        reload();


    }

    private void reload() {
        try {
            getBinding().tvName.setText(detailSmallLocationResponse.getData().getTabs().get(0).getName());
            getBinding().tvRating.setText(detailSmallLocationResponse.getData().getTabs().get(0).getEvaluate());
            getBinding().tvContentRating.setText(detailSmallLocationResponse.getData().getTabs().get(0).getEvaluate_text());
            getBinding().tvCommentCount.setText(detailSmallLocationResponse.getData().getTabs().get(0).getComment_count());

            getBinding().tvRating2.setText(detailSmallLocationResponse.getData().getTabs().get(0).getEvaluate());
            getBinding().tvContentRating2.setText(detailSmallLocationResponse.getData().getTabs().get(0).getEvaluate_text());
            getBinding().tvCommentCount2.setText(detailSmallLocationResponse.getData().getTabs().get(0).getComment_count());


            getBinding().progress1.setProgress(getPercent(detailSmallLocationResponse.getData().getTabs().get(0).getCount_1()));
            getBinding().progress2.setProgress(getPercent(detailSmallLocationResponse.getData().getTabs().get(0).getCount_2()));
            getBinding().progress3.setProgress(getPercent(detailSmallLocationResponse.getData().getTabs().get(0).getCount_3()));
            getBinding().progress4.setProgress(getPercent(detailSmallLocationResponse.getData().getTabs().get(0).getCount_4()));
            getBinding().progress5.setProgress(getPercent(detailSmallLocationResponse.getData().getTabs().get(0).getCount_5()));

            getBinding().tvCountRating1.setText(detailSmallLocationResponse.getData().getTabs().get(0).getCount_1());
            getBinding().tvCountRating2.setText(detailSmallLocationResponse.getData().getTabs().get(0).getCount_2());
            getBinding().tvCountRating3.setText(detailSmallLocationResponse.getData().getTabs().get(0).getCount_3());
            getBinding().tvCountRating4.setText(detailSmallLocationResponse.getData().getTabs().get(0).getCount_4());
            getBinding().tvCountRating5.setText(detailSmallLocationResponse.getData().getTabs().get(0).getCount_5());
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            DetailSmallLocationResponse.Data.Tab tab = detailSmallLocationResponse.getData().getTabs().get(1);
            if (tab.isHas_location()) {
                if (tab.getDistance() != null && !"".equals(tab.getDistance()) && Double.parseDouble(tab.getDistance()) < 1000) {
                    getBinding().tvDistance.setText(tab.getDistance_text() + " " + tab.getDistance() + " m");
                } else if (tab.getDistance() != null && !"".equals(tab.getDistance())) {
                    double finalValue = Math.round(Double.parseDouble(tab.getDistance()) / 1000 * 10.0) / 10.0;
                    getBinding().tvDistance.setText(tab.getDistance_text() + " " + finalValue + " km");
                }
            } else {
                getBinding().tvDistance.setText("Không xác định");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getPercent(String star) {
        float aFloat = Float.parseFloat(star);
        int percent = (int) (aFloat / Float.parseFloat(detailSmallLocationResponse.getData().getTabs().get(0).getComment_count()) * 100);
        return percent;
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String name = "";
                    try {
                        name = detailSmallLocationResponse.getData().getTabs().get(0).getName();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    addFragment(new WriteReviewFragment(detailSmallLocationResponse.getData().getId(), detailSmallLocationResponse.getData().getContent_type(), name));
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
        if (observable instanceof ReviewViewModel && null != o) {
            if (o instanceof GetReviewResponse) {
                GetReviewResponse response = (GetReviewResponse) o;
                contentList.clear();
                contentList.addAll(response.getData().getContent());
                ratingAdapter.notifyDataSetChanged();
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

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
    public void onReloadReview(OnReviewSuccess onReviewSuccess) {
        viewModel.getReview(detailSmallLocationResponse.getData().getId(), null);
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.SMALL_LOCATION_REVIEW, TrackingAnalytic.ScreenTitle.SMALL_LOCATION_REVIEW);
    }

    @Override
    public void onClickItem(Travel travel) {

    }

    @Override
    public void likeEvent(int position) {
        try {
            Account account = MyApplication.getInstance().getAccount();
            GetReviewResponse.Data.Content travel = contentList.get(position);
            if (null != account && account.isLogin()) {
                viewModel.likeEvent(travel.getId(), "comments");
                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.TOP_EXPERIENCE, TrackingAnalytic.ScreenTitle.TOP_EXPERIENCE)
                            .setContent_type("comments")
                            .setContent_id(travel.getId())
                            .setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (travel.isLiked()) {
                    travel.setLiked(false);
                    if (null != travel.getLikeCount()) {
                        String likeCount = (Integer.parseInt(travel.getLikeCount()) - 1) + "";
                        travel.setLikeCount(likeCount);
                    }
                } else {
                    travel.setLiked(true);
                    if (null != travel.getLikeCount()) {
                        String likeCount = (Integer.parseInt(travel.getLikeCount()) + 1) + "";
                        travel.setLikeCount(likeCount);
                    }
                }
                ratingAdapter.notifyItemChanged(position);
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
