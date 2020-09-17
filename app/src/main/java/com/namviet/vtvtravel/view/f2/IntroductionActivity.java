package com.namviet.vtvtravel.view.f2;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityIntroductionBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2introduction.IntroductionFragment;

public class IntroductionActivity extends BaseActivityNew<F2ActivityIntroductionBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_introduction;
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
        return new IntroductionFragment();
    }
}
