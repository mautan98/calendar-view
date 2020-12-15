package com.namviet.vtvtravel.view.fragment.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.MyFragment;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.HomeMenuAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.FragmentHomeBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;

import com.namviet.vtvtravel.model.f2event.OnClickMomentInMenu;
import com.namviet.vtvtravel.model.f2event.OnClickTab;
import com.namviet.vtvtravel.model.f2event.OnClickVideoInMenu;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToCallNow;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.response.f2menu.MenuResponse;

import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.ChatActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.virtualswitchboard.VirtualSwitchBoardActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2booking.BookingFragment;
import com.namviet.vtvtravel.view.fragment.f2menu.MenuFragment;
import com.namviet.vtvtravel.view.fragment.f2video.VideoFragment;
import com.namviet.vtvtravel.view.fragment.newhome.NewHomeFragment;
import com.namviet.vtvtravel.viewmodel.HomeViewModel;
import com.namviet.vtvtravel.viewmodel.newhome.NewHomeViewModel;
import com.namviet.vtvtravel.widget.HomeMenuFooter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Observable;
import java.util.Observer;


public class HomeFragment extends MainFragment implements Observer, HomeMenuFooter.HomeBarBottomClick {
    private NewHomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private HomeMenuAdapter adapter;

    private Fragment mFragment = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
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
        viewModel = new NewHomeViewModel();
        viewModel.addObserver(this);
        viewModel.getSetting();
        homeViewModel = new HomeViewModel(getContext());
        binding.setHomeViewModel(homeViewModel);
        homeViewModel.addObserver(this);


        switchFragment(SlideMenu.MenuType.HIGHLIGHT_SCREEN);
//        binding.menuFooter.setLogin(true);
//        binding.menuFooter.setHomeBarBottomOnClick(this);

        binding.ivCall.setOnClickListener(this);
        binding.ivChat.setOnClickListener(this);
        binding.ivCallFake.setOnClickListener(this);
        binding.layoutMenuFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutMenuFloat.setVisibility(View.GONE);
            }
        });
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()
                && account.isTravelingSupporter()) {
            binding.btnVirtualCall.setVisibility(View.VISIBLE);
        } else {
//            binding.btnVirtualCall.setVisibility(View.GONE);
        }

        binding.btnVirtualCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutMenuFloat.setVisibility(View.GONE);
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                   if(account.isTravelingSupporter()){
                       VirtualSwitchBoardActivity.Companion.openActivity(mActivity);
                   }else {
                       Toast.makeText(mActivity, "Tính năng chỉ dành cho sở du lịch", Toast.LENGTH_SHORT).show();
                   }
                } else {
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }

            }
        });
        binding.btnCallNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mActivity.openNewLogin(0);
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    mActivity.switchFragment(SlideMenu.MenuType.MAIN_CALL_NOW_SCREEN);
                    binding.layoutMenuFloat.setVisibility(View.GONE);
                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean(Constants.IntentKey.IS_FROM_BUTTON_CALL_NOW, true);
