package com.namviet.vtvtravel.view.fragment.home;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baseapp.activity.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.MyFragment;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.Utils;
import com.namviet.vtvtravel.adapter.HomeMenuAdapter;
import com.namviet.vtvtravel.adapter.newhome.NewHomeAdapter;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentHomeBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;

import com.namviet.vtvtravel.model.f2event.OnClickBookingTopMenu;
import com.namviet.vtvtravel.model.f2event.OnClickMomentInMenu;
import com.namviet.vtvtravel.model.f2event.OnClickTab;
import com.namviet.vtvtravel.model.f2event.OnClickVideoInMenu;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToCallNow;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.response.f2menu.MenuResponse;

import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.ChatActivity;
import com.namviet.vtvtravel.view.f2.CreateTripActivity;
import com.namviet.vtvtravel.view.f2.DetailDealWebviewActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.VQMMWebviewActivity;
import com.namviet.vtvtravel.view.f2.landingpage.LandingPageActivity;
import com.namviet.vtvtravel.view.f2.virtualswitchboard.VirtualSwitchBoardActivity;
import com.namviet.vtvtravel.view.f3.booking.view.BookingActivity;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealHomeActivity;
import com.namviet.vtvtravel.view.f3.floor.view.FloorActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2booking.BookingFragment;
import com.namviet.vtvtravel.view.fragment.f2menu.MenuFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog;
import com.namviet.vtvtravel.view.fragment.f2video.VideoFragment;
import com.namviet.vtvtravel.view.fragment.f2webview.VQMMWebviewFragment;
import com.namviet.vtvtravel.view.fragment.newhome.NewHomeFragment;
import com.namviet.vtvtravel.viewmodel.HomeViewModel;
import com.namviet.vtvtravel.viewmodel.newhome.NewHomeViewModel;
import com.namviet.vtvtravel.widget.HomeMenuFooter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Observable;
import java.util.Observer;

import io.sentry.util.Util;


public class HomeFragment extends MainFragment implements Observer, HomeMenuFooter.HomeBarBottomClick {
    private NewHomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private HomeMenuAdapter adapter;


    float x = 0f;
    float y = 0f;

    private boolean isShowBottomMenu = false;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initViews(View v) {
        super.initViews(v);
        getMenuDataFromCache();
        viewModel = new NewHomeViewModel();
        viewModel.addObserver(this);
        viewModel.getSetting();
        homeViewModel = new HomeViewModel(getContext());
        binding.setHomeViewModel(homeViewModel);
        homeViewModel.addObserver(this);


        switchFragment(SlideMenu.MenuType.HIGHLIGHT_SCREEN);
//        binding.menuFooter.setLogin(true);
//        binding.menuFooter.setHomeBarBottomOnClick(this);

//        binding.ivCall.setOnClickListener(this);
//        binding.ivChat.setOnClickListener(this);
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
                    if (account.isTravelingSupporter()) {
                        VirtualSwitchBoardActivity.Companion.openActivity(mActivity, VirtualSwitchBoardActivity.Companion.getTRAVEL_TYPE());
                    } else {
                        LandingPageActivity.startScreen(mActivity);
                    }
                } else {
                    LandingPageActivity.startScreen(mActivity);
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


        binding.btnShowBottomMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isShowBottomMenu) {
                        binding.viewAboveBottomMenu.setVisibility(View.GONE);
                        //      binding.motionLayout.transitionToEnd();
                        //                    binding.viewCoverBottomMenu.setVisibility(View.GONE);
                        //                    binding.btnShowBottomMenu.setRotation(45);
                        binding.addButtonMotionLayout.transitionToStart();
                        isShowBottomMenu = false;
                        hideMenuAnim();
                    } else {
                        //                    binding.btnShowBottomMenu.setRotation(0);
                        binding.addButtonMotionLayout.transitionToEnd();
                        binding.viewAboveBottomMenu.setVisibility(View.VISIBLE);
                        //       binding.motionLayout.transitionToStart();
                        //                    binding.viewCoverBottomMenu.setVisibility(View.VISIBLE);
                        isShowBottomMenu = true;
                        showMenuAnim();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        binding.imgSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    hideBottomMenu();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                FloorActivity.Companion.openActivity(mActivity);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 200);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        binding.imgCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    hideBottomMenu();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Account account = MyApplication.getInstance().getAccount();
                                if (null != account && account.isLogin()) {
                                    CreateTripActivity.startScreen(mActivity);
                                } else {
                                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 200);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        binding.imgPrize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    hideBottomMenu();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DealHomeActivity.Companion.startScreen(mActivity);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 200);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        binding.imgLuckyWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideBottomMenu();
                try {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Account account = MyApplication.getInstance().getAccount();
                                if (null != account && account.isLogin()) {
                                    VQMMWebviewActivity.startScreen(mActivity, "");
                                } else {
                                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }, 200);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

