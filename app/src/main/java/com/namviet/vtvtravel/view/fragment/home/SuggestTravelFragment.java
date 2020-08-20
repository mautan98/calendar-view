package com.namviet.vtvtravel.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentSuggestTravelBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.HelloLocation;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.response.SuggestTravelResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;

import java.util.Observable;
import java.util.Observer;

public class SuggestTravelFragment extends MainFragment implements Observer {

    private FragmentSuggestTravelBinding binding;
    private PlaceViewModel placeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_suggest_travel, container, false);
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
        binding.tvTitleSuggest.setText(Html.fromHtml(getString(R.string.suggest_travel_title)));
        binding.btSchedule.setOnClickListener(this);
        binding.btSuggest.setOnClickListener(this);
        binding.btMySchedule.setOnClickListener(this);
        binding.ivMenu.setOnClickListener(this);
        binding.llNotify.setOnClickListener(this);
        updateViews();

        placeViewModel.loadHelloLocation();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            if (view == binding.btSuggest) {
                mActivity.switchFragment(SlideMenu.MenuType.LIST_SUGGEST_SCREEN);
            } else if (view == binding.btSchedule) {
                mActivity.switchFragment(SlideMenu.MenuType.SCHEDULE_TRAVEL_SCREEN);
            } else if (view == binding.btMySchedule) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.IntentKey.KEY_POSITION, 1);
                mActivity.setBundle(bundle);
                mActivity.switchFragment(SlideMenu.MenuType.ACCOUNT_SCREEN);
            } else if (view == binding.ivMenu) {
                mActivity.openAndCloseDrawer();
            } else if (view == binding.llNotify) {
                goToNotifyScreen();
            }
        } else if (view == binding.ivMenu) {
            mActivity.openAndCloseDrawer();
        } else if (view == binding.llNotify) {
            goToNotifyScreen();
        } else {
            mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
        }


    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }

    @Override
    protected void setUnreadTv(TextView tvCount, int count) {
        super.setUnreadTv(tvCount, count);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            if (arg instanceof SuggestTravelResponse) {
                SuggestTravelResponse helloLocation = (SuggestTravelResponse) arg;
                binding.tvTitleSuggest.setText(helloLocation.getData().getGreeting());
                if (null != helloLocation.getData().getBanner_greeting()
                        && !"".equals(helloLocation.getData().getBanner_greeting())) {
                    setImageUrl(helloLocation.getData().getBanner_greeting(), binding.imvBackground);
                }
            }
        }
    }
}
