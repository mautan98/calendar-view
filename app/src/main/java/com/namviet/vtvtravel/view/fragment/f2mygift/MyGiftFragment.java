package com.namviet.vtvtravel.view.fragment.f2mygift;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.mygift.ChildrenMenuAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentMyGiftBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.fragment.f2service.GetInfoResponse;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceResponse;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MyGiftFragment extends BaseFragment<F2FragmentMyGiftBinding> implements Observer {
    private ChildrenMenuAdapter childrenMenuAdapter;
    private String title;
    private ServiceViewModel serviceViewModel;

    @SuppressLint("ValidFragment")
    public MyGiftFragment(List<MenuItem> menuItems, String title) {
        this.menuItems = menuItems;
        this.title = title;
    }

    public MyGiftFragment() {
    }

    private List<MenuItem> menuItems = new ArrayList<>();

    //    private
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_my_gift;
    }

    @Override
    public void initView() {
        serviceViewModel = new ServiceViewModel(getContext());
        serviceViewModel.addObserver(this);
    }

    @Override
    public void initData() {
        getBinding().tvTitle.setText(title);
        childrenMenuAdapter = new ChildrenMenuAdapter(menuItems, mActivity, new ChildrenMenuAdapter.ClickItem() {
            @Override
            public void onClickItem() {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    String code = String.valueOf(MyApplication.getInstance().getAccount().getId());
                    String token = String.valueOf(MyApplication.getInstance().getAccount().getToken());
                    serviceViewModel.getInfo(code, token);
                }

            }
        });
        getBinding().rclContent.setAdapter(childrenMenuAdapter);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ServiceViewModel && arg != null) {
            if (arg instanceof GetInfoResponse) {
                GetInfoResponse getInfoResponse = (GetInfoResponse) arg;
                if (getInfoResponse.getData().getUser().getPackageCode() != null && getInfoResponse.getData().getUser().getPackageCode().equals("TRAVEL_VIP")) {
                    TravelVoucherActivity.openScreen(mActivity, false);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setMessage("Mời đăng ký gói VIP để tận hưởng ưu đãi từ VTV Travel");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ServiceActivity.startScreen(mActivity);
                        }
                    });
                    builder.setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create();
                    builder.show();
                }
            } else if (arg instanceof ResponseError) {
                ResponseError responseError = (ResponseError) arg;
            }
        }
    }
}
