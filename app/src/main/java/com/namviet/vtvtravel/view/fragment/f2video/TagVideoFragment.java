package com.namviet.vtvtravel.view.fragment.f2video;

import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2video.SubVideoAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentTagVideoBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.f2video.DetailVideoResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.f2video.SubVideoViewModel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TagVideoFragment extends BaseFragment<F2FragmentTagVideoBinding> implements Observer {
    private SubVideoViewModel subVideoViewModel;
    private SubVideoAdapter subVideoAdapter;
    private String tag;

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_tag_video;
    }

    @Override
    public void initView() {
        subVideoViewModel = new SubVideoViewModel();
        getBinding().setSubVideoViewModel(subVideoViewModel);
        subVideoViewModel.addObserver(this);
        subVideoViewModel.getVideoByTag(tag);

        getBinding().tvTitle.setText("#" + tag);
    }

    @Override
    public void initData() {

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
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof SubVideoViewModel && null != o) {
            if (o instanceof DetailVideoResponse) {
                DetailVideoResponse response = (DetailVideoResponse) o;
                List<Video> videos = response.getData().getItems();

                subVideoAdapter = new SubVideoAdapter(mActivity, videos, new SubVideoAdapter.ClickItem() {
                    @Override
                    public void onClickItem(Video video) {

                    }

                    @Override
                    public void likeEvent(int position) {
                        try {
                            Account account = MyApplication.getInstance().getAccount();
                            Video video = videos.get(position);
                            if (null != account && account.isLogin()) {
                                subVideoViewModel.likeEvent(video.getId(), video.getContent_type());

                                try {
                                    TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.TAG_VIDEO, TrackingAnalytic.ScreenTitle.TAG_VIDEO)
                                            .setContent_type(video.getContent_type())
                                            .setContent_id(video.getId())
                                            .setScreen_class(this.getClass().getName()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (video.isLiked()) {
                                    video.setLiked(false);
                                    if (null != video.getLikeCount()) {
                                        String likeCount = (Integer.parseInt(video.getLikeCount()) - 1) + "";
                                        video.setLikeCount(likeCount);
                                    }
                                } else {
                                    video.setLiked(true);
                                    if (null != video.getLikeCount()) {
                                        String likeCount = (Integer.parseInt(video.getLikeCount()) + 1) + "";
                                        video.setLikeCount(likeCount);
                                    }
                                }
                                subVideoAdapter.notifyItemChanged(position);
                            } else {
                                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                getBinding().recycleContent.setAdapter(subVideoAdapter);

            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((BaseActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.TAG_VIDEO, TrackingAnalytic.ScreenTitle.TAG_VIDEO);
    }

}
