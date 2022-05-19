package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import android.view.View;

import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityLoginAndRegisterBinding;
import com.namviet.vtvtravel.databinding.F2FragmentPageMainLoginBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2account.MainPageLoginAndRegisterFragment;

public class LoginAndRegisterActivityNew extends BaseActivityNew<F2ActivityLoginAndRegisterBinding> {
    public boolean isFromButtonCallNow;
    public boolean isFromBooking;
    public boolean isFromDeal;

    private int position;
    public String packageCode = Constants.TypePackage.TRAVEL_VIP;

    public String fullName = "";
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_login_and_register;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        isFromButtonCallNow = getIntent().getBooleanExtra(Constants.IntentKey.IS_FROM_BUTTON_CALL_NOW, false);
        isFromBooking = getIntent().getBooleanExtra(Constants.IntentKey.IS_FROM_BOOKING, false);
        isFromDeal = getIntent().getBooleanExtra(Constants.IntentKey.IS_FROM_DEAL, false);
        position = getIntent().getIntExtra(Constants.IntentKey.KEY_POSITION, 0);
    }

    @Override
    public void doAfterOnCreate() {
    }

    @Override
    public void setClick() {
        getBinding().btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().layoutWarning.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public BaseFragment<F2FragmentPageMainLoginBinding> initFragment() {
        MainPageLoginAndRegisterFragment mainPageLoginAndRegisterFragment = new MainPageLoginAndRegisterFragment();
        mainPageLoginAndRegisterFragment.setPosition(position);
        return mainPageLoginAndRegisterFragment;
    }

    public void showWarning(String message){
        try {
            if(message == null || (message != null && message.isEmpty())){
                getBinding().tvWarningMessage.setText("Đã xảy ra lỗi, xin quý khách hãy kiểm tra lại thông tin và thử lại sau!");
            }else {
                getBinding().tvWarningMessage.setText(message);
            }
            getBinding().layoutWarning.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startScreen(Context activity, int position, boolean isFromButtonCallNow){
        Intent intent = new Intent(activity, LoginAndRegisterActivityNew.class);
        intent.putExtra(Constants.IntentKey.POSITION_LOGIN, position);
        intent.putExtra(Constants.IntentKey.KEY_POSITION, position);
        intent.putExtra(Constants.IntentKey.IS_FROM_BUTTON_CALL_NOW, isFromButtonCallNow);
        activity.startActivity(intent);
    }

    public static void startScreen(Context activity, int position, boolean isFromButtonCallNow, boolean isFromBooking){
        Intent intent = new Intent(activity, LoginAndRegisterActivityNew.class);
        intent.putExtra(Constants.IntentKey.POSITION_LOGIN, position);
        intent.putExtra(Constants.IntentKey.KEY_POSITION, position);
        intent.putExtra(Constants.IntentKey.IS_FROM_BUTTON_CALL_NOW, isFromButtonCallNow);
        intent.putExtra(Constants.IntentKey.IS_FROM_BOOKING, isFromBooking);
        activity.startActivity(intent);
    }

    public static void startScreen(Context activity, int position, boolean isFromButtonCallNow, boolean isFromBooking, boolean isFromDealAndHaveBackLink){
        Intent intent = new Intent(activity, LoginAndRegisterActivityNew.class);
        intent.putExtra(Constants.IntentKey.POSITION_LOGIN, position);
        intent.putExtra(Constants.IntentKey.KEY_POSITION, position);
        intent.putExtra(Constants.IntentKey.IS_FROM_BUTTON_CALL_NOW, isFromButtonCallNow);
        intent.putExtra(Constants.IntentKey.IS_FROM_DEAL, isFromDealAndHaveBackLink);
        intent.putExtra(Constants.IntentKey.IS_FROM_BOOKING, isFromBooking);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        try {
            if(isCreatePassOnTop()){
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
        getBinding().layoutWarning.setVisibility(View.GONE);

    }


    public Fragment getTopFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        if (fragmentTag.equals("after OTP")) {
            return getSupportFragmentManager().findFragmentByTag(fragmentTag);
        } else {
            return null;
        }

    }

    public boolean isCreatePassOnTop(){
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                return false;
            }
            String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            if (fragmentTag.equals("RecreatePassF2Fragment")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void hideWarning(){
        getBinding().layoutWarning.setVisibility(View.GONE);
    }
}
