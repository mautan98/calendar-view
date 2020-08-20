package com.namviet.vtvtravel.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.PhotoNiceAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentPhotoNiceTabBinding;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.response.CategoryItem;
import com.namviet.vtvtravel.response.NewsResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.NewsViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TabPhotoNiceFragment extends MainFragment implements Observer, NewsSelectListener {
    private FragmentPhotoNiceTabBinding binding;
    private PhotoNiceAdapter photoGalleryAdapter;
    private CategoryItem mCategoryItem;
    private NewsViewModel newsViewModel;
    private List<News> newsList = new ArrayList<>();
    private String moreLink;
    private EndlessRecyclerViewScrollListener scrollListener;

    public static TabPhotoNiceFragment newInstance(CategoryItem categoryItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, categoryItem);
        TabPhotoNiceFragment tabPhotoNiceFragment = new TabPhotoNiceFragment();
        tabPhotoNiceFragment.setArguments(bundle);
        return tabPhotoNiceFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mCategoryItem = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_nice_tab, container, false);
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                newsViewModel.loadListPhotoNice(mCategoryItem.getLink());

            }
        }, Constants.TimeDelay);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rvPhotoNice.setLayoutManager(gridLayoutManager);
        photoGalleryAdapter = new PhotoNiceAdapter(getContext(), newsList);
        photoGalleryAdapter.setNewsSelectListener(this);
        binding.rvPhotoNice.setAdapter(photoGalleryAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (null != moreLink) {
                    newsViewModel.loadListPhotoNice(moreLink);
                }

            }
        };
        binding.rvPhotoNice.addOnScrollListener(scrollListener);
    }

    @Override
    public void update(Observable observable, final Object o) {
        if (observable instanceof NewsViewModel) {
            if (o != null) {
                if (o instanceof NewsResponse) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NewsResponse response = (NewsResponse) o;
                            moreLink = response.getData().getMore_link();
                            newsList.addAll(response.getData().getItems());
                            photoGalleryAdapter.notifyDataSetChanged();
                        }
                    });
                }  else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                }
            } else {
                moreLink = null;
            }
        }
    }

    @Override
    public void onSelectNews(News news) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
        mActivity.setBundle(bundle);
        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
    }
}
