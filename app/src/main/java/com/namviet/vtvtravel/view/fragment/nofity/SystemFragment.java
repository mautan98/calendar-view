package com.namviet.vtvtravel.view.fragment.nofity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ListNotifyAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentNotifySystemBinding;
import com.namviet.vtvtravel.listener.SearchSelectListener;
import com.namviet.vtvtravel.model.ItemNotify;
import com.namviet.vtvtravel.model.NearItem;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.response.NotifyResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.NotifyViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SystemFragment extends MainFragment implements Observer, SearchSelectListener {
    private NearItem mNearItem;
    private NotifyViewModel notifyViewModel;
    private List<ItemNotify> notifyList = new ArrayList<>();
    private ListNotifyAdapter listNotifyAdapter;
    private String moreLink;
    private EndlessRecyclerViewScrollListener scrollListener;

    public static SystemFragment newInstance(NearItem nearItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, nearItem);
        SystemFragment fragment = new SystemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private FragmentNotifySystemBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mNearItem = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notify_system, container, false);
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
        notifyViewModel = new NotifyViewModel(getContext());
        notifyViewModel.addObserver(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvNotify.setLayoutManager(layoutManager);
        listNotifyAdapter = new ListNotifyAdapter(getContext(), notifyList);
        binding.rvNotify.setAdapter(listNotifyAdapter);
        listNotifyAdapter.setSearchSelectListener(this);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (null != moreLink) {
                    notifyViewModel.getListNotifyMore(moreLink);
                }

            }
        };
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        if (null != mNearItem) {
            notifyViewModel.getListNotifyMore(mNearItem.getLink());
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof NotifyViewModel) {
            if (null != o) {
                if (o instanceof NotifyResponse) {
                    NotifyResponse response = (NotifyResponse) o;
                    if (null != response.getData()) {
                        notifyList.addAll(response.getData().getItems());
                        moreLink = response.getData().getMore_link();
                        listNotifyAdapter.notifyDataSetChanged();
                        if (listNotifyAdapter.getItemCount() == 0) {
                            binding.tvContent.setVisibility(View.VISIBLE);
                        } else {
                            binding.tvContent.setVisibility(View.GONE);
                        }
                    } else {
                        moreLink = null;
                        binding.tvContent.setVisibility(View.VISIBLE);
                        showMessage(response.getMessage());
                    }
                } else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                    moreLink = null;
                    binding.tvContent.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    public void onSearchSelect(Object object) {
        ItemNotify notify = (ItemNotify) object;
        if (!notify.isRead()) {
            notifyViewModel.readNotify(notify.getView_link());
        }
        if (notify.isLinked()) {
            if (notify.getContent_link() != null) {
                Bundle bundle = new Bundle();
                if (notify.getContent_type().equals(Constants.TypePlace.places)) {
                    Travel travel = new Travel();
                    travel.setDetail_link(notify.getContent_link());
                    travel.setId(notify.getContent_id());
                    bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
                    mActivity.setBundle(bundle);
                    mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
                } else if (notify.getContent_type().equals(Constants.TypePlace.restaurants)) {
                    Travel travel = new Travel();
                    travel.setDetail_link(notify.getContent_link());
                    travel.setId(notify.getContent_id());
                    bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
                    mActivity.setBundle(bundle);
                    mActivity.switchFragment(SlideMenu.MenuType.DETAIL_EAT_SCREEN);
                } else if (notify.getContent_type().equals(Constants.TypePlace.hotels)) {
                    Travel travel = new Travel();
                    travel.setDetail_link(notify.getContent_link());
                    travel.setId(notify.getContent_id());
                    bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
                    mActivity.setBundle(bundle);
                    mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_STAY_SCREEN);
                } else if (notify.getContent_type().equals(Constants.TypePlace.centers)) {
                    Travel travel = new Travel();
                    travel.setDetail_link(notify.getContent_link());
                    travel.setId(notify.getContent_id());
                    bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
                    bundle.putString(Constants.IntentKey.KEY_TYPE, getString(R.string.tv_play));
                    mActivity.setBundle(bundle);
                    mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
                } else if ((null != notify.getType() && notify.getType().equals(Constants.TypePlace.post)) ||
                        (null != notify.getContent_type() && notify.getContent_type().equals(Constants.TypePlace.sponsored_posts))) {
                    News news = new News(notify.getContent_id(), notify.getContent_link(), notify.getContent_type());
                    bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
                    mActivity.setBundle(bundle);
                    mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
                } else if (null != notify.getType() && notify.getType().equals(Constants.TypePlace.link)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(notify.getContent_link()));
                    startActivity(intent);
                }
            }
        } else {
            showMessage(notify.getContent());
        }

    }
}
