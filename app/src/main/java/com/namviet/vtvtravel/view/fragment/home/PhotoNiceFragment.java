package com.namviet.vtvtravel.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.PhotoNicePageAdapter;
import com.namviet.vtvtravel.adapter.SlidePhotoAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentPhotoNiceBinding;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.response.CategoryItem;
import com.namviet.vtvtravel.response.CategoryPhotoResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.SlideShowResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.NewsViewModel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class PhotoNiceFragment extends MainFragment implements Observer, NewsSelectListener {
    private FragmentPhotoNiceBinding binding;
    private PhotoNicePageAdapter photoNicePageAdapter;
    private NewsViewModel newsViewModel;
    private SlidePhotoAdapter slidePhotoAdapter;

    private Handler mHandler = new Handler();
    private int mCurrentPage = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_nice, container, false);
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
        newsViewModel = new NewsViewModel(getContext());
        binding.setNewsViewModel(newsViewModel);
        newsViewModel.addObserver(this);
        binding.tvTitle.setText(getString(R.string.photo_nice));
        binding.ivBack.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);

        updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivBack) {
            mActivity.onBackPressed();
        }
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        showDialogLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                newsViewModel.loadCategoryPhotoNice();
                newsViewModel.loadSlideShow();
            }
        }, Constants.TimeDelay);


    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof NewsViewModel) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (o != null) {
                        if (o instanceof CategoryPhotoResponse) {
                            CategoryPhotoResponse itemCategoryData = (CategoryPhotoResponse) o;
                            setUITab(itemCategoryData.getData());

                        } else if (o instanceof SlideShowResponse) {
                            SlideShowResponse slideShows = (SlideShowResponse) o;
                            slidePhotoAdapter = new SlidePhotoAdapter(getContext(), slideShows.getData());
                            binding.vpSlideShow.setAdapter(slidePhotoAdapter);
                            binding.vpIndicator.attachToViewPager(binding.vpSlideShow);
                            slidePhotoAdapter.setNewsSelectListener(PhotoNiceFragment.this);

                            autoNextViewPager(slideShows.getData().size());

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

    private void autoNextViewPager(int size) {
        Runnable update = () -> {
            if (mCurrentPage == size - 1) {
                mCurrentPage = 0;
            }
            binding.vpSlideShow.setCurrentItem(mCurrentPage++, true);
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(update);
            }
        }, 100, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setUITab(List<CategoryItem> list) {
        photoNicePageAdapter = new PhotoNicePageAdapter(getChildFragmentManager(), getContext(), list);
        binding.vpMoment.setAdapter(photoNicePageAdapter);
        binding.tabLayout.setupWithViewPager(binding.vpMoment);

    }

    @Override
    public void onSelectNews(News news) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
    }
}
