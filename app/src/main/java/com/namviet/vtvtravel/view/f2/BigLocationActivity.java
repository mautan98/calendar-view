package com.namviet.vtvtravel.view.f2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.test.mock.MockPackageManager;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityBigLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.service.TrackLocationService;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.ultils.ServiceUltils;
import com.namviet.vtvtravel.view.fragment.f2biglocation.BigLocationFragment;
import com.namviet.vtvtravel.view.fragment.f2biglocation.SearchBigLocationFragment;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.ankit.gpslibrary.ADLocation;
import org.ankit.gpslibrary.MyTracker;

public class BigLocationActivity extends BaseActivityNew<F2ActivityBigLocationBinding> {
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private boolean openSearch;
    private String regionId;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_big_location;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        regionId = getIntent().getStringExtra(Constants.IntentKey.DATA);
        openSearch = getIntent().getBooleanExtra(Constants.IntentKey.OPEN_SEARCH, false);
    }

    @Override
    public void doAfterOnCreate() {
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != MockPackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);
            } else {
//                getLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        if (openSearch) {
            return new SearchBigLocationFragment();
        }else {
            return new BigLocationFragment(regionId);
        }
    }

    public static void startScreen(Activity activity, String regionId, boolean openSearch){
        Intent intent = new Intent(activity, BigLocationActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, regionId);
        intent.putExtra(Constants.IntentKey.OPEN_SEARCH, openSearch);
        activity.startActivity(intent);
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

//        try {
//            List<Fragment> fragments = getSupportFragmentManager().getFragments();
//            if (fragments != null) {
//                for (Fragment fragment : fragments) {
//                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void getLocation() {
        MyTracker tracker = new MyTracker(this, new MyTracker.ADLocationListener() {
            @Override
            public void whereIAM(ADLocation loc) {
                try {
                    PreferenceUtil.getInstance(BigLocationActivity.this).setValue(Constants.PrefKey.LAT_LOCATION, "" + loc.getLat());
                    PreferenceUtil.getInstance(BigLocationActivity.this).setValue(Constants.PrefKey.LNG_LOCATION, "" + loc.getLng());

                    MyApplication.getInstance().setMyLocation(new MyLocation(loc.getCity(), loc.getAddress(), loc.getCountry(), loc.getLat(), loc.getLng()));
                    BaseViewModel baseViewModel = new BaseViewModel();
                    baseViewModel.trackLocation(loc.getLat(), loc.getLng(), DeviceUtils.getDeviceId(BigLocationActivity.this));
                    if (!ServiceUltils.isMyServiceRunning(BigLocationActivity.this, TrackLocationService.class)) {
                        Intent intent = new Intent(BigLocationActivity.this, TrackLocationService.class);
                        startService(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tracker.track();
//        searchViewModel.loadSearchTrend();
    }
}
