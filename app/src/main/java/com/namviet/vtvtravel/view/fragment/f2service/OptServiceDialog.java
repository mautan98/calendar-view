package com.namviet.vtvtravel.view.fragment.f2service;

import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.DialogOtpServiceBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.dialog.BaseDialogFragment;

import java.util.Observable;
import java.util.Observer;


public class OptServiceDialog extends BaseDialogFragment implements Observer {
    private DialogOtpServiceBinding binding;
    private ServiceViewModel serviceViewModel;
    private Service service;
    private RegisterFailDialog registerFailDialog;
    private CountDownTimer cTimer = null;

    public static OptServiceDialog newInstance(Service service) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_DIALOG, service);
        OptServiceDialog loadingDialog = new OptServiceDialog();
        loadingDialog.setArguments(bundle);
        return loadingDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            service = getArguments().getParcelable(Constants.IntentKey.KEY_DIALOG);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_otp_service, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected void initView(View v) {
        serviceViewModel = new ServiceViewModel(getContext());
        binding.setServiceViewModel(serviceViewModel);
        serviceViewModel.addObserver(this);

        binding.btnExit.setOnClickListener(this);
        binding.btnSendOtp.setOnClickListener(this);
        binding.btnResentOtp.setOnClickListener(this);

        String message = "Một mã xác thực đã được gửi đến số thuê bao \n" + MyApplication.getInstance().getAccount().getMobile();
        binding.txtPhone.setText(message);
        startCountDown();
    }

    private void startCountDown() {
        cTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                String minute = String.valueOf(millisUntilFinished / 60000);
                String second = String.valueOf((millisUntilFinished % 60000) / 1000);
                if (second.length() == 1) {
                    second = "0" + second;
                }
                binding.txt3.setText("0" + minute + ":" + second);
                binding.btnResentOtp.setTextColor(Color.parseColor("#999999"));
                binding.btnResentOtp.setClickable(false);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                binding.txt3.setText("");
                binding.btnResentOtp.setTextColor(Color.parseColor("#FFFFFF"));
                binding.btnResentOtp.setClickable(true);
                showMessage("Thời gian nhập mã OTP đã kết thúc, xin vui lòng thử lại");
            }

        }.start();
    }

    private void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    @Override
    public void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        String mobile = MyApplication.getInstance().getAccount().getMobile();
        switch (view.getId()) {
            case R.id.btn_exit:
                dismiss();
                break;
            case R.id.btn_sendOtp:
//                dismiss();
                String otp = binding.edtOtpService.getText().toString();
                if (otp.isEmpty()) {
                    showMessage("Mã OTP không được phép bỏ trống");
                } else {
                    KeyboardUtils.hideKeyboard(mActivity, binding.edtOtpService);
                    showDialogLoading();
                    serviceViewModel.confirmServiceOtp(mobile, service.getCode(), "ANDROID", otp);
                }
                break;
            case R.id.btn_resentOtp:
                showDialogLoading();
                serviceViewModel.resentServiceOtp(mobile, service.getCode(), "ANDROID");
                startCountDown();
                break;
            default:
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        dimissDialogLoading();
        cancelTimer();
        if (o instanceof ServiceViewModel) {
            if (arg instanceof ServiceOtpResponse) {
//                typeRegisterDialog = OptServiceDialog.newInstance();
//                typeRegisterDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
//                typeRegisterDialog.setCancelable(true);
                ServiceOtpResponse serviceOtpResponse = (ServiceOtpResponse) arg;
                if (serviceOtpResponse.isSuccess()) {
                    dismiss();
                    if(service.getCode().equals("TRAVEL_VIP")) {
                        mActivity.switchFragment(SlideMenu.MenuType.REGISTER_SUCCESS_SCREEN);
                    }else {
                        mActivity.switchFragment(SlideMenu.MenuType.REGISTER_SUCCESS_FRIEND_SCREEN);
                    }
                    try {
                        TrackingAnalytic.postEvent(TrackingAnalytic.PACKAGE_REQUEST, TrackingAnalytic.getDefault("", "").setScreen_class(this.getClass().getName()));
                        TrackingAnalytic.postEvent(TrackingAnalytic.PACKAGE_SUBSCRIBE_SUCCESS, TrackingAnalytic.getDefault("", "").setScreen_class(this.getClass().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    showMessage(serviceOtpResponse.getMessage());
//                    Toast.makeText(mActivity, serviceOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (arg instanceof ErrorResponse) {

                try {
                    ErrorResponse errorResponse = (ErrorResponse) arg;
                    if ("USER_PKG_NOT_ENOGHT_MONEY".equals(errorResponse.getErrorCode())) {
                        dismiss();
                        registerFailDialog = RegisterFailDialog.newInstance();
                        registerFailDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                        registerFailDialog.setCancelable(true);
                        TrackingAnalytic.postEvent(TrackingAnalytic.PACKAGE_SUBSCRIBE_NOT_ENOUGH_CREDIT, TrackingAnalytic.getDefault("", "").setScreen_class(this.getClass().getName()));
                    } else {
                        registerFailDialog = RegisterFailDialog.newInstance(errorResponse.getMessage());
                        registerFailDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                        registerFailDialog.setCancelable(true);
                        TrackingAnalytic.postEvent(TrackingAnalytic.PACKAGE_SUBSCRIBE_FAIL, TrackingAnalytic.getDefault("", "").setScreen_class(this.getClass().getName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
