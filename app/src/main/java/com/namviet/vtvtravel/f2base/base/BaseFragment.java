package com.namviet.vtvtravel.f2base.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;


public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment implements BaseView {
    public abstract int getLayoutRes();


    public abstract void initView();

    public abstract void initData();

    public abstract void inject();

    public abstract void setClickListener();

    public abstract void setObserver();


    protected BaseActivityNew mActivity;
    protected View rootView;

    private T binding;

    private ProgressDialog mLoadingDialog;

    public String screenTitle;
    public String screenCode;

    public void setScreenTitle(){

    }

    public void setDataScreen(String screenCode, String screenTitle){
        this.screenCode = screenCode;
        this.screenTitle = screenTitle;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (!(getActivity() instanceof BaseActivityNew)) {
                new Throwable("Activity no override BaseActivity");
            }
            mActivity = (BaseActivityNew) getActivity();
            inject();
            Log.d("LamLV: ", this.getClass().getName());
            setScreenTitle();
            try {
                TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(screenCode, screenTitle).setScreen_class(this.getClass().getName()));
            } catch (Exception e) {

            }
        } catch (Exception e) {
            Log.e("exception", e.getMessage() + "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            bindView(inflater, container, getLayoutRes());
        } catch (Exception e) {
            Log.e("exception", e.getMessage() + "");
        }
        return binding.getRoot();
    }

    public void bindView(LayoutInflater inflater, ViewGroup viewGroup, int res) {
        binding = DataBindingUtil.inflate(
                inflater, res, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);
            initView();
            setClickListener();
            initData();
            setObserver();
        } catch (Exception e) {
            Log.e("exception", e.getMessage() + "");
        }
    }


    @Override
    public void addFragment(BaseFragment fragment) {
        try {
            try {
                if(mActivity instanceof  LoginAndRegisterActivityNew){
                    ((LoginAndRegisterActivityNew) mActivity).hideWarning();
                    KeyboardUtils.hideKeyboard(mActivity, getBinding().getRoot());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager frm = mActivity.getSupportFragmentManager();
            frm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.mainFrame, fragment)
                    .addToBackStack(fragment.getClass().getSimpleName()).commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void addFragmentWithTag(BaseFragment fragment, String tag) {
        try {
            FragmentManager frm = mActivity.getSupportFragmentManager();
            frm.beginTransaction()
                    .add(R.id.mainFrame, fragment, tag)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        } catch (Exception e) {
        }
    }


    @Override
    public void showLoading() {
        try {
            mLoadingDialog = new ProgressDialog(mActivity);
            mLoadingDialog.setCancelable(true);
            mLoadingDialog.setMessage("Đang tải...");
            mLoadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void hideLoading() {
        try {
            if (null != mLoadingDialog) {
                mLoadingDialog.dismiss();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideKeyboard(Context context, View view) {
        KeyboardUtils.hideKeyboard(context, view);
    }

    @Override
    public void onRequestFailure(String message) {

    }

    @Override
    public void showToast(String mess) {
        F2Util.showToast(mActivity, mess);
    }


    @Override
    public void logE(String mess) {

    }

    @Override
    public void showKeyBoard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onNetworkError() {
        // Show dialog thong báo
//        OmiAlertDialog dialog = OmiAlertDialog.newInstance(getContext(), "Error", "No internet connected");
//        dialog.show(getFragmentManager(), null);
    }


    public void onCannotDetectLocation() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public T getBinding() {
        return binding;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
