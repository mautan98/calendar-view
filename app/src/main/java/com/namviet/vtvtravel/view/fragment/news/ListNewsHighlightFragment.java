package com.namviet.vtvtravel.view.fragment.news;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.L;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ListNewsHighlightAdapter;
import com.namviet.vtvtravel.adapter.ListNewsHighlightAdapterMenu;
import com.namviet.vtvtravel.adapter.SlidePhotoAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentListNewsHighlightBinding;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.model.Banner;
import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.response.BannerResponse;
import com.namviet.vtvtravel.response.NewsResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.SlideShowResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.NewsViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ListNewsHighlightFragment extends MainFragment implements Observer, NewsSelectListener {
    private FragmentListNewsHighlightBinding binding;
    private ListNewsHighlightAdapter listNewsHighlightAdapter;
    private ListNewsHighlightAdapterMenu listNewsHighlightAdapterMenu;
    private NewsViewModel newsViewModel;
    private ItemMenu itemMenu;
    private List<News> newsList = new ArrayList<>();
    private String moreLink;
    private EndlessRecyclerViewScrollListener scrollListener;
    private SlidePhotoAdapter slidePhotoAdapter;
    private List<Banner> bannerList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            itemMenu = getArguments().getParcelable(Constants.IntentKey.KEY_ITEM_MENU);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_news_highlight, container, false);
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
        if (null != itemMenu) {
            binding.tvTitle.setText(itemMenu.getName());
        }
        binding.ivBack.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvListNews.setLayoutManager(layoutManager);
        if (itemMenu != null && itemMenu.getCode().equals(Constants.TypeLeftMenu.NOTEBOOK_NEWS)) {
            listNewsHighlightAdapter = new ListNewsHighlightAdapter(getContext(), newsList, true);
            listNewsHighlightAdapterMenu = new ListNewsHighlightAdapterMenu(getContext(), newsList);
        } else {
            listNewsHighlightAdapterMenu = new ListNewsHighlightAdapterMenu(getContext(), newsList);
//            listNewsHighlightAdapter = new ListNewsHighlightAdapter(getContext(), newsList);
        }
        binding.rvListNews.setAdapter(listNewsHighlightAdapterMenu);
        listNewsHighlightAdapterMenu.setNewsSelectListener(this);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (null != moreLink) {
                    newsViewModel.getCategoryNews(itemMenu.getCode(), page);
                }

            }
        };
        binding.rvListNews.addOnScrollListener(scrollListener);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        if (null != itemMenu) {
            showDialogLoading();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    newsViewModel.getCategoryNews(itemMenu.getCode(), 1);
                    newsViewModel.getSlideShowNews(itemMenu.getCode());
                    newsViewModel.getBannerList(itemMenu.getCode());

                }
            }, Constants.TimeDelay);

        }

    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof NewsViewModel) {
            if (o != null) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (o instanceof NewsResponse) {
                            NewsResponse response = (NewsResponse) o;
                            moreLink = response.getData().getMore_link();
                            newsList.addAll(response.getData().getItems());
                            if (null != bannerList) {
                                for (Banner banner : bannerList) {
                                    if (banner.getPosition_index() < newsList.size() && !newsList.get(banner.getPosition_index()).isBanner()) {
                                        newsList.add(banner.getPosition_index(), new News(banner.getEmbed_link(), true));
                                    }
                                }
                            }
                            listNewsHighlightAdapterMenu.notifyDataSetChanged();

                        } else if (o instanceof SlideShowResponse) {
                            SlideShowResponse slideShows = (SlideShowResponse) o;
                            slidePhotoAdapter = new SlidePhotoAdapter(getContext(), slideShows.getData());
                            binding.vpSlideShow.setAdapter(slidePhotoAdapter);
                            binding.vpIndicator.attachToViewPager(binding.vpSlideShow);
                            slidePhotoAdapter.setNewsSelectListener(ListNewsHighlightFragment.this);
                        } else if (o instanceof BannerResponse) {
                            BannerResponse response = (BannerResponse) o;
                            L.e("response banner" + response.toString());
                            bannerList = response.getData();
                        }  else if (o instanceof ResponseError) {
                            ResponseError responseError = (ResponseError) o;
                            showMessage(responseError.getMessage());
                        }
                    }
                });
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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivBack) {
            mActivity.onBackPressed();
        }
    }
}
