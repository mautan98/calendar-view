package com.namviet.vtvtravel.view.fragment.f2account;

import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentDetailDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToBooking;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToCallNow;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndReloadDeal;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.model.f2event.OnUpdateLogin;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2account.HtmlResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.f2account.AccountViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.Observable;
import java.util.Observer;

public class DetailDealFragment extends BaseFragment<F2FragmentDetailDealBinding> implements Observer {
    private AccountViewModel accountViewModel;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_deal;
    }

    @Override
    public void initView() {
        accountViewModel = new AccountViewModel();
        accountViewModel.addObserver(this);
        accountViewModel.getDealDetail();
    }

    @Override
    public void initData() {

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
    public void update(Observable observable, Object o) {
        if (observable instanceof AccountViewModel && null != o) {
            if (o instanceof HtmlResponse) {
                HtmlResponse htmlResponse = (HtmlResponse) o;
                getBinding().webView.loadDataWithBaseURL("", htmlResponse.getData().getDescription(), "text/html", "UTF-8", null);
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        screenCode = TrackingAnalytic.ScreenCode.DEAL_DETAIL;
        screenTitle = TrackingAnalytic.ScreenTitle.DEAL_DETAIL;
    }
}
