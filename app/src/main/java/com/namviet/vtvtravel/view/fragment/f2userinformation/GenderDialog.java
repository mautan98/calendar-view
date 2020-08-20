package com.namviet.vtvtravel.view.fragment.f2userinformation;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentInviteBinding;
import com.namviet.vtvtravel.databinding.F2LayoutBottomSheetGenderBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2.ClassForInvitedUser;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonDialog;
import com.namviet.vtvtravel.viewmodel.f2callnow.InviteDialogViewModel;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class GenderDialog extends BaseDialogBottom implements View.OnClickListener {
    private F2LayoutBottomSheetGenderBinding binding;
    private Gender gender;

    @SuppressLint("ValidFragment")
    public GenderDialog(Gender gender){
        this.gender = gender;
    }

    public GenderDialog(){

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.f2_layout_bottom_sheet_gender;
    }

    @Override
    protected void init(Bundle saveInstanceState, View view) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((View) Objects.requireNonNull(getView()).getParent()).setBackgroundColor(Color.TRANSPARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getDialog().getWindow() != null) {
            this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_layout_bottom_sheet_gender, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        binding.layoutMale.setOnClickListener(this);
        binding.layoutFemale.setOnClickListener(this);
        binding.layoutOther.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutMale:
                gender.clickGender("Nam");
                dismiss();
                break;
            case R.id.layoutFemale:
                gender.clickGender("Nữ");
                dismiss();
                break;
            case R.id.layoutOther:
                gender.clickGender("Khác");
                dismiss();
                break;
        }
    }

    public interface Gender {
        void clickGender(String gender);
    }
}
