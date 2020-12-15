package com.namviet.vtvtravel.view.fragment.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.L;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.BannerAdapter;
import com.namviet.vtvtravel.adapter.CommentAdapter;
import com.namviet.vtvtravel.adapter.ListNewsHighlightAdapter;
import com.namviet.vtvtravel.adapter.RelationNewsAdapterMenu;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentDetailNewsHighlightBinding;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.listener.PostCommentListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Banner;
import com.namviet.vtvtravel.model.Comment;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.response.BannerResponse;
import com.namviet.vtvtravel.response.CommentResponse;
import com.namviet.vtvtravel.response.DetailNewsData;
import com.namviet.vtvtravel.response.DetailNewsResponse;
import com.namviet.vtvtravel.response.PostCommentResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.dialog.CommentDialogFragment;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.WebviewFragment;
import com.namviet.vtvtravel.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DetailNewsHighlightFragment extends MainFragment implements Observer, NewsSelectListener, PostCommentListener {

    private FragmentDetailNewsHighlightBinding binding;
    private NewsViewModel newsViewModel;
    private News mNews;
    private ListNewsHighlightAdapter listNewsHighlightAdapter;
    private RelationNewsAdapterMenu relationNewsAdapterMenu;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();
    private int countComment = 0;
    private int totalPage = 0;
    private int page = 0;
    private List<Banner> bannerTop = new ArrayList<>();
    private List<Banner> bannerBottom = new ArrayList<>();
    private BannerAdapter bannerTopAdapter;
    private BannerAdapter bannerBottomAdapter;
    private final String TYPESCREEN = "Cơ hội du lịch";
    private final String URL_API = "https://api.travel.onex.vn/pages/5d48fcd8190d81ad1c8b456c";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mNews = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
            Log.d("HungDong: ", mNews.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_news_highlight, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar.myToolbar);
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
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.toolBar.ivSearch.setOnClickListener(this);
        newsViewModel = new NewsViewModel(getContext());
        binding.setNewsViewModel(newsViewModel);
        newsViewModel.addObserver(this);

        binding.rvBannerTop.setNestedScrollingEnabled(false);
        binding.rvBannerTop.setLayoutManager(new LinearLayoutManager(getContext()));
        bannerTopAdapter = new BannerAdapter(getContext(), bannerTop);
        binding.rvBannerTop.setAdapter(bannerTopAdapter);

        binding.rvBannerBottom.setNestedScrollingEnabled(false);
        binding.rvBannerBottom.setLayoutManager(new LinearLayoutManager(getContext()));
        bannerBottomAdapter = new BannerAdapter(getContext(), bannerBottom);
        binding.rvBannerBottom.setAdapter(bannerBottomAdapter);

        binding.rvComment.setNestedScrollingEnabled(false);
        binding.rvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        commentAdapter = new CommentAdapter(mActivity, commentList, this);
        binding.rvComment.setAdapter(commentAdapter);


        binding.rvNewsSameCategory.setNestedScrollingEnabled(false);
        binding.rvNewsSameCategory.setLayoutManager(new GridLayoutManager(getContext(), 2));

        binding.rvNewsRelated.setNestedScrollingEnabled(false);
        binding.rvNewsRelated.setLayoutManager(new GridLayoutManager(getContext(), 2));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.KeyBroadcast.KEY_LOGIN_SCREEN);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, intentFilter);
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
                if (null != mNews) {
                    newsViewModel.getNewsById(mNews.getDetail_link());
                }
            }
        }, Constants.TimeDelay);
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            setImageUrl(account.getImageProfile(), binding.avatar);
        }
    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof NewsViewModel) {
            if (o != null) {
                if (o instanceof DetailNewsResponse) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DetailNewsResponse response = (DetailNewsResponse) o;
                            updateUI(response.getData());
                        }
                    });
                    newsViewModel.getBannerDetail(mNews.getDetail_link());
                } else if (o instanceof CommentResponse) {
                    CommentResponse commentResponse = (CommentResponse) o;
                    totalPage = commentResponse.getData().getTotalPages();
                    countComment = commentResponse.getData().getTotalElements();
                    binding.tvTitleComment.setText(getText(R.string.comment) + " (" + countComment + ")");
                    commentList.clear();
                    commentList.addAll(commentResponse.getData().getContent());
                    commentAdapter.notifyDataSetChanged();
                } else if (o instanceof PostCommentResponse) {
                    countComment++;
                    binding.tvTitleComment.setText(getText(R.string.comment) + " (" + countComment + ")");
                    PostCommentResponse response = (PostCommentResponse) o;
                    Account account = MyApplication.getInstance().getAccount();
                    Comment comment = response.getData();
                    comment.setUser(account);
                    // commentList.add(0, comment);
                    commentAdapter.notifyDataSetChanged();

                } else if (o instanceof BannerResponse) {
                    BannerResponse response = (BannerResponse) o;
                    L.e("response banner" + response.toString());
                    List<Banner> bannerList = response.getData();
                    bannerTop.clear();
                    bannerBottom.clear();
                    for (Banner banner : bannerList) {
                        if (banner.getPosition_code().equals("TOP")) {
                            bannerTop.add(banner);
                        } else {
                            bannerBottom.add(banner);
                        }
                    }
                    bannerTopAdapter.notifyDataSetChanged();
                    bannerBottomAdapter.notifyDataSetChanged();
                }  else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                }
            } else {
            }
        }
    }

    private void updateUI(DetailNewsData data) {
        binding.toolBar.tvTitle.setText(data.getTitle());
        binding.tvTitleNews.setText(data.getName());
        binding.tvTime.setText(DateUtltils.timeToString(data.getCreated()));
        binding.tvView.setText("" + data.getView_count());
        if (TYPESCREEN.equals(data.getTitle())) {
            binding.webContent.loadData(data.getDescription(), "text/html; charset=utf-8", "UTF-8");
        } else if (null != data.getDescription() && null != data.getFooter().getDescription()) {
            // binding.webContent.loadData(data.getDescription() + data.getFooter().getDescription(),
            //         "text/html; charset=utf-8", "UTF-8");
            binding.webContent.loadDataWithBaseURL(null,
                    data.getDescription() + data.getFooter().getDescription(),
                    "text/html; charset=utf-8", "UTF-8", null);
            binding.webContent.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith(data.getFooter().getUrl())) {
                        Bundle bundle = new Bundle();
                        bundle.putString(WebviewFragment.URL_WEBVIEW, data.getFooter().getUrl_api());
//                        bundle.putString(WebviewFragment.URL_WEBVIEW, URL_API);
                        mActivity.setBundle(bundle);
                        mActivity.switchFragment(SlideMenu.MenuType.WEBVIEW_SCREEN);
                        return true;
                    }
                    return false;
                }
            });
        }
        if (data.getNeighbours() != null) {
            listNewsHighlightAdapter = new ListNewsHighlightAdapter(getContext(), data.getNeighbours());
            binding.rvNewsSameCategory.setAdapter(listNewsHighlightAdapter);
            listNewsHighlightAdapter.setNewsSelectListener(this);
        } else {
            binding.tvTitleNewsSameCategory.setVisibility(View.GONE);
        }

        if (data.getRelations() != null) {
            relationNewsAdapterMenu = new RelationNewsAdapterMenu(getContext(), data.getRelations());
            binding.rvNewsRelated.setAdapter(relationNewsAdapterMenu);
            relationNewsAdapterMenu.setNewsSelectListener(this);
        } else {
            binding.tvTitleNewsRelated.setVisibility(View.GONE);
        }
        newsViewModel.loadComment(mNews.getId(), page);

    }

    @Override
    public void onSelectNews(News news) {
        showDialogLoading();
        newsViewModel.getNewsById(news.getDetail_link());
        binding.nesScroll.scrollTo(0, 0);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.tvComment) {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                CommentDialogFragment commentDialogFragment = CommentDialogFragment.newInstance();
                commentDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                commentDialogFragment.setPostCommentListener(this);
            } else {
                mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
            }
        }
    }

    @Override
    public void onPostComment(String comment, Integer parentId) {
        Account account = MyApplication.getInstance().getAccount();
        newsViewModel.postComment(account.getId(), comment, mNews.getId(), mNews.getContent_type(), parentId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.KeyBroadcast.KEY_LOGIN_SCREEN)) {
                Account account = MyApplication.getInstance().getAccount();
                setImageUrl(account.getImageProfile(), binding.avatar);
            }
        }

    };
}
