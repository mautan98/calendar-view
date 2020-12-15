package com.namviet.vtvtravel.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.view.MainActivity;

public class BaseDialogFragment extends DialogFragment implements View.OnClickListener {
    protected MainActivity mActivity;
    private LoadingDialog mLoadingDialog;

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics display = getResources().getDisplayMetrics();
        int width = display.widthPixels;
        if (null != getDialog()) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

//        @Override
//    public int show(FragmentTransaction transaction, String tag) {
//        return super.show(transaction, tag);
//    }

    @Override
    public void show(FragmentManager manager, String tag) {
//        super.show(manager, tag);
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View view) {

    }

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(getContext()).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);

    }

    public void showDialogLoading() {
        mLoadingDialog = LoadingDialog.newInstance();
        mLoadingDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
        mLoadingDialog.setCancelable(false);
    }

    public void dimissDialogLoading() {
        if (null != mLoadingDialog) {
            mLoadingDialog.dismissAllowingStateLoss();
        }
    }

    public void showMessage(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder
                .setMessage("" + message)
                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