//        binding.viewCoverBottomMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hideBottomMenu();
//            }
//        });


        binding.viewAboveBottomMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideBottomMenu();
            }
        });

        binding.layoutButton.post(new Runnable() {
            @Override
            public void run() {
                x = binding.layoutButton.getX();
                y = binding.layoutButton.getY();
            }
        });


        gestureDetector = new GestureDetector(mActivity, new SingleTapConfirm());
//        binding.layoutButton.setOnTouchListener(new View.OnTouchListener() {
//            float dX, dY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                Log.e("event", event.getX() + "");
//                Log.e("event", event.getY() + "");
//
//
//                if (gestureDetector.onTouchEvent(event)) {
//                    // single tap
//
//                    if (event.getX() > 0 && event.getX() < 217) {
//                        if (event.getY() > 0 && event.getY() < 120) {
////
//                            binding.layoutButton.animate().x(x).y(y).setDuration(500L).setListener(new Animator.AnimatorListener() {
//                                @Override
//                                public void onAnimationStart(Animator animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    binding.layoutMenuFloat.setVisibility(View.VISIBLE);
//                                }
//
//                                @Override
//                                public void onAnimationCancel(Animator animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animator animation) {
//
//                                }
//                            }).start();
//                        } else {
//                            binding.layoutButton.animate().x(x).y(y).setDuration(500L).setListener(new Animator.AnimatorListener() {
//                                @Override
//                                public void onAnimationStart(Animator animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    ChatActivity.startScreen(mActivity);
//                                }
//
//                                @Override
//                                public void onAnimationCancel(Animator animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animator animation) {
//
//                                }
//                            }).start();
////
//                        }
//                    }
////                    if (layout.getVisibility() == View.INVISIBLE) {
//////                        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//////                        mWindowManager.updateViewLayout(mChatHeadView, params);
////                        layout.setVisibility(View.VISIBLE);
////                    } else {
//////                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//////                        mWindowManager.updateViewLayout(mChatHeadView, params);
////                        layout.setVisibility(View.INVISIBLE);
////                    }
//                    return true;
//                } else {
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        dX = binding.layoutButton.getX() - event.getRawX();
//                        dY = binding.layoutButton.getY() - event.getRawY();
//                        return true;
//                    }
//
////                    binding.layoutButton.animate()
////                            .x(event.getRawX() + dX)
////                            .y(event.getRawY() + dY)
////                            .setDuration(0)
////                            .start();
//                    binding.layoutButton.setX(event.getRawX() + dX);
//                    binding.layoutButton.setY(event.getRawY() + dY);
//
////                    trashView.setVisibility(View.VISIBLE);
//
////                    if(event.getAction() == MotionEvent.ACTION_UP){
////                        trashView.setVisibility(View.GONE);
////                    }
//                    return true;
//                }
//            }
//        });



        binding.layoutButton.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction = 0;
            private int initialX = 0;
            private int initialY = 0;
            private float initialTouchX = 0f;
            private float initialTouchY = 0f;


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x_cord = (int) motionEvent.getRawX();
                if (gestureDetector.onTouchEvent(motionEvent)) {
                    if (motionEvent.getX() > 0 && motionEvent.getX() < 217) {
                        if (motionEvent.getY() > 0 && motionEvent.getY() < 120) {
//
                            binding.layoutButton.animate().x(x).y(y).setDuration(500L).setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    binding.layoutMenuFloat.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }).start();
                        } else {
                            binding.layoutButton.animate().x(x).y(y).setDuration(500L).setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    ChatActivity.startScreen(mActivity);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }).start();
