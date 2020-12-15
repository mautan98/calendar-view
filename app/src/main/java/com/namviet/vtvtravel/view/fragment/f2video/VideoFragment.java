package com.namviet.vtvtravel.view.fragment.f2video;

import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentVideoBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2video.VideoResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.f2oldbase.SearchActivity;
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
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.VIDEOS, TrackingAnalytic.ScreenTitle.VIDEOS).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.startScreen(mActivity);
            }
        });

    }

    private VTVTabStyleAdapter mainAdapter;
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
                mainAdapter = new VTVTabStyleAdapter(getChildFragmentManager());
                for (int i = 0; i < videoResponse.getData().size(); i++) {
                    SubVideoFragment subVideoFragment = new SubVideoFragment();
                    subVideoFragment.setContentLink(videoResponse.getData().get(i).getLink());
                    mainAdapter.addFragment(subVideoFragment, "");
                }

                binding.vpContent.setAdapter(mainAdapter);
                binding.tabLayout.setupWithViewPager(binding.vpContent);
                for (int i = 0; i < videoResponse.getData().size(); i++) {
                    View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style, null);
                    TextView tvHome = tabHome.findViewById(R.id.tvTitle);
                    tvHome.setText((videoResponse.getData().get(i).getName()));
                    if (i == 0) {
                        tvHome.setTextColor(Color.parseColor("#00918D"));
                    } else {
                        tvHome.setTextColor(Color.parseColor("#101010"));
                    }
                    View view = tabHome.findViewById(R.id.indicator);
                    if (i == 0) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.INVISIBLE);
                    }
                    binding.tabLayout.getTabAt(i).setCustomView(tabHome);
                }
                binding.tabLayout.addOnTabSelectedListener(OnTabSelectedListener);
            }

        } else if (o instanceof ErrorResponse) {
            ErrorResponse responseError = (ErrorResponse) o;
            try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
            } catch (Exception e) {

            }
        }

    }

    private TabLayout.OnTabSelectedListener OnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            mainAdapter.SetOnSelectView(binding.tabLayout, c);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            mainAdapter.SetUnSelectView(binding.tabLayout, c);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


}