//                    mActivity.setBundle(bundle);
//                    mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);

                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, true);
                }
            }
        });

        binding.btnCall1039.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(getString(R.string.calling_address));
            }
        });

        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        binding.tabLayoutMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                setOnSelectView(binding.tabLayoutMain, tab.getPosition());



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setUnSelectView(binding.tabLayoutMain, tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        checkPermission();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof NewHomeViewModel && null != o) {
            if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    Toast.makeText(mActivity, responseError.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            } else if (o instanceof MenuResponse) {
                menuResponse = (MenuResponse) o;
                getMainMenu();
            }

        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        homeViewModel.reset();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivCall) {
//            call(getString(R.string.calling_address));

            binding.layoutMenuFloat.setVisibility(View.VISIBLE);
        } else if (view == binding.ivChat) {
//            mActivity.switchFragment(SlideMenu.MenuType.CHAT_SCREEN);
            ChatActivity.startScreen(mActivity);
        } else if (view == binding.ivCallFake) {
            binding.layoutMenuFloat.setVisibility(View.GONE);
        }
    }

    @Override
    public void switchFragment(SlideMenu.MenuType screen) {
        super.switchFragment(screen);
        switch (screen) {
            case HIGHLIGHT_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), NewHomeFragment.class, null, false);
                break;
            case LIST_VIDEO_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), VideoFragment.class, null, false);
                break;
            case SUGGEST_TRAVEL_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), SuggestTravelFragment.class, null, false);
                break;
            case MOMENT_TRAVEL_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), YourMomentFragment.class, null, false);
                break;
            case NEW_MENU:
                MyFragment.openFragment(this, getContentFrame(), MenuFragment.class, null, false);
                break;
            case BOOKING_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), BookingFragment.class, null, false);
                break;
        }
    }

    @Override
    public int getContentFrame() {
        return R.id.frHome;
    }


    private void postEventBusClickTab() {
        EventBus.getDefault().post(new OnClickTab());
    }

    @Override
    public void onMenuClick(int position) {
        postEventBusClickTab();
//        binding.menuFooter.setIndexCurrent(position);
        switchFragment(SlideMenu.MenuType.NEW_MENU);
        mActivity.showDialogNoCon();

    }

    @Override
    public void onHotClick(int position) {
//        binding.menuFooter.setIndexCurrent(position);
        switchFragment(SlideMenu.MenuType.HIGHLIGHT_SCREEN);
        mActivity.showDialogNoCon();

    }

    @Override
    public void onSuggestClick(int position) {
        postEventBusClickTab();
//        binding.menuFooter.setIndexCurrent(position);
        switchFragment(SlideMenu.MenuType.BOOKING_SCREEN);
        mActivity.showDialogNoCon();
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
        } else {
            LoginAndRegisterActivityNew.startScreen(mActivity, 0, false, true);
        }
    }

    @Override
    public void onMomentClick(int position) {
        postEventBusClickTab();
//        binding.menuFooter.setIndexCurrent(position);
        switchFragment(SlideMenu.MenuType.MOMENT_TRAVEL_SCREEN);
        mActivity.showDialogNoCon();
    }

    @Override
    public void onVideoClick(int position) {
        postEventBusClickTab();
//        binding.menuFooter.setIndexCurrent(position);
        switchFragment(SlideMenu.MenuType.LIST_VIDEO_SCREEN);
        mActivity.showDialogNoCon();
    }

    @Override
    public void onNavigationLogin() {

    }

    public void call(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder
                .setMessage("" + message)
                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String phone = "1039";
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.dimiss, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 80;


    @Subscribe
    public void onLoginSuccessAndGoToCallNow(OnLoginSuccessAndGoToCallNow onLoginSuccessAndGoToCallNow) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            mActivity.switchFragment(SlideMenu.MenuType.MAIN_CALL_NOW_SCREEN);
            binding.layoutMenuFloat.setVisibility(View.GONE);
        } else {
//            Bundle bundle = new Bundle();
//            bundle.putBoolean(Constants.IntentKey.IS_FROM_BUTTON_CALL_NOW, true);
//            mActivity.setBundle(bundle);
//            mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
        }
    }

    @Subscribe
    public void onReloadUserView(OnLoginSuccessAndUpdateUserView onLoginSuccessAndUpdateUserView) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()
                && account.isTravelingSupporter()) {
            binding.btnVirtualCall.setVisibility(View.VISIBLE);
        } else {
//            binding.btnVirtualCall.setVisibility(View.GONE);
        }
    }


    @Subscribe
    public void onClickVideo(OnClickVideoInMenu onClickVideoInMenu) {
        try {
            int size  = menuResponse.getData().getMenus().getFooter().size();
            for (int i = 0; i < size; i++) {
                if(menuResponse.getData().getMenus().getFooter().get(i).getCode_type().equals("APP_FOOTER_VIDEO")){
                    TabLayout.Tab newTab =  binding.tabLayoutMain.getTabAt(i);
                    newTab.select();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        onVideoClick(3);


    }

    @Subscribe
    public void onClickVideo(OnClickMomentInMenu onClickMomentInMenu) {
        try {
            int size  = menuResponse.getData().getMenus().getFooter().size();
            for (int i = 0; i < size; i++) {
                if(menuResponse.getData().getMenus().getFooter().get(i).getCode_type().equals("APP_FOOTER_MOMENT")){
                    TabLayout.Tab newTab =  binding.tabLayoutMain.getTabAt(i);
                    newTab.select();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        onMomentClick(1);
    }


    private MenuResponse menuResponse;
    private void getMainMenu() {
//        menuResponse = new Gson().fromJson(json, MenuResponse.class);
        try {
            int size  = menuResponse.getData().getMenus().getFooter().size();

            for (int i = 0; i < size; i++) {
                View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_layout_main_tab_footer_app, null);
                TextView tvHome = tabHome.findViewById(R.id.tvTitle);
                tvHome.setText((menuResponse.getData().getMenus().getFooter().get(i).getName()));
                if (i == 0) {
                    tvHome.setTextColor(Color.parseColor("#00918D"));
                } else {
                    tvHome.setTextColor(Color.parseColor("#65676B"));
                }

                ImageView imgHome = tabHome.findViewById(R.id.imgAvatar);
                if(i == 0){
                    Glide.with(mActivity).load(menuResponse.getData().getMenus().getFooter().get(i).getIcon_enable_url()).into(imgHome);
                }else {
                    Glide.with(mActivity).load(menuResponse.getData().getMenus().getFooter().get(i).getIcon_url()).into(imgHome);
                }

                binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setCustomView(tabHome));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnSelectView(TabLayout tabLayout, int position) {
        try {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View selected = tab.getCustomView();
            TextView iv_text = selected.findViewById(R.id.tvTitle);
            iv_text.setTextColor(Color.parseColor("#00918D"));

            ImageView imageView = selected.findViewById(R.id.imgAvatar);
            Glide.with(mActivity).load(menuResponse.getData().getMenus().getFooter().get(position).getIcon_enable_url()).into(imageView);

            switch (menuResponse.getData().getMenus().getFooter().get(position).getCode_type()){
                case "APP_FOOTER_HOME":
                    onHotClick(0);
                    break;
                case "APP_FOOTER_MOMENT":
                    onMomentClick(0);
                    break;
                case "APP_FOOTER_BOOKING":
                    onSuggestClick(0);
                    break;
                case "APP_FOOTER_VIDEO":
                    onVideoClick(0);
                    break;
                case "APP_FOOTER_MENU":
                    onMenuClick(0);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setUnSelectView(TabLayout tabLayout, int position) {
        try {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View selected = tab.getCustomView();
            TextView iv_text = selected.findViewById(R.id.tvTitle);
            iv_text.setTextColor(Color.parseColor("#65676B"));

            ImageView imageView = selected.findViewById(R.id.imgAvatar);
            Glide.with(mActivity).load(menuResponse.getData().getMenus().getFooter().get(position).getIcon_url()).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