//
                        }
                    }
//                    if (layout.getVisibility() == View.INVISIBLE) {
////                        params.width = WindowManager.LayoutParams.MATCH_PARENT;
////                        mWindowManager.updateViewLayout(mChatHeadView, params);
//                        layout.setVisibility(View.VISIBLE);
//                    } else {
////                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
////                        mWindowManager.updateViewLayout(mChatHeadView, params);
//                        layout.setVisibility(View.INVISIBLE);
//                    }
                    return true;
                } else {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            initialX = (int) binding.layoutButton.getX();
                            initialY = (int) binding.layoutButton.getY();

                            initialTouchX = motionEvent.getRawX();
                            initialTouchY = motionEvent.getRawY();

                            lastAction = motionEvent.getAction();
                            return true;
                        case MotionEvent.ACTION_UP:
                            if (lastAction != MotionEvent.ACTION_DOWN) {
                                resetPosition(x_cord);
                            } else {
                            }
                            lastAction = motionEvent.getAction();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            int x = Math.round(initialX + (motionEvent.getRawX() - initialTouchX));
                            int y = Math.round(initialY + (motionEvent.getRawY() - initialTouchY));
                            if (x < 0 || y < 0 || x > getScreenWidth() || y > getScreenHeight() || initialX == x && initialY == y) {
                                return true;
                            }
                            //Update the layout with new X & Y coordinate

                            binding.layoutButton.setX(x);
                            binding.layoutButton.setY(y);

                            lastAction = motionEvent.getAction();
                            return true;
                    }
                }
                return false;
            }

        });


        updateViews();
    }


    private void resetPosition(int x_cord_now) {
        if (x_cord_now <= getScreenWidth() / 2) {
            moveToLeft();
        } else {
            moveToRight();
        }

    }


    private void updateAnimateLocation(int i) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) binding.layoutButton.getX(), i);
        valueAnimator.addUpdateListener(it -> {
            binding.layoutButton.setX((int) it.getAnimatedValue());
        });
        valueAnimator.setDuration(250);
        valueAnimator.start();
    }


    private void moveToLeft() {
        updateAnimateLocation(0);
    }

    /*  Method to move the Floating widget view to Right  */
    private void moveToRight() {
        updateAnimateLocation(getScreenWidth() - binding.layoutButton.getWidth());
    }



    private GestureDetector gestureDetector;

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    private void showMenuAnim() {
        Animation scaleDown = AnimationUtils.loadAnimation(mActivity, R.anim.scale_up1);
        scaleDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                try {
                    binding.redStar.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.redStar.startAnimation(scaleDown);
    }

    private void hideMenuAnim() {
        Animation scaleDown = AnimationUtils.loadAnimation(mActivity, R.anim.scale_down1);
        scaleDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    binding.redStar.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.redStar.startAnimation(scaleDown);
    }

    private void hideBottomMenu() {
//        binding.btnShowBottomMenu.setRotation(45);
        binding.viewAboveBottomMenu.setVisibility(View.GONE);
//        binding.viewCoverBottomMenu.setVisibility(View.GONE);
        //       binding.motionLayout.transitionToEnd();
        binding.addButtonMotionLayout.transitionToStart();
        if (isShowBottomMenu)
            hideMenuAnim();
        isShowBottomMenu = false;
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        binding.tabLayoutMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setOnSelectView(binding.tabLayoutMain, tab.getPosition());
                hideBottomMenu();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setUnSelectView(binding.tabLayoutMain, tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getActivity().sendBroadcast(new Intent("SCROLL_TO_TOP"));
                }
                hideBottomMenu();
            }
        });
        //       binding.motionLayout.transitionToEnd();
//        checkPermission();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof NewHomeViewModel && null != o) {
            if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
                } catch (Exception e) {
                }
            } else if (o instanceof MenuResponse) {
                menuResponse = (MenuResponse) o;
                PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.MENU_DATA, new Gson().toJson(menuResponse));
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
        binding.layoutButton.setVisibility(View.VISIBLE);
