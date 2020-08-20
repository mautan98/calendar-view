package com.namviet.vtvtravel.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2FragmentFormChatBinding;
import com.namviet.vtvtravel.databinding.FragmentFormChatBinding;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.viewmodel.FormChatViewModel;

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

                mUsername = binding.edtUsername.getText().toString();
                mPhone = binding.edtPhone.getText().toString();
                mTimeContact = binding.edtBirth.getText().toString();
                mStartTime = mTimeContact +" "+ binding.spinnerStart.getSelectedItem().toString() + ":00";
                mEndTime = mTimeContact +" "+ binding.spinnerEnd.getSelectedItem().toString() + ":00";
                mContent = binding.edtContent.getText().toString();


                if (mUsername.isEmpty() || mPhone.isEmpty() || mTimeContact.isEmpty() || mStartTime.isEmpty() || mEndTime.isEmpty() || mContent.isEmpty()) {
                    Toast.makeText(mActivity, "Bạn cần nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (!ValidateUtils.isValidPhoneNumber(mPhone)) {
                    Toast.makeText(mActivity, "Số điện thoai không chính xác!", Toast.LENGTH_SHORT).show();
                } else {
                    formChatViewModel.sendFormChat(mUsername, mPhone, mTimeContact, mStartTime, mEndTime, mContent);
                    KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
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
                    mActivity.onBackPressed();
                    mActivity.switchFragment(SlideMenu.MenuType.FORM_SUCCESS_SCREEN);
                }
            } else if (arg instanceof ResponseError) {
                ResponseError responseError = (ResponseError) arg;
                mActivity.showMessage(responseError.getMessage());
            }
        }
    }
}
