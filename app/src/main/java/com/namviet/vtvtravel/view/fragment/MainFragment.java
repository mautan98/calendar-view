package com.namviet.vtvtravel.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baseapp.fragment.BaseFragment;
import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelFactory;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.ItemNotify;
import com.namviet.vtvtravel.response.CountUnreadRespone;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.dialog.LoadingDialog;
import com.namviet.vtvtravel.view.fragment.f2service.Service;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;

import java.io.File;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements View.OnClickListener {

    protected MainActivity mActivity;
    private LoadingDialog mLoadingDialog;
    private TextView tvCountUnread;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(mActivity instanceof ServiceActivity) {
            this.mActivity = (ServiceActivity) context;
        }else {
            this.mActivity = (MainActivity) context;
        }
    }

    protected void initViews(View v) {

    }

    protected void updateViews() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.KeyBroadcast.KEY_COUNT_UNREAD);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, intentFilter);
    }

    public void setTvCountUnread(TextView tvCountUnread) {
        this.tvCountUnread = tvCountUnread;
        getCountUnread();
    }

    public void setCountUnRead(int count) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != tvCountUnread && null != account && account.isLogin()) {
            if (count > 0) {
                tvCountUnread.setVisibility(View.VISIBLE);
                if (count > 100) {
                    tvCountUnread.setText("99+");
                } else {
                    tvCountUnread.setText("" + count);
                }
            } else {
                tvCountUnread.setVisibility(View.INVISIBLE);
            }
        } else if (null != tvCountUnread){
            tvCountUnread.setVisibility(View.INVISIBLE);
        }
    }

    private void getCountUnread() {
        Map<String, Object> queryMap = Param.getListNotify(DeviceUtils.getDeviceId(getContext()));
        TravelService apiService = TravelFactory.createService(TravelService.class);
        Call<CountUnreadRespone> call = apiService.getCountUnread(queryMap);
        call.enqueue(new Callback<CountUnreadRespone>() {
            @Override
            public void onResponse(Call<CountUnreadRespone> call, Response<CountUnreadRespone> response) {
                if (null != response.body() && null != response.body().getData()) {
                    setCountUnRead(response.body().getData().getCount());
                }
            }

            @Override
            public void onFailure(Call<CountUnreadRespone> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivSearch) {
            mActivity.switchFragment(SlideMenu.MenuType.SEARCH_SCREEN);
            goToSearch();
        }
    }

    public class ClickHandler {
        public void onClickView(View view) {
        }
    }

    protected void goToSearch() {
        int size = mActivity.getStackMap().get(mActivity.getmCurrentMenuTab()).size();
        if (size >= 2) {
            for (int i = size - 2; i >= 1; i--) {
                mActivity.getStackMap().get(mActivity.getmCurrentMenuTab()).removeElementAt(i);
            }
        }
    }

    public void switchFragment(SlideMenu.MenuType screen) {
    }

    public int getContentFrame() {
        return 0;
    }

    public void showMessage(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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

    public void createSuccess(String message, int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder
                .setMessage("" + message)
                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.IntentKey.KEY_POSITION, position);
                        mActivity.setBundle(bundle);
                        mActivity.switchFragment(SlideMenu.MenuType.ACCOUNT_SCREEN);
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(getContext()).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);

    }

    public void setImageFile(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(getContext()).setDefaultRequestOptions(requestOptions).load(new File(ulrCs)).thumbnail(0.2f).into(image);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    public void goToNotifyScreen() {
//        mActivity.switchFragment(SlideMenu.MenuType.NOTIFY_SCREEN);
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            mActivity.switchFragment(SlideMenu.MenuType.NOTIFY_SCREEN);
        } else {
            mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
        }
    }

    protected void setUnreadTv(TextView tvCount, int count) {
        if (count > 99) {
            tvCount.setText("99+");
        } else if (count == 0) {
            tvCount.setVisibility(View.INVISIBLE);
        } else {
            tvCount.setText("" + count);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.KeyBroadcast.KEY_COUNT_UNREAD)) {
                int count = intent.getIntExtra(Constants.IntentKey.KEY_COUNT_UNREAD, 0);
                setCountUnRead(count);
            }
        }

    };
}