//        binding.menuFooter.setIndexCurrent(position);
        switchFragment(SlideMenu.MenuType.NEW_MENU);
        mActivity.showDialogNoCon();

    }

    @Override
    public void onHotClick(int position) {
        binding.layoutButton.setVisibility(View.VISIBLE);
//        binding.menuFooter.setIndexCurrent(position);
        switchFragment(SlideMenu.MenuType.HIGHLIGHT_SCREEN);
        mActivity.showDialogNoCon();
    }

    @Override
    public void onSuggestClick(int position) {
        postEventBusClickTab();
        binding.layoutButton.setVisibility(View.GONE);
//        binding.menuFooter.setIndexCurrent(position);
        switchFragment(SlideMenu.MenuType.BOOKING_SCREEN);
        mActivity.showDialogNoCon();
//        Account account = MyApplication.getInstance().getAccount();
//        if (null != account && account.isLogin()) {
//        } else {
//            LoginAndRegisterActivityNew.startScreen(mActivity, 0, false, true);
//        }
    }

    @Override
    public void onMomentClick(int position) {
        postEventBusClickTab();
        binding.layoutButton.setVisibility(View.VISIBLE);
//        binding.menuFooter.setIndexCurrent(position);
        switchFragment(SlideMenu.MenuType.MOMENT_TRAVEL_SCREEN);
        mActivity.showDialogNoCon();
    }

    @Override
    public void onVideoClick(int position) {
        postEventBusClickTab();
        binding.layoutButton.setVisibility(View.VISIBLE);
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
    public void onClickBookingFromTopMenu(OnClickBookingTopMenu onLoginSuccessAndUpdateUserView) {
//        BookingActivity.Companion.openActivity(mActivity);
        try {
            OneButtonTitleImageDialog oneButtonTitleImageDialog = new OneButtonTitleImageDialog();
            oneButtonTitleImageDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
        } catch (Exception exception) {

        }
    }


    @Subscribe
    public void onClickVideo(OnClickVideoInMenu onClickVideoInMenu) {
        try {
            int size = menuResponse.getData().getMenus().getFooter().size();
            for (int i = 0; i < size; i++) {
                if (menuResponse.getData().getMenus().getFooter().get(i).getCode_type().equals("APP_FOOTER_VIDEO")) {
                    TabLayout.Tab newTab = binding.tabLayoutMain.getTabAt(i);
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
            int size = menuResponse.getData().getMenus().getFooter().size();
            for (int i = 0; i < size; i++) {
                if (menuResponse.getData().getMenus().getFooter().get(i).getCode_type().equals("APP_FOOTER_MOMENT")) {
                    TabLayout.Tab newTab = binding.tabLayoutMain.getTabAt(i);
                    newTab.select();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        onMomentClick(1);
    }

    private void getMenuDataFromCache() {
        try {
            String json = PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.MENU_DATA, new Gson().toJson(menuResponse));
            menuResponse = new Gson().fromJson(json, MenuResponse.class);
            getMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private MenuResponse menuResponse;

    private void getMainMenu() {
//        menuResponse = new Gson().fromJson(json, MenuResponse.class);
        try {
            binding.tabLayoutMain.removeAllTabs();
            int size = menuResponse.getData().getMenus().getFooter().size();

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
                if (i == 0) {
                    Glide.with(mActivity).load(menuResponse.getData().getMenus().getFooter().get(i).getIcon_enable_url()).into(imgHome);
                } else {
                    Glide.with(mActivity).load(menuResponse.getData().getMenus().getFooter().get(i).getIcon_url()).into(imgHome);
                }

                if (i == 2) {
                    tvHome.setVisibility(View.GONE);
                    imgHome.setVisibility(View.GONE);
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

            switch (menuResponse.getData().getMenus().getFooter().get(position).getCode_type()) {
                case "APP_FOOTER_HOME":
                    onHotClick(0);
                    break;
                case "APP_FOOTER_MOMENT":
                    onMomentClick(0);
                    break;
                case "APP_FOOTER_BOOKING":
//                    onSuggestClick(0);
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


    private int screenWidth = 0;
    private int screenHeight = 0;
    private int getScreenWidth(){
        if(screenWidth == 0) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
        }
        return screenWidth;
    }


    private int getScreenHeight(){
        int topAddButton = (int) binding.addButtonMotionLayout.getY();
        if(screenHeight == 0) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenHeight = topAddButton - dpToPx(100);
        }
        return screenHeight;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
