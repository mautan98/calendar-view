package com.namviet.vtvtravel.view.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentFormChatBinding;
import com.namviet.vtvtravel.model.f2event.OnBackToChatBot;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.fragment.f2chat.FormSuccessDialog;
import com.namviet.vtvtravel.viewmodel.FormChatViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class FormChatFragment extends MainFragment implements Observer {
    private F2FragmentFormChatBinding binding;
    private FormChatViewModel formChatViewModel;

    private List<String> mStartList = new ArrayList<>();
    private List<String> mEndList = new ArrayList<>();
    private ArrayAdapter<String> mStartAdapter;
    private ArrayAdapter<String> mEndAdapter;

    private Calendar mCalendar;

    private String mUsername;
    private String mPhone;
    private String mTimeContact;
    private String mStartTime;
    private String mEndTime;
    private String mContent;
    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_form_chat, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        startCountDown();
        detectActive();
        formChatViewModel = new FormChatViewModel();
        binding.setFormChatViewModel(formChatViewModel);
        formChatViewModel.addObserver(this);


        mCalendar = Calendar.getInstance();
        binding.toolBar.tvTitle.setText("");
        binding.edtBirth.setOnClickListener(this);
        binding.btnSend.setOnClickListener(this);
        binding.toolBar.ivBack.setOnClickListener(this);

        mStartAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, getStartTimes());
        mStartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerStart.setAdapter(mStartAdapter);
        binding.spinnerStart.setSelection(0);
        binding.spinnerStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startCountDown();
                String a = parent.getItemAtPosition(position).toString();
                Log.d("LamLV: ", a);
                mEndList.clear();
                for (int i = position; i < mStartList.size(); i++) {
                    mEndList.add(i + 9 + ":00");
                }
                if (mEndAdapter != null) {
                    mEndAdapter.notifyDataSetChanged();
                    binding.spinnerEnd.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mEndAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, mEndList);
        mEndAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerEnd.setAdapter(mEndAdapter);
        binding.spinnerEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String a = parent.getItemAtPosition(position).toString();
                Log.d("LamLV: ", a);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void detectActive(){
        binding.edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                startCountDown();
            }
        });


        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                startCountDown();
            }
        });

        binding.edtBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                startCountDown();
            }
        });

        binding.edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                startCountDown();
            }
        });

        binding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                startCountDown();
            }
        });
    }



    private CountDownTimer mTimer60s;

    private void startCountDown() {
        final int[] i = {0};
        if (mTimer60s != null) {
            mTimer60s.cancel();
            i[0] = 0;
        }
        mTimer60s = new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                i[0] = i[0] + 1;
            }

            public void onFinish() {
                mActivity.finish();
            }

        }.start();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.edtBirth:
                int year = mCalendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((dialog, year1, monthOfYear, dayOfMonth) -> {
                    String day = "" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                    String month = "" + (monthOfYear < 9 ? "0" + (monthOfYear + 1) : monthOfYear + 1);
                    String mBirth = day + "-" + month + "-" + year1;
                    binding.edtBirth.setText(mBirth);
                }, year, mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show(mActivity.getSupportFragmentManager(), "datePicker");
                datePickerDialog.setMinDate(mCalendar);
                break;
            case R.id.btnSend:

                startCountDown();
                mUsername = binding.edtUsername.getText().toString();
                mPhone = binding.edtPhone.getText().toString();
                mTimeContact = binding.edtBirth.getText().toString();
                mStartTime = mTimeContact + " " + binding.spinnerStart.getSelectedItem().toString() + ":00";
                mEndTime = mTimeContact + " " + binding.spinnerEnd.getSelectedItem().toString() + ":00";
                mContent = binding.edtContent.getText().toString();
                email = binding.edtEmail.getText().toString();


                if (mUsername.isEmpty() || mPhone.isEmpty() || mTimeContact.isEmpty() || mStartTime.isEmpty() || mEndTime.isEmpty() || mContent.isEmpty()) {
                    Toast.makeText(mActivity, "Bạn cần nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (!ValidateUtils.isValidPhoneNumber(mPhone)) {
                    Toast.makeText(mActivity, "Số điện thoại bạn nhập không đúng. Xin vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                } else {

                    if(!ValidateUtils.isValidEmail(email)){
                        Toast.makeText(mActivity, "Email bạn nhập không đúng định dạng. Xin vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    formChatViewModel.sendFormChat(mUsername, mPhone, mTimeContact, mStartTime, mEndTime, mContent, email);
                    try {
                        KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
                    } catch (Exception e) {
                    }
                }
                break;
            case R.id.ivBack:
                mActivity.onBackPressed();
                break;
            default:
                break;
        }
    }

    private List<String> getStartTimes() {
        for (int i = 8; i < 21; i++) {
            mStartList.add(i + ":00");
        }
        return mStartList;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (null != o && null != arg) {
            if (arg instanceof BaseResponse) {
                BaseResponse baseResponse = (BaseResponse) arg;
                if (baseResponse.getStatus().equals("success")) {
//                    mActivity.onBackPressed();
//                    mActivity.switchFragment(SlideMenu.MenuType.FORM_SUCCESS_SCREEN);
                    FormSuccessDialog formSuccessDialog = FormSuccessDialog.newInstance();
                    formSuccessDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                    formSuccessDialog.setCancelable(false);
                }
            } else if (arg instanceof ResponseError) {
                ResponseError responseError = (ResponseError) arg;
                mActivity.showMessage(responseError.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
        EventBus.getDefault().post(new OnBackToChatBot());
    }


    private void cancelTimer(){
        if(mTimer60s != null){
            mTimer60s.cancel();
        }
    }
}
