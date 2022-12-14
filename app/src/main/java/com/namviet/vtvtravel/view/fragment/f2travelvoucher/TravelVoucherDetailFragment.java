package com.namviet.vtvtravel.view.fragment.f2travelvoucher;

import android.annotation.SuppressLint;
import android.view.View;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelvoucher.TravelVoucherAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentTravelVoucherDetailBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.f2.VQMMWebviewActivity;
import com.namviet.vtvtravel.view.fragment.f2offline.CopyVoucherDialog;
import com.namviet.vtvtravel.viewmodel.f2travelvoucher.TravelVoucherViewModel;

import java.util.Observable;
import java.util.Observer;

public class TravelVoucherDetailFragment extends BaseFragment<F2FragmentTravelVoucherDetailBinding> implements Observer {
    private TravelVoucherAdapter travelVoucherAdapter;
    private ListVoucherResponse.Data.Voucher voucher;
    private boolean isStore;
    private boolean isFromRegVip;
    private TravelVoucherViewModel travelVoucherViewModel;

    @SuppressLint("ValidFragment")
    public TravelVoucherDetailFragment(ListVoucherResponse.Data.Voucher voucher, boolean isStore, boolean isFromRegVip) {
        this.voucher = voucher;
        this.isStore = isStore;
        this.isFromRegVip = isFromRegVip;
    }

    public TravelVoucherDetailFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_travel_voucher_detail;
    }

    @Override
    public void initView() {
        travelVoucherViewModel = new TravelVoucherViewModel();
        travelVoucherViewModel.addObserver(this);


        if (isStore) {
            if (isFromRegVip) {
                getBinding().btnSend.setText("Nh???n th?????ng ngay");
            } else {
                getBinding().btnSend.setText("Quay ????? nh???n th?????ng");
            }
        } else {
            getBinding().btnSend.setText("D??ng ngay");
        }
    }

    @Override
    public void initData() {
        try {
            if(voucher.getBannerUri() == null || (voucher.getBannerUri() != null && voucher.getBannerUri().isEmpty())){
                Glide.with(mActivity).load(voucher.getHomeUri()).into(getBinding().imgAvatar);
            }else {
                Glide.with(mActivity).load(voucher.getBannerUri()).into(getBinding().imgAvatar);
            }
        } catch (Exception e) {
        }
        getBinding().tvName.setText(voucher.getName());
        try {
            getBinding().tvTimeLeft.setText("H???n ?????n " + DateUtltils.timeToString(voucher.getExpireDate() / 1000));
        } catch (Exception e) {
            getBinding().imgTime.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStore) {
                    if (isFromRegVip) {
                        showLoading();
                        travelVoucherViewModel.checkUserReceiver(voucher.getId(), "APP", "Android", "VQMM");
                        try {
                            TrackingAnalytic.postEvent(TrackingAnalytic.CLICK_RECEIVE_PROMOTION, TrackingAnalytic.getDefault("TravelVoucherDetail", "Chi ti???t voucher").setScreen_class(this.getClass().getName()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Account account = MyApplication.getInstance().getAccount();
                        if (null != account && account.isLogin()) {
                            VQMMWebviewActivity.startScreen(mActivity, voucher.getId());
                        } else {
                            LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                        }

                    }
                } else {
                    CopyVoucherDialog copyVoucherDialog = CopyVoucherDialog.newInstance(voucher, null);
                    copyVoucherDialog.show(mActivity.getSupportFragmentManager(), null);
                }
            }
        });

        getBinding().webView.loadDataWithBaseURL("", voucher.getContent(), "text/html", "UTF-8", null);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof TravelVoucherViewModel && null != o) {
            if (o instanceof BaseResponse) {
                BaseResponse baseResponse = (BaseResponse) o;
                ReceiverGiftSuccessDialog receiverGiftSuccessDialog = ReceiverGiftSuccessDialog.newInstance(new ReceiverGiftSuccessDialog.ClickButton() {
                    @Override
                    public void onClickButton() {
                        TravelVoucherActivity.openScreen(mActivity, false, TravelVoucherActivity.OpenType.LIST, false);
                    }
                });
                receiverGiftSuccessDialog.show(mActivity.getSupportFragmentManager(), null);

            } else if (o instanceof ErrorResponse) {
                try {
                    ErrorResponse responseError = (ErrorResponse) o;
                    if (responseError.getCodeToSplitCase().equals("checkUserReceiver")) {
                        NotifiDialog notifiDialog = NotifiDialog.newInstance("", responseError.getErrorCode(), "");
                        notifiDialog.show(mActivity.getSupportFragmentManager(), null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.VOUCHER_DETAIL, TrackingAnalytic.ScreenTitle.VOUCHER_DEATAIL);
    }
}
