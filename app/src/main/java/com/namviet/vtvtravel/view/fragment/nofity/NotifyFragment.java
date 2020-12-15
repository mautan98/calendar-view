package com.namviet.vtvtravel.view.fragment.nofity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ListNotifyAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentNotifyBinding;
import com.namviet.vtvtravel.listener.SearchSelectListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.ItemNotify;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.response.NotifyResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.NotifyViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NotifyFragment extends MainFragment implements Observer, SearchSelectListener {
    private FragmentNotifyBinding binding;

    private NotifyViewModel notifyViewModel;
    private List<ItemNotify> notifyList = new ArrayList<>();
    private ListNotifyAdapter listNotifyAdapter;
    private String moreLink;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notify, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
        binding.tvTitle.setText(getString(R.string.notify));
        binding.ivBack.setOnClickListener(this);
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
        binding.rvNotify.addOnScrollListener(scrollListener);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        Account account = MyApplication.getInstance().getAccount();
        notifyViewModel.getListNotify(account.getToken(), DeviceUtils.getDeviceId(getContext()));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivBack) {
            mActivity.onBackPressed();
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
