package com.namviet.vtvtravel.view.fragment.f2video;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2video.SubVideoAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentHighLightSeeMoreVideoBinding;
import com.namviet.vtvtravel.databinding.F2FragmentTagVideoBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.f2video.DetailVideoResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.viewmodel.f2video.SubVideoViewModel;
import com.namviet.vtvtravel.viewmodel.f2video.VideoViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class HighLightSeeMoreVideoFragment extends BaseFragment<F2FragmentHighLightSeeMoreVideoBinding> implements Observer {
    private SubVideoViewModel subVideoViewModel;
    private SubVideoAdapter subVideoAdapter;
    private String regionName;
    private String regionId;
    private String detailLink;
    private List<Video> videos = new ArrayList<>();
    private String loadMoreLink;
    @SuppressLint("ValidFragment")
    public HighLightSeeMoreVideoFragment(String regionName, String regionId,  String detailLink) {
        this.regionId = regionId;
        this.detailLink = detailLink;
        this.regionName = regionName;
    }

    public HighLightSeeMoreVideoFragment() {
    }

    public void setTag(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_high_light_see_more_video;
    }

    @Override
    public void initView() {
        subVideoViewModel = new SubVideoViewModel();
        getBinding().setSubVideoViewModel(subVideoViewModel);
        subVideoViewModel.addObserver(this);
        subVideoViewModel.getDetailVideo(detailLink+"?region_id="+regionId, false);
        getBinding().tvTitle.setText(regionName);
    }

    @Override
    public void initData() {
        subVideoAdapter = new SubVideoAdapter(mActivity, videos, new SubVideoAdapter.ClickItem() {
            @Override
            public void onClickItem(Video video) {

            }
        });
        getBinding().recycleContent.setAdapter(subVideoAdapter);



        getBinding().recycleContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    subVideoViewModel.getDetailVideo(loadMoreLink, true);
                    loadMoreLink = "";
                }
            }
        });

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
        if (observable instanceof SubVideoViewModel && null != o) {
            if (o instanceof DetailVideoResponse) {
                DetailVideoResponse response = (DetailVideoResponse) o;
                loadMoreLink = response.getData().getMore_link();
                if(response.isLoadMore()){
                    videos.addAll(response.getData().getItems());
                }else {
                    videos.clear();
                    videos.addAll(response.getData().getItems());
                }
                subVideoAdapter.notifyDataSetChanged();

            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((BaseActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

}
