package com.namviet.vtvtravel.view.fragment.f2account;

import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2FragmentRulesBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2account.HtmlResponse;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.f2account.AccountViewModel;

import java.util.Observable;
import java.util.Observer;

public class RulesFragment extends BaseFragment<F2FragmentRulesBinding> implements Observer {
    private AccountViewModel accountViewModel;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_rules;
    }

    @Override
    public void initView() {
        accountViewModel = new AccountViewModel();
        accountViewModel.addObserver(this);
        accountViewModel.getUsageRule();
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
}
