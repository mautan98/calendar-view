package com.namviet.vtvtravel.view.fragment.home;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.CategoryVideoPageAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentListVideoBinding;
import com.namviet.vtvtravel.response.CategoryItem;
import com.namviet.vtvtravel.response.CategoryPhotoResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.VideoViewModel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ListVideoFragment extends MainFragment implements Observer {

    private FragmentListVideoBinding binding;
    private CategoryVideoPageAdapter pagerAdapter;
    private VideoViewModel videoViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_video, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar);
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
        setTvCountUnread(binding.tvNotification);
        binding.ivMenu.setOnClickListener(this);
        binding.llNotify.setOnClickListener(this);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        showDialogLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                videoViewModel.loadCategoryVideo();
            }
        }, Constants.TimeDelay);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivMenu) {
            mActivity.openAndCloseDrawer();
        } else if (view == binding.llNotify) {
            goToNotifyScreen();
        }

    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof VideoViewModel) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (o != null) {
                        if (o instanceof CategoryPhotoResponse) {
                            CategoryPhotoResponse itemCategoryData = (CategoryPhotoResponse) o;
                            setUITab(itemCategoryData.getData());
                        } else if (o instanceof ResponseError) {
                            ResponseError responseError = (ResponseError) o;
                            showMessage(responseError.getMessage());
                        }
                    } else {

                    }
                }
            });

        }
    }

    private void setUITab(List<CategoryItem> list) {
        pagerAdapter = new CategoryVideoPageAdapter(mActivity.getSupportFragmentManager(), mActivity, list);
        binding.vpVideo.setAdapter(pagerAdapter);

        binding.tabVideo.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        binding.tabVideo.setViewPager(binding.vpVideo);
        binding.tabVideo.setCurrentTab(0);
        binding.vpVideo.setOffscreenPageLimit(list.size());

    }


}
