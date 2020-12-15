package com.namviet.vtvtravel.view.fragment.f2offline;

import androidx.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentPageMainLoginBinding;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2account.LoginF2Fragment;
import com.namviet.vtvtravel.view.fragment.f2account.RegisterF2Fragment;

import java.util.Objects;

public class MainPageLoginFragment extends MainFragment {

    private F2FragmentPageMainLoginBinding binding;
    private int position;

    public static MainPageLoginFragment newInstance(Bundle bundle) {
        MainPageLoginFragment fragment = new MainPageLoginFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            position = getArguments().getInt(Constants.IntentKey.KEY_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_page_main_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    protected void initViews(View v) {
        super.initViews(v);
        updateViews();
        setClick();
        renderViewPager();
        binding.vpMainLogin.setCurrentItem(position);
    }

    private void setClick() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        binding.btnTabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               tabLoginSelected();
            }
        });

        binding.btnTabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               tabRegisterSelected();
            }
        });

        binding.vpMainLogin.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
                } catch (Exception e) {

                }
                if(position == 0){
                    tabLoginSelected();
                }else {
                    tabRegisterSelected();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void renderViewPager() {
        MainAdapter mainAdapter = new MainAdapter(getChildFragmentManager());
        LoginF2Fragment loginF2Fragment = new LoginF2Fragment();
        mainAdapter.addFragment(loginF2Fragment, "loginF2Fragment");
        RegisterF2Fragment registerF2Fragment = new RegisterF2Fragment();
        mainAdapter.addFragment(registerF2Fragment, "registerF2Fragment");
        binding.vpMainLogin.setAdapter(mainAdapter);
//
    }



//    @Override
    protected void updateViews() {
        super.updateViews();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void tabLoginSelected(){
        binding.vpMainLogin.setCurrentItem(0,true);
        Typeface typeface = ResourcesCompat.getFont(mActivity, R.font.roboto_black);
        binding.btnTabLogin.setTypeface(typeface);
        binding.btnTabLogin.setAlpha(1);

        Typeface typeface1 = ResourcesCompat.getFont(mActivity, R.font.roboto_regular);
        binding.btnTabRegister.setTypeface(typeface1);
        binding.btnTabRegister.setAlpha(0.5f);
    }

    private void tabRegisterSelected(){
        binding.vpMainLogin.setCurrentItem(1,true);
        Typeface typeface = ResourcesCompat.getFont(mActivity, R.font.roboto_regular);
        binding.btnTabLogin.setTypeface(typeface);
        binding.btnTabLogin.setAlpha(0.5f);

        Typeface typeface1 = ResourcesCompat.getFont(mActivity, R.font.roboto_black);
        binding.btnTabRegister.setTypeface(typeface1);
        binding.btnTabRegister.setAlpha(1);
    }

}
