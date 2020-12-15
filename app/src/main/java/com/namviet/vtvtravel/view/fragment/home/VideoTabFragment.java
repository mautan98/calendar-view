package com.namviet.vtvtravel.view.fragment.home;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ListVideoAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentTabCateVideoBinding;
import com.namviet.vtvtravel.listener.VideoSelectListener;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.CategoryItem;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.VideoResponse;
import com.namviet.vtvtravel.view.PlayListVideoActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.VideoViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class VideoTabFragment extends MainFragment implements Observer, VideoSelectListener {
    private FragmentTabCateVideoBinding binding;
    private ListVideoAdapter listVideoAdapter;
    private CategoryItem categoryItem;
    private VideoViewModel videoViewModel;
    private ArrayList<Video> videoList = new ArrayList<>();
    private String moreLink;
    private EndlessRecyclerViewScrollListener scrollListener;

    public static VideoTabFragment newInstance(CategoryItem item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, item);
        VideoTabFragment fragment = new VideoTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            categoryItem = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_cate_video, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        videoViewModel = new VideoViewModel();
        binding.setVideoViewModel(videoViewModel);
        videoViewModel.addObserver(this);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, Constants.TimeDelay);
        binding.prLoading.setVisibility(View.VISIBLE);
        videoViewModel.loadListVideo(categoryItem.getLink());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvListVideo.setLayoutManager(layoutManager);
        listVideoAdapter = new ListVideoAdapter(getContext(), videoList);
        listVideoAdapter.setVideoSelectListener(this);
        binding.rvListVideo.setAdapter(listVideoAdapter);
        listVideoAdapter.notifyDataSetChanged();
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (null != moreLink) {
                    videoViewModel.loadListVideo(moreLink);
                }

            }
        };
        binding.rvListVideo.addOnScrollListener(scrollListener);
    }

    @Override
    public void update(Observable observable, final Object o) {
        binding.prLoading.setVisibility(View.GONE);
        if (observable instanceof VideoViewModel) {
            if (o != null) {
                if (o instanceof VideoResponse) {
                    VideoResponse videoResponse = (VideoResponse) o;
                    moreLink = videoResponse.getData().getMore_link();
                    videoList.addAll(videoResponse.getData().getItems());
                    listVideoAdapter.notifyDataSetChanged();
//                    binding.rvListVideo.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//
//                        }
//                    }, 100);
                } else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                }
            } else {
                moreLink = null;
            }

        }
    }

    @Override
    public void onSelectVideo(Video video, int position, View view) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, video);
//        mActivity.setBundle(bundle);
//        mActivity.switchFragment(SlideMenu.MenuType.PLAYLIST_VIDEO_SCREEN);

        int count = videoList.get(position).getView_count();
        videoList.get(position).setView_count(count + 1);
        listVideoAdapter.notifyDataSetChanged();

        Intent intent = new Intent(getActivity(), PlayListVideoActivity.class);
        intent.putExtra(Constants.IntentKey.KEY_ACTIVITY, video);
        startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }
}
