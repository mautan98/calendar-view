package com.namviet.vtvtravel.view.fragment.f2service;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.DialogTypeRegisterBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.view.dialog.BaseDialogFragment;

import java.util.Observable;
import java.util.Observer;


public class TypeRegisterDialog extends BaseDialogFragment implements Observer {
    private DialogTypeRegisterBinding binding;
    private OptServiceDialog typeRegisterDialog;
    private ServiceViewModel serviceViewModel;
    private Service service;

    public static TypeRegisterDialog newInstance(Service service) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_DIALOG, service);
        TypeRegisterDialog loadingDialog = new TypeRegisterDialog();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_type_register, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceViewModel = new ServiceViewModel(getContext());
        binding.setServiceViewModel(serviceViewModel);
        serviceViewModel.addObserver(this);

        binding.btnSimRegister.setOnClickListener(this::onClick);
        binding.btnExit.setOnClickListener(this::onClick);
        try {
            binding.txt2.setText("Quý khách lựa chọn hình thức đăng ký gói dịch vụ " + service.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_sim_register:
                String mobile = MyApplication.getInstance().getAccount().getMobile();
                showDialogLoading();
                serviceViewModel.requestServiceOtp(mobile, service.getCode(), "ANDROID");
                dismiss();
                break;
            case R.id.btn_exit:
                dismiss();
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
                ServiceOtpResponse serviceOtpResponse = (ServiceOtpResponse) arg;
                if (serviceOtpResponse.isSuccess()) {
                    dismiss();
                    typeRegisterDialog = OptServiceDialog.newInstance(service);
                    typeRegisterDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                    typeRegisterDialog.setCancelable(true);
                } else {
                    Toast.makeText(mActivity, serviceOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (arg instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) arg;
//                showMessage(responseError.getMessage());
                Toast.makeText(mActivity, responseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//    }
}
