package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityOperatorInfomationBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2opeator.OperatorFragment;

public class OperatorInformationActivity extends BaseActivityNew<F2ActivityOperatorInfomationBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_operator_infomation;
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
        return new OperatorFragment();
    }


    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, OperatorInformationActivity.class);
        activity.startActivity(intent);
    }
}
