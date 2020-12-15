package com.namviet.vtvtravel.view.fragment.f2account;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentPageMainLoginBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;

import java.util.Objects;

public class MainPageLoginAndRegisterFragment extends BaseFragment<F2FragmentPageMainLoginBinding> {

    private int position;
    private Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_page_main_login;
    }

    @Override
    public void initView() {
        renderViewPager();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getBinding().vpMainLogin.setCurrentItem(position);
            }
        }, 200);

    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().btnTabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabLoginSelected();
            }
        });

        getBinding().btnTabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabRegisterSelected();
            }
        });

        getBinding().vpMainLogin.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 0){
                    tabLoginSelected();
                }else {
                    tabRegisterSelected();
                }

                try {
                    KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
                } catch (Exception e) {

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setObserver() {

    }


    private void tabLoginSelected(){
        getBinding().vpMainLogin.setCurrentItem(0,true);
        Typeface typeface = ResourcesCompat.getFont(mActivity, R.font.roboto_black);
        getBinding().btnTabLogin.setTypeface(typeface);
        getBinding().btnTabLogin.setAlpha(1);

        Typeface typeface1 = ResourcesCompat.getFont(mActivity, R.font.roboto_regular);
        getBinding().btnTabRegister.setTypeface(typeface1);
        getBinding().btnTabRegister.setAlpha(0.5f);
    }

    private void tabRegisterSelected(){
        getBinding().vpMainLogin.setCurrentItem(1,true);
        Typeface typeface = ResourcesCompat.getFont(mActivity, R.font.roboto_regular);
        getBinding().btnTabLogin.setTypeface(typeface);
        getBinding().btnTabLogin.setAlpha(0.5f);

        Typeface typeface1 = ResourcesCompat.getFont(mActivity, R.font.roboto_black);
        getBinding().btnTabRegister.setTypeface(typeface1);
        getBinding().btnTabRegister.setAlpha(1);
    }

    private void renderViewPager() {
        MainAdapter mainAdapter = new MainAdapter(getChildFragmentManager());
        LoginF2Fragment loginF2Fragment = new LoginF2Fragment();
        mainAdapter.addFragment(loginF2Fragment, "loginF2Fragment");
        RegisterF2Fragment registerF2Fragment = new RegisterF2Fragment();
        mainAdapter.addFragment(registerF2Fragment, "registerF2Fragment");
        getBinding().vpMainLogin.setAdapter(mainAdapter);
//
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.LOGIN_AND_REGISTER, TrackingAnalytic.ScreenTitle.LOGIN_AND_REGISTER);
    }
}
