package com.namviet.vtvtravel.view.fragment.f2introduction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.test.mock.MockPackageManager;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2introduction.IntroductionAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentIntroductionBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.service.TrackLocationService;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.ultils.ServiceUltils;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.ankit.gpslibrary.ADLocation;
import org.ankit.gpslibrary.MyTracker;

public class IntroductionFragment extends BaseFragment<F2FragmentIntroductionBinding> {
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private IntroductionAdapter introductionAdapter;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_introduction;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        introductionAdapter = new IntroductionAdapter(mActivity);
        getBinding().vpContent.setAdapter(introductionAdapter);
        getBinding().vpIndicator.attachToViewPager(getBinding().vpContent);


        try {
            if (ContextCompat.checkSelfPermission(mActivity, mPermission) != MockPackageManager.PERMISSION_GRANTED) {
                requestPermissions( new String[]{mPermission, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);
            } else {
//                getLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.startActivity(new Intent(mActivity, MainActivity.class));
                mActivity.finish();
            }
        });

//        getBinding().btnContinue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(getBinding().vpContent.getCurrentItem() == 2){
//                    mActivity.startActivity(new Intent(mActivity, MainActivity.class));
//                    mActivity.finish();
//                }else {
//                    getBinding().vpContent.setCurrentItem(getBinding().vpContent.getCurrentItem() + 1);
//                }
//            }
//        });
    }

    @Override
    public void setObserver() {

    }

    public void getLocation() {
        MyTracker tracker = new MyTracker(mActivity, new MyTracker.ADLocationListener() {
            @Override
            public void whereIAM(ADLocation loc) {
                try {
                    PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.LAT_LOCATION, "" + loc.getLat());
                    PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.LNG_LOCATION, "" + loc.getLng());

                    MyApplication.getInstance().setMyLocation(new MyLocation(loc.getCity(), loc.getAddress(), loc.getCountry(), loc.getLat(), loc.getLng()));
                    BaseViewModel baseViewModel = new BaseViewModel();
                    baseViewModel.trackLocation(loc.getLat(), loc.getLng(), DeviceUtils.getDeviceId(mActivity));
                    if (!ServiceUltils.isMyServiceRunning(mActivity, TrackLocationService.class)) {
                        Intent intent = new Intent(mActivity, TrackLocationService.class);
                        mActivity.startService(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tracker.track();
//        searchViewModel.loadSearchTrend();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {

                }
                break;
        }

    }
}
