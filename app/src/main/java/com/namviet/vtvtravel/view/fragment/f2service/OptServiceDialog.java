package com.namviet.vtvtravel.view.fragment.f2service;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.DialogOtpServiceBinding;
import com.namviet.vtvtravel.databinding.DialogTypeRegisterBinding;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.dialog.BaseDialogFragment;

import java.util.Objects;
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
        if (o instanceof ServiceViewModel) {
            if (arg instanceof ServiceOtpResponse) {
//                typeRegisterDialog = OptServiceDialog.newInstance();
//                typeRegisterDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
//                typeRegisterDialog.setCancelable(true);
                ServiceOtpResponse serviceOtpResponse = (ServiceOtpResponse) arg;
                if (serviceOtpResponse.isSuccess()) {
                    mActivity.switchFragment(SlideMenu.MenuType.REGISTER_SUCCESS_SCREEN);
                } else {
                    showMessage(serviceOtpResponse.getMessage());
//                    Toast.makeText(mActivity, serviceOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (arg instanceof ResponseError) {
//                ResponseError responseError = (ResponseError) arg;
//                showMessage(responseError.getMessage());
                registerFailDialog = RegisterFailDialog.newInstance();
                registerFailDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                registerFailDialog.setCancelable(true);
            }
        }
    }
}
