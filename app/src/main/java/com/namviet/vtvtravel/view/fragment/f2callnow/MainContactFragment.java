package com.namviet.vtvtravel.view.fragment.f2callnow;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentMainCallNowBinding;
import com.namviet.vtvtravel.databinding.F2FragmentMainContactBinding;
import com.namviet.vtvtravel.model.f2event.OnLoadContactSuccess;
import com.namviet.vtvtravel.view.fragment.MainFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainContactFragment extends MainFragment {

    private F2FragmentMainContactBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_main_contact, container, false);
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
        updateViews();
        binding.btnCallNow.setOnClickListener(this);
        binding.btnAll.setOnClickListener(this);
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        renderViewPager();
        handleSearch();
        handleDoneSearch();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btnCallNow:
                binding.vpMainContact.setCurrentItem(0);
                break;
            case R.id.btnAll:
                binding.vpMainContact.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private ContactCallNowFragment contactCallNowFragment;
    private ContactAllFragment contactAllFragment;
    private void renderViewPager() {
        MainAdapter mainAdapter = new MainAdapter(getChildFragmentManager());
        contactCallNowFragment = new ContactCallNowFragment();
        mainAdapter.addFragment(contactCallNowFragment, "contactCallNowFragment");
        contactAllFragment = new ContactAllFragment();
        mainAdapter.addFragment(contactAllFragment, "contactAllFragment");
        binding.vpMainContact.setAdapter(mainAdapter);

        binding.vpMainContact.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btnCallNow();
                    refreshCallNow();
                } else {
                    btnAll();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//
    }

    private void btnCallNow() {
        binding.btnCallNow.setAlpha(1.0f);
        binding.btnAll.setAlpha(0.5f);
    }

    private void btnAll() {
        binding.btnCallNow.setAlpha(0.5f);
        binding.btnAll.setAlpha(1.0f);
    }

    private void handleSearch(){
        RxTextView.afterTextChangeEvents(binding.edtSearch)
                .skipInitialValue()
                .debounce(450, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                        search(binding.edtSearch.getText().toString());
                    }
                });
    }

    private void search(String string) {
        contactAllFragment.search(string, binding.vpMainContact.getCurrentItem());
        contactCallNowFragment.search(string, binding.vpMainContact.getCurrentItem());
    }

    public void reloadVp(){
        contactCallNowFragment.getAllContact();
        contactAllFragment.getAllContact();
    }

    private void handleDoneSearch(){
        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(binding.edtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    public void refreshCallNow(){
        contactCallNowFragment.refreshCallNow();
    }


}
