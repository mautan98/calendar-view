package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.baseapp.activity.BaseActivity;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityRuleBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2account.RulesFragment;

public class RuleActivity extends BaseActivityNew<F2ActivityRuleBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_rule;
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
        return new RulesFragment();
    }

    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, RuleActivity.class);
        activity.startActivity(intent);
    }
}
