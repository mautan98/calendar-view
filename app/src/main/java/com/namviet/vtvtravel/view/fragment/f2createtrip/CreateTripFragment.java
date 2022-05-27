package com.namviet.vtvtravel.view.fragment.f2createtrip;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.FragmentTransaction;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentCreateTripBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.view.f2.DisplayMarkerForMapActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionMainFragment;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


public class CreateTripFragment extends BaseFragment<F2FragmentCreateTripBinding> implements ChooseRegionMainFragment.ChooseRegion, Observer {
    private String link = WSConfig.HOST_CREATE_TRIP;
    private ArrayList<Location> locationList = new ArrayList<>();
    private SearchBigLocationViewModel locationViewModel;
    Map<String, String> extraHeaders = new HashMap<>();


    public CreateTripFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_create_trip;
    }

    @Override
    public void initView() {
        locationViewModel = new SearchBigLocationViewModel();
        locationViewModel.addObserver(this);
        locationViewModel.getAllLocation();

    }

    @Override
    public void initData() {
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().edtStartPlace.setOnClickListener(v -> {
            if (locationList.size() > 0) {
                ChooseRegionMainFragment chooseRegionMainFragment = new ChooseRegionMainFragment();
                chooseRegionMainFragment.setData(locationList, CreateTripFragment.this);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.rlt_main, chooseRegionMainFragment,ChooseRegionMainFragment.class.getSimpleName()).addToBackStack(ChooseRegionMainFragment.class.getSimpleName()).commit();
            }
        });
    }

    @Override
    public void setObserver() {

    }

    private String genLink() {
        try {
            Double lat = null;
            Double lng = null;
            if (null != MyApplication.getInstance().getMyLocation()
                    && MyApplication.getInstance().getMyLocation().getLat() != MyApplication.getInstance().getMyLocation().getLog()) {
                lat = MyApplication.getInstance().getMyLocation().getLat();
                lng = MyApplication.getInstance().getMyLocation().getLog();
            }
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin() && lat != null && lng != null) {
                extraHeaders.put("token",account.getToken());
                return link + "?lat=" + lat + "&long=" + lng + "&backLink=" + "true";
            } else if (null != account && account.isLogin()) {
                extraHeaders.put("token",account.getToken());
                return link + "?backLink=" + "true";
            } else {
                return link + "?backLink=" + "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return link;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void clickRegion(@Nullable Location location) {
        if (location != null) {
            getBinding().edtStartDate.setText(location.getName());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SearchBigLocationViewModel) {
            if (arg instanceof AllLocationResponse) {
                AllLocationResponse locationResponse = (AllLocationResponse) arg;
                if (locationResponse.getData() != null)
                    locationList = (ArrayList<Location>) locationResponse.getData();
            }
        }
    }
}
