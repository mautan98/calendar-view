package com.namviet.vtvtravel.view.fragment.f2callnow;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.utils.KeyboardUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentMainCallNowBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnBackToHome;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.MainOfflineFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainCallNowFragment extends MainFragment {

    private F2FragmentMainCallNowBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_main_call_now, container, false);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateViews();
                checkPermission();
            }
        }, 500);


    }

    @Override
    protected void updateViews() {
        super.updateViews();
        renderViewPager();
        handleViewPager();
        setClick();
        setupTabIcons();
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            if(account.getImageProfile()!=null && !account.getImageProfile().isEmpty()) {
                Glide.with(mActivity).load(account.getImageProfile()).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.f2_defaut_user).error(R.drawable.f2_defaut_user).into(binding.imgAvatar);
            }
        }else {

        }

        try {
            binding.txtTitleBanner2.setText(account.getFullname() != null?account.getFullname():"Bạn"+" ơi, giờ đây bạn có thể trò chuyện với bạn bè \ntiện hơn rất nhiều");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private MainAdapter mainAdapter;
    private MainContactFragment mainContactFragment;
    private void renderViewPager() {
         mainAdapter = new MainAdapter(getChildFragmentManager());
        MainCallHistoryFragment mainCallHistoryFragment = new MainCallHistoryFragment();
        mainAdapter.addFragment(mainCallHistoryFragment, "mainCallHistoryFragment");
        mainContactFragment = new MainContactFragment();
        mainAdapter.addFragment(mainContactFragment, "mainContactFragment");
        binding.vpMainCallNow.setAdapter(mainAdapter);
        binding.tabLayout.setupWithViewPager(binding.vpMainCallNow);
//
    }

    private void setClick(){
        binding.viewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutLearnMore.setVisibility(View.GONE);
            }
        });

        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideKeyboard(mActivity, binding.getRoot());
                mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, new UserFragment()).addToBackStack(null).commit();
            }
        });

        binding.btnToCallScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, new SearchNumberFragment()).addToBackStack(null).commit();
            }
        });

        binding.btnLearnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainOfflineFragment mainOfflineFragment = new MainOfflineFragment();
                mainOfflineFragment.setFromCallNow(Constants.TypeOpenOffline.FROM_LEARN_MORE);
                mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, mainOfflineFragment).addToBackStack(null).commit();
            }
        });
    }

    private void setupTabIcons() {
        View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab, null);
        View tabNew = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab, null);


        ImageView imgHome = tabHome.findViewById(R.id.imgIconTab);
        ImageView imgNew = tabNew.findViewById(R.id.imgIconTab);

        TextView tvHome = tabHome.findViewById(R.id.tvIconTab);
        TextView tvNew = tabNew.findViewById(R.id.tvIconTab);

        imgHome.setImageResource(R.drawable.f2_missing_call_change_icon_control);
        imgNew.setImageResource(R.drawable.f2_contact_change_icon_control);

        tvHome.setText("Lịch sử cuộc gọi");
        tvHome.setTextColor(Color.parseColor("#999999"));
        tvNew.setText("Danh bạ");

        tabHome.setSelected(true);
        binding.tabLayout.getTabAt(0).setCustomView(tabHome);
        binding.tabLayout.getTabAt(1).setCustomView(tabNew);

        binding.tabLayout.addOnTabSelectedListener(OnTabSelectedListener);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void OnBackToHome(OnBackToHome onBackToHome){
        mActivity.onBackPressed();
    }



    private void checkPermission(){
        mActivity.checkPermission();
    }

    public void handleViewPager(){
        binding.vpMainCallNow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    mainContactFragment.reloadVp();
                }

                KeyboardUtils.hideKeyboard(mActivity, binding.getRoot());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
