package com.namviet.vtvtravel.view.fragment.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.SlideMomentAdapter;
import com.namviet.vtvtravel.adapter.VideoMomentAdapter;
import com.namviet.vtvtravel.adapter.YourMomentAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.response.NewestResponse;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.response.SlideShowResponse;
import com.namviet.vtvtravel.response.VideoMomentResponse;
import com.namviet.vtvtravel.videomanage.ui.VideoPlayerView;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.databinding.FragmentYourMomentBinding;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.widget.CustPagerTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class YourMomentFragment extends MainFragment implements Observer, TravelSelectListener {
    private FragmentYourMomentBinding binding;
    private YourMomentAdapter yourMomentAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlaceViewModel placeViewModel;
    private List<Travel> travelList = new ArrayList<>();
    private List<Travel> getAllTravel = new ArrayList<>();
    private SlideMomentAdapter slideMomentAdapter;
    private VideoMomentAdapter videoMomentAdapter;
    private boolean loadMore;
    private int page = 1;
    private int REQUEST_PERMISTION_STORAGE = 10;
    private boolean isFirst = true;
    private final int LIMIT = 6;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_your_moment, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.myToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
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
        placeViewModel = new PlaceViewModel(getContext());
        binding.setPlaceViewModel(placeViewModel);
        placeViewModel.addObserver(this);
        setTvCountUnread(binding.tvNotification);
        binding.ivMenu.setOnClickListener(this);
        binding.tvAddMoment.setOnClickListener(this);
        binding.ivMenu.setOnClickListener(this);
        binding.btMore.setOnClickListener(this);
        binding.llNotify.setOnClickListener(this);
        mLayoutManager = new GridLayoutManager(mActivity, 2);
        binding.rvYourMoment.setLayoutManager(mLayoutManager);
        binding.rvYourMoment.setNestedScrollingEnabled(false);

        yourMomentAdapter = new YourMomentAdapter(getContext(), travelList);
        binding.rvYourMoment.setAdapter(yourMomentAdapter);
        yourMomentAdapter.setTravelSelectListener(this);

        binding.rvVideo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        binding.vpSlideShow.setPageTransformer(false, new CustPagerTransformer(getContext()));

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
                placeViewModel.loadMoment(1, LIMIT);
                placeViewModel.loadMomentNewest();
            }
        }, Constants.TimeDelay);


    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof PlaceViewModel) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (o != null) {
                        if (o instanceof PlaceResponse) {
                            PlaceResponse itemCategoryData = (PlaceResponse) o;
                            loadMore = itemCategoryData.getData().isHasMore();
                            getAllTravel = itemCategoryData.getData().getItems();
                            int sizeList = getAllTravel.size();
                            if (isFirst) {
                                for (int i = 0; i < sizeList; i++) {
                                    if (i < LIMIT)
                                        travelList.add(getAllTravel.get(i));
                                }
                                isFirst = false;
                            } else {
                                travelList.addAll(getAllTravel);
                            }

                            yourMomentAdapter.notifyDataSetChanged();
                            if (loadMore) {
                                page++;
                                binding.btMore.setVisibility(View.VISIBLE);
                            } else {
                                binding.btMore.setVisibility(View.GONE);
                            }
                        } else if (o instanceof NewestResponse) {
                            NewestResponse slideShows = (NewestResponse) o;
                            slideMomentAdapter = new SlideMomentAdapter(mActivity.getSupportFragmentManager(), slideShows.getData());
                            binding.vpSlideShow.setAdapter(slideMomentAdapter);
                            placeViewModel.loadVideoMoment();
                        } else if (o instanceof VideoMomentResponse) {
                            VideoMomentResponse slideShows = (VideoMomentResponse) o;
                            videoMomentAdapter = new VideoMomentAdapter(getContext(), slideShows.getData());
                            binding.rvVideo.setAdapter(videoMomentAdapter);
                            binding.vpIndicator.attachToRecyclerView(binding.rvVideo);
                            videoMomentAdapter.setTravelSelectListener(YourMomentFragment.this);
                        }
                    } else {

                    }
                }
            });

        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivMenu) {
            mActivity.openAndCloseDrawer();
        } else if (view == binding.tvAddMoment) {
            Account acc = MyApplication.getInstance().getAccount();
            if (acc != null && acc.isLogin()) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISTION_STORAGE);
                } else {
                    mActivity.switchFragment(SlideMenu.MenuType.SHARE_MOMENT_SCREEN);
                }

            } else {
                mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
            }
        } else if (view == binding.ivMenu) {
            mActivity.openAndCloseDrawer();
        } else if (view == binding.btMore) {
            if (loadMore) {
                showDialogLoading();
                placeViewModel.loadMoment(page, LIMIT);
            }
        } else if (view == binding.llNotify) {
            goToNotifyScreen();
        }
    }

    @Override
    public void onSelectTravel(Travel travel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, travel);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_MOMENT_SCREEN);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISTION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mActivity.switchFragment(SlideMenu.MenuType.SHARE_MOMENT_SCREEN);
            }
        }

    }

    @Override
    protected void setUnreadTv(TextView tvCount, int count) {
        super.setUnreadTv(tvCount, count);
    }
}
