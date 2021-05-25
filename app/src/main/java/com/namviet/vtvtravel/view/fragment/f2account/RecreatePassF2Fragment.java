package com.namviet.vtvtravel.view.fragment.f2account;

import android.annotation.SuppressLint;
import androidx.databinding.DataBindingUtil;
import androidx.core.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentRecreatePassBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnReloadCountSystemInbox;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecreatePassF2Fragment extends BaseFragment<F2FragmentRecreatePassBinding> implements Observer {

    private AccountViewModel accountViewModel;
    private String screenType;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_recreate_pass;
    }

    @Override
    public void initView() {
        accountViewModel = new AccountViewModel();
        getBinding().setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);

        handleValidateSuccess(getBinding().edtPassword, getBinding().linearPassword);
        handleValidateSuccess(getBinding().edtPassword2, getBinding().linearPassword2);
    }

    @Override
    public void initData() {
        if (screenType.equals(Constants.IntentKey.TYPE_OTP_RESET_PASS)) {
            getBinding().tvTitle.setText("Tạo lại mật khẩu");
        } else {
            getBinding().tvTitle.setText("Tạo mật khẩu");
        }

        getBinding().imgEyeCovered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                getBinding().imgEyeCovered.setVisibility(View.GONE);
                getBinding().imgEye.setVisibility(View.VISIBLE);
            }
        });

        getBinding().imgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                getBinding().imgEyeCovered.setVisibility(View.VISIBLE);
                getBinding().imgEye.setVisibility(View.GONE);
            }
        });

        getBinding().imgEyeCovered2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().edtPassword2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                getBinding().imgEyeCovered2.setVisibility(View.GONE);
                getBinding().imgEye2.setVisibility(View.VISIBLE);
            }
        });

        getBinding().imgEye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().edtPassword2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                getBinding().imgEyeCovered2.setVisibility(View.VISIBLE);
                getBinding().imgEye2.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(view -> {
            mActivity.onBackPressed();
            mActivity.onBackPressed();
        });

        getBinding().btnConfirm.setOnClickListener(v -> {
            resetError();
            String pass1 = getBinding().edtPassword.getText().toString();
            String pass2 = getBinding().edtPassword2.getText().toString();

            if (pass1.isEmpty()) {
                handleValidateFail(getBinding().edtPassword, getBinding().linearPassword, "Mật khẩu không được để trống", getBinding().tvPassError);
            } else if (pass2.isEmpty()) {
                handleValidateFail(getBinding().edtPassword2, getBinding().linearPassword2, "Xác nhận mật khẩu không được để trống", getBinding().tvRetypePassError);
            } else if (!pass1.equals(pass2)) {
                handleValidateFail(getBinding().edtPassword2, getBinding().linearPassword2, "Xác nhận mật khẩu phải trùng với thông tin mật khẩu", getBinding().tvRetypePassError);
            } else if (pass1.length() < 6) {
                handleValidateFail(getBinding().edtPassword, getBinding().linearPassword, "Mật khẩu không được nhỏ hơn 6 ký tự", getBinding().tvPassError);
            } else {
                Integer id = MyApplication.getInstance().getAccount().getId();
                String mobile = MyApplication.getInstance().getAccount().getMobile();
                String token = MyApplication.getInstance().getAccount().getToken();
                if (screenType.equals(Constants.IntentKey.TYPE_OTP_REGISTER)) {
                    accountViewModel.setPassRegister(id, mobile, pass1, ((LoginAndRegisterActivityNew) mActivity).packageCode);
                } else {
                    accountViewModel.setPassReset(id, mobile, pass1, token);
                }

                showLoading();
            }
        });
    }

    @Override
    public void setObserver() {

    }


    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
//        if (observable instanceof AccountViewModel && null != o) {
//            if (o instanceof AccountResponse) {
//                AccountResponse accountResponse = (AccountResponse) o;
//
//            } else if (o instanceof ResponseError) {
//                ResponseError responseError = (ResponseError) o;
//                logE(responseError.getMessage());
//            }
//
//        }

        if (observable instanceof AccountViewModel) {
            if (null != o) {
                if (o instanceof AccountResponse) {
                    AccountResponse accountResponse = (AccountResponse) o;
                    if (accountResponse.isSuccess()) {
                        MyApplication.getInstance().setAccount(accountResponse.getData());
                        /*mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);*/
                        ;
                        if (screenType.equals(Constants.IntentKey.TYPE_OTP_RESET_PASS)) {
                            showToast("Thực hiện cấp lại mật khẩu thành công, mời bạn đăng nhập!");
                        } else {
                            showToast("Đăng ký thành công, mời bạn đăng nhập!");
                        }

                        EventBus.getDefault().post(new OnReloadCountSystemInbox());

                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                        mActivity.finish();
                    } else {
                        showToast(accountResponse.getMessage());
                    }
                } else if (o instanceof ErrorResponse) {
                    ErrorResponse responseError = (ErrorResponse) o;
                    try {
                        getBinding().tvCreatePassFail.setVisibility(View.VISIBLE);
                        getBinding().tvCreatePassFail.setText(responseError.getMessage());
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    private void handleValidateFail(EditText editText, LinearLayout linearLayout, String error , TextView tvError) {
        linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login_fail));
        tvError.setText(error);
        tvError.setVisibility(View.VISIBLE);
        editText.requestFocus();
    }

    @SuppressLint("CheckResult")
    private void handleValidateSuccess(EditText editText, LinearLayout linearLayout) {
        RxTextView.afterTextChangeEvents(editText)
                .skipInitialValue()
                .debounce(450, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewAfterTextChangeEvent -> {
                    linearLayout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_login));
                });
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.RECREATE_PASS, TrackingAnalytic.ScreenTitle.RECREATE_PASS);
    }


    private void resetError(){
        getBinding().tvPassError.setText("Mật khẩu từ 6 - 20 ký tự");
        getBinding().tvRetypePassError.setVisibility(View.INVISIBLE);
        getBinding().tvRetypePassError.setText("");
        getBinding().tvCreatePassFail.setVisibility(View.INVISIBLE);
        getBinding().tvCreatePassFail.setText("");
    }
}
