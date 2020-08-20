package com.namviet.vtvtravel.f2base.base;

import android.content.Context;
import android.view.View;

public interface BaseView {

    void addFragment(BaseFragment fragment);

    void addFragmentWithTag(BaseFragment fragment, String tag);

    void showLoading();

    void hideLoading();

    void hideKeyboard(Context context, View view);

    void onRequestFailure(String message);

    void onNetworkError();

    void onCannotDetectLocation();

    void showToast(String mess);

    void logE(String mess);

    void showKeyBoard(View view);
}
