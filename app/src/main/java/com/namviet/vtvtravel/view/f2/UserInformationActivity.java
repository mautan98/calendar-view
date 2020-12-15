package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityUserInformationBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2userinformation.UserInformationFragment;

import java.util.List;

public class UserInformationActivity extends BaseActivityNew<F2ActivityUserInformationBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_user_information;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {
    }

    @Override
    public BaseFragment initFragment() {
        return new UserInformationFragment();
    }

    public static void openScreen(Context activity) {
        Intent intent = new Intent(activity, UserInformationActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
