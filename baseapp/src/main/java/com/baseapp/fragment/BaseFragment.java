package com.baseapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.baseapp.utils.KeyboardUtils;


/**
 * Created by xuannt on 8/8/2017.
 */

public class BaseFragment extends Fragment {
    protected View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            view = inflater.inflate(layoutId, null);
        } else {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        //Click outside hide keyboard...
        outSideHideScreen(view);
        return view;
    }

    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    protected void initViews(View view) {

    }

    protected void updateViews() {

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void outSideHideScreen(final View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
//                    KeyboardUtils.hideKeyboard(getActivity(), view);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                outSideHideScreen(innerView);
            }
        }
    }
}
