package com.namviet.vtvtravel.view.fragment.f2offline;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentInviteBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2.ClassForInvitedUser;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.viewmodel.f2callnow.InviteDialogViewModel;
import com.namviet.vtvtravel.viewmodel.f2callnow.MainCallHistoryViewModel;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class InviteDialog extends BaseDialogBottom implements Observer {
    private F2FragmentInviteBinding binding;
    private InviteDialogViewModel inviteDialogViewModel;
    private String phone;
    private String phoneToShow;
    private boolean isInvited;
    private int state = 0;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhoneToShow(String phoneToShow) {
        this.phoneToShow = phoneToShow;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.f2_fragment_invite;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_invite, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        inviteDialogViewModel = new InviteDialogViewModel();
        binding.setInviteDialogViewModel(inviteDialogViewModel);
        inviteDialogViewModel.addObserver(this);

        binding.btnRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                binding.btnAgreeDisable.setVisibility(View.GONE);
                binding.btnAgreeEnable.setVisibility(View.VISIBLE);



                if(checkedId == R.id.btnSendTo1039){
                    state = 1;
                }else if(checkedId == R.id.btnShareToOtherApp){
                    state = 2;
                }

                if(isInvited && state == 1){
                    binding.btnAgreeDisable.setVisibility(View.VISIBLE);
                    binding.btnAgreeEnable.setVisibility(View.GONE);
                }
            }
        });

        binding.btnAgreeEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state == 1){
                    if (!ValidateUtils.isValidPhoneNumberInvite(phone)) {
                        OneButtonDialog oneButtonDialog = OneButtonDialog.newInstance("Số điện thoại bạn đang mời \nkhông đúng", "OK", new OneButtonDialog.ClickButton() {
                            @Override
                            public void onClickButton() {

                            }
                        });
                        oneButtonDialog.show(getChildFragmentManager(), null);
                    }else {
                        inviteDialogViewModel.inviteCallNow(phone);
                    }
                }else if (state == 2){
                    try {
                        Account account = MyApplication.getInstance().getAccount();
                        F2Util.startSenDataText((MainActivity)getBaseActivity(), "[QC] Bạn nhận được lời mời tham gia tính năng Call Now từ số điện thoại "+account.getMobile()+", truy cập [link] và tải ứng dụng VTVTravel ngay để được tham gia tính năng gọi thoại với mức cước cực kỳ hấp dẫn.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        if(isInvited){
            binding.btnSendTo1039.setAlpha(0.5f);
        }

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof InviteDialogViewModel && null != o) {
            if (o instanceof BaseResponse) {
                BaseResponse baseResponse = (BaseResponse) o;
                OneButtonDialog oneButtonDialog = OneButtonDialog.newInstance("Bạn đã mời thành công số \n" + phoneToShow, "OK", new OneButtonDialog.ClickButton() {
                    @Override
                    public void onClickButton() {
                        ClassForInvitedUser classForInvitedUser = new ClassForInvitedUser();
                        classForInvitedUser.setPhone(phone);
                        MyApplication.getAppDatabase().foodDao().insertOneInvitedUser(classForInvitedUser);
                        dismiss();
                    }
                });
                oneButtonDialog.show(getChildFragmentManager(), null);
            } else if (o instanceof ResponseError) {
                ResponseError responseError = (ResponseError) o;
                try {
                    Toast.makeText(getBaseActivity(), "Mời không thành công, bạn hãy thử lại sau nhé!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setInvited(boolean invited) {
        isInvited = invited;
    }
}
