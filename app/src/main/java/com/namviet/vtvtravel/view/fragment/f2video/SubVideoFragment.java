package com.namviet.vtvtravel.view.fragment.f2video;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.TagVideoAdapter;
import com.namviet.vtvtravel.adapter.f2video.SubVideoAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentSubVideoBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.f2video.DetailVideoResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.viewmodel.f2video.SubVideoViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SubVideoFragment extends BaseFragment<F2FragmentSubVideoBinding> implements Observer{
    private SubVideoViewModel subVideoViewModel;
    private SubVideoAdapter subVideoAdapter;
    private String contentLink;
    private int position;
    private List<Video> videos = new ArrayList<>();
    private String loadMoreLink;


    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_sub_video;
    }

    @Override
    public void initView() {
        getBinding().rclContent.setHasFixedSize(true);
        subVideoViewModel = new SubVideoViewModel();
        getBinding().setSubVideoViewModel(subVideoViewModel);
        subVideoViewModel.addObserver(this);
        subVideoViewModel.getDetailVideo(contentLink, false);
    }

    @Override
    public void initData() {
        subVideoAdapter = new SubVideoAdapter(context, videos, new SubVideoAdapter.ClickItem() {
            @Override
            public void onClickItem(Video video) {

            }
        });
        getBinding().rclContent.setAdapter(subVideoAdapter);



        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context  = context;

    }
    private Context context;

    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
