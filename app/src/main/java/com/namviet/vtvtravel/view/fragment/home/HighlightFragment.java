package com.namviet.vtvtravel.view.fragment.home;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.HomeCategoryAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentHighlightBinding;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.CategoryHome;
import com.namviet.vtvtravel.response.ItemCategoryData;
import com.namviet.vtvtravel.ultils.FirebaseAnalytic;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.HomeViewModel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class HighlightFragment extends MainFragment implements Observer {
    private FragmentHighlightBinding binding;
    private HomeViewModel homeViewModel;

    private HomeCategoryAdapter categoryAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static HighlightFragment newInstance() {
        HighlightFragment highlightFragment = new HighlightFragment();
        return highlightFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_highlight, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
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
        homeViewModel = new HomeViewModel(getContext());
        binding.setHomeViewModel(homeViewModel);
        homeViewModel.addObserver(this);
        setTvCountUnread(binding.tvNotification);
        binding.rvHome.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvHome.setLayoutManager(mLayoutManager);

        categoryAdapter = new HomeCategoryAdapter(getContext());
        binding.rvHome.setAdapter(categoryAdapter);

        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                double percentage = (double) Math.abs(verticalOffset) / binding.toolbar.getHeight();
                if (percentage > 0.8) {
                    binding.rlMenuScroll.setVisibility(View.GONE);
                    binding.llMenuToolbar.setVisibility(View.VISIBLE);
                    binding.ivLogoHome.setVisibility(View.INVISIBLE);

                } else {
                    binding.rlMenuScroll.setVisibility(View.VISIBLE);
                    binding.llMenuToolbar.setVisibility(View.GONE);
                    binding.ivLogoHome.setVisibility(View.VISIBLE);

                }
            }
        });

        binding.ivMenu.setOnClickListener(this);
        binding.llNotify.setOnClickListener(this);
        binding.tvSearch.setOnClickListener(this);
        binding.llWhere.setOnClickListener(this);
        binding.llFood.setOnClickListener(this);
        binding.llLiving.setOnClickListener(this);
        binding.llPlay.setOnClickListener(this);
        binding.tbWhere.setOnClickListener(this);
        binding.tbFood.setOnClickListener(this);
        binding.tbPlay.setOnClickListener(this);
        binding.tbLiving.setOnClickListener(this);
        binding.tbSearch.setOnClickListener(this);
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
                homeViewModel.loadDataHome();
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
        } else if (view == binding.llWhere) {
            Travel travel = new Travel();
            travel.setCode(Constants.TypeItemHome.WHERE_GO);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
            mActivity.setBundle(bundle);
            mActivity.switchFragment(SlideMenu.MenuType.WHERE_SCREEN);
            Log.d("LamLV: ", "Click on O Dau Menu");
            mActivity.pushEvent(FirebaseAnalytic.CLICK_HP_HEADER_DESTINATION);
        } else if (view == binding.llFood) {
            mActivity.switchFragment(SlideMenu.MenuType.FOOD_SCREEN);
            mActivity.pushEvent(FirebaseAnalytic.CLICK_HP_HEADER_RESTAURANT);
        } else if (view == binding.llLiving) {
            mActivity.switchFragment(SlideMenu.MenuType.LIVING_SCREEN);
            mActivity.pushEvent(FirebaseAnalytic.CLICK_HP_HEADER_HOTEL);
        } else if (view == binding.llPlay) {
            mActivity.switchFragment(SlideMenu.MenuType.PLAY_SCREEN);
            mActivity.pushEvent(FirebaseAnalytic.CLICK_HP_HEADER_EVENT);
        } else if (view == binding.tvSearch) {
            mActivity.pushEvent(FirebaseAnalytic.CLICK_HP_SEARCH);
            mActivity.switchFragment(SlideMenu.MenuType.SEARCH_SCREEN);
        } else if (view == binding.tbWhere) {
            binding.llWhere.performClick();
            mActivity.pushEvent(FirebaseAnalytic.CLICK_HP_NAVIGATION_DESTINATION);
        } else if (view == binding.tbFood) {
            binding.llFood.performClick();
            mActivity.pushEvent(FirebaseAnalytic.CLICK_HP_NAVIGATION_RESTAURANT);
        } else if (view == binding.tbPlay) {
            binding.llPlay.performClick();
            mActivity.pushEvent(FirebaseAnalytic.CLICK_HP_NAVIGATION_EVENT);
        } else if (view == binding.tbLiving) {
            binding.llLiving.performClick();
            mActivity.pushEvent(FirebaseAnalytic.CLICK_HP_NAVIGATION_HOTEL);
        } else if (view == binding.tbSearch) {
            binding.tvSearch.performClick();
        }
    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof HomeViewModel) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (o != null) {
                        if (o instanceof ItemCategoryData) {
                            ItemCategoryData itemCategoryData = (ItemCategoryData) o;
                            List<CategoryHome> listCate = categoryAdapter.getCategoryHomeList();
                            for (int i = 0; i < listCate.size(); i++) {
                                if (listCate.get(i).getCode().equals(itemCategoryData.getCode())) {
                                    listCate.get(i).setTravelList(itemCategoryData.getItems());
                                    break;
                                }
                            }
                            categoryAdapter.updateCategoryHomeList(listCate);
                        }
                    } else {
                        if (homeViewModel.getListCategory() != null) {
                            if (null != homeViewModel.getMenuHeader() && null != homeViewModel.getMenuHeader().getBackground_urls() && homeViewModel.getMenuHeader().getBackground_urls().size() > 0) {
                                homeViewModel.getMenuHeader();
                                setImageUrl(homeViewModel.getMenuHeader().getBackground_urls().get(0), binding.backgroundImageView);
                            }
                            dimissDialogLoading();
                            categoryAdapter.updateCategoryHomeList(homeViewModel.getListCategory());
                            homeViewModel.loadItemCategory(homeViewModel.getListCategory());
                        }
                    }
                }
            });

        }
    }

//    public void scaleView(View v, float startScale) {
//        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat(View.SCALE_X, 0, startScale);
//        PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0, startScale);
//        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(v, valuesHolder1,valuesHolder2);
//        anim.setDuration(1000); // duration 3 seconds
//        anim.start();
//    }


    @Override
    public void onResume() {
        super.onResume();
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            setTvCountUnread(binding.tvNotification);
        } else {
            binding.tvNotification.setText("");
        }
    }

    @Override
    protected void setUnreadTv(TextView tvCount, int count) {
        super.setUnreadTv(tvCount, count);
    }
}
