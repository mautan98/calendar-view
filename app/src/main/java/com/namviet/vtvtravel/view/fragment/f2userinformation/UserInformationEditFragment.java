package com.namviet.vtvtravel.view.fragment.f2userinformation;

import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2FragmentPersonalInformationEditBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;

public class UserInformationEditFragment extends BaseFragment<F2FragmentPersonalInformationEditBinding> implements View.OnClickListener, GenderDialog.Gender {
    private boolean checkMergeInfo = false;
    private boolean checkMergePaper = false;
    private boolean checkMergeBill = false;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_personal_information_edit;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().txtDone.setOnClickListener(this);
        getBinding().btnBack.setOnClickListener(this);
        getBinding().imgMergePaper.setOnClickListener(this);
        getBinding().tvGender.setOnClickListener(this);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDone:
                mActivity.finish();
                break;
            case R.id.btnBack:
                mActivity.finish();
                break;
            case R.id.imgMergeInfo:
                if (checkMergeInfo) {
                    getBinding().layoutInfo.setVisibility(View.VISIBLE);
                    getBinding().imgMergeInfo.setRotation(0);
                } else {
                    getBinding().layoutInfo.setVisibility(View.GONE);
                    getBinding().imgMergeInfo.setRotation(180);
                }
                checkMergeInfo = !checkMergeInfo;
                break;
            case R.id.tvGender:
                GenderDialog genderDialog = new GenderDialog(this);
                genderDialog.show(getChildFragmentManager(), null);
                break;
        }
    }

    private void visibleLayout() {
        getBinding().imgMergeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkMergeInfo) {
                    getBinding().layoutInfo.setVisibility(View.VISIBLE);
                    getBinding().imgMergeInfo.setRotation(0);
                } else {
                    getBinding().layoutInfo.setVisibility(View.GONE);
                    getBinding().imgMergeInfo.setRotation(-90);
                }

                checkMergeInfo = !checkMergeInfo;
            }
        });
        getBinding().imgMergePaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkMergePaper) {
                    getBinding().layoutPaper.setVisibility(View.VISIBLE);
                    getBinding().imgMergePaper.setRotation(0);
                } else {
                    getBinding().layoutPaper.setVisibility(View.GONE);
                    getBinding().imgMergePaper.setRotation(-90);
                }
                checkMergePaper = !checkMergePaper;
            }
        });
        getBinding().imgMergeBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkMergeBill) {
                    getBinding().layoutBill.setVisibility(View.VISIBLE);
                    getBinding().imgMergeBill.setRotation(0);
                } else {
                    getBinding().layoutBill.setVisibility(View.GONE);
                    getBinding().imgMergeBill.setRotation(-90);
                }
                checkMergeBill = !checkMergeBill;
            }
        });
    }

    @Override
    public void clickGender(String gender) {
        try {
            getBinding().tvGender.setText(gender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.USER_INFO, TrackingAnalytic.ScreenTitle.USER_INFO);
    }

}
