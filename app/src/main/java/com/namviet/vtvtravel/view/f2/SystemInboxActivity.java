package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivitySystemInboxBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.SystemInboxFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog;

public class SystemInboxActivity extends BaseActivityNew<F2ActivitySystemInboxBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_system_inbox;
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
        return new SystemInboxFragment();
    }

    public static void startScreen(Context activity) {
//        Intent intent = new Intent(activity, SystemInboxActivity.class);
//        activity.startActivity(intent);
        try {
            OneButtonTitleImageDialog oneButtonTitleImageDialog = new OneButtonTitleImageDialog();
            oneButtonTitleImageDialog.show(((MainActivity)activity).getSupportFragmentManager(), Constants.TAG_DIALOG);
        } catch (Exception e) {
            try {
                OneButtonTitleImageDialog oneButtonTitleImageDialog = new OneButtonTitleImageDialog();
                oneButtonTitleImageDialog.show(((BaseActivityNew)activity).getSupportFragmentManager(), Constants.TAG_DIALOG);
            } catch (Exception exception) {

            }

        }
    }
}
