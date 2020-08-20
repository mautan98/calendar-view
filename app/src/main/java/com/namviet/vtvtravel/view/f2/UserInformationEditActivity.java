package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityUserInformationEditBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2userinformation.UserInformationEditFragment;

public class UserInformationEditActivity extends BaseActivityNew<F2ActivityUserInformationEditBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_user_information_edit;
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
        return new UserInformationEditFragment();
    }
    public static void openScreen(Context activity) {
        Intent intent = new Intent(activity, UserInformationEditActivity.class);
        activity.startActivity(intent);
    }
}
