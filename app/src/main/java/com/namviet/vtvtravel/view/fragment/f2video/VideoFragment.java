package com.namviet.vtvtravel.view.fragment.f2video;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentVideoBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2video.VideoResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.f2video.VideoViewModel;

import java.util.Observable;
import java.util.Observer;

public class VideoFragment extends MainFragment implements Observer {
    private F2FragmentVideoBinding binding;
    private VideoViewModel viewModel;
    private VideoResponse videoResponse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_video, container, false);
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
        binding.vpContent.setOffscreenPageLimit(10);
        viewModel = new VideoViewModel();
        binding.setVideoViewModel(viewModel);
        viewModel.addObserver(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.getCategoryVideo();
            }
        }, 500);

    }

    @Override
    public void update(Observable observable, Object o) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    binding.shimmerViewContainer.stopShimmer();
                    binding.shimmerViewContainer.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);

        if (observable instanceof VideoViewModel && null != o) {
            if (o instanceof VideoResponse) {
                videoResponse = (VideoResponse) o;
                MainAdapter mainAdapter = new MainAdapter(getChildFragmentManager());
                for (int i = 0; i < videoResponse.getData().size(); i++) {
                    SubVideoFragment subVideoFragment = new SubVideoFragment();
                    subVideoFragment.setContentLink(videoResponse.getData().get(i).getLink());
                    mainAdapter.addFragment(subVideoFragment, "");
                }

                binding.vpContent.setAdapter(mainAdapter);
                binding.tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package));
                binding.tabLayout.setupWithViewPager(binding.vpContent);
                for (int i = 0; i < videoResponse.getData().size(); i++) {
                    binding.tabLayout.getTabAt(i).setText(videoResponse.getData().get(i).getName());
                }
            }

        } else if (o instanceof ErrorResponse) {
            ErrorResponse responseError = (ErrorResponse) o;
            try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
            } catch (Exception e) {

            }
        }

    }

}
