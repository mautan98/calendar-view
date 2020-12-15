package com.namviet.vtvtravel.view.fragment.f2callnow;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.baseapp.utils.KeyboardUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.offline.AllContactAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentSearchCallBinding;
import com.namviet.vtvtravel.model.f2.ClassForInvitedUser;
import com.namviet.vtvtravel.model.f2.Contact;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2callnow.VerifyUserResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.InviteDialog;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonDialog;
import com.namviet.vtvtravel.view.fragment.f2offline.TwoButtonDialog;
import com.namviet.vtvtravel.viewmodel.f2callnow.SearchNumberViewModel;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class SearchNumberFragment extends MainFragment implements AllContactAdapter.ClickInvite, AllContactAdapter.ClickItem, Observer {
    private F2FragmentSearchCallBinding binding;
    private String phone;
    private AllContactAdapter allContactAdapter;
    private SearchNumberViewModel searchNumberViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_search_call, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews(view);

        searchNumberViewModel = new SearchNumberViewModel();
        binding.setSearchNumberViewModel(searchNumberViewModel);
        searchNumberViewModel.addObserver(this);

        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SEARCH_NUMBER, TrackingAnalytic.ScreenTitle.SEARCH_NUMBER).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void checkNumber(String number){
        searchNumberViewModel.verifyUser(number);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        updateViews();
        setClick();
        if (phone == null || (phone != null && phone.isEmpty())) {
            binding.btnCall.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_circle_cal));
        } else {
            binding.btnCall.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_circle_call_enable));
            String validatePhone;
            if ("84".equals(phone.substring(0, 2))) {
                validatePhone = phone.replaceFirst("84", "0");
            } else {
                validatePhone = phone;
            }
            binding.edtPhone.setText(validatePhone);

        }
        binding.edtPhone.requestFocus();
        showSoftKeyboard(binding.edtPhone);

        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            binding.layout.setVisibility(View.VISIBLE);
                        } else {
                            binding.layout.setVisibility(View.GONE);
                        }
                    }
                });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handleSearch();
            }
        }, 1000);
    }

    private void setClick() {

        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                F2Util.startCallIntent(mActivity, "1039" + phone);
                String mobile = binding.edtPhone.getText().toString();
                if (!ValidateUtils.isValidPhoneNumber09(mobile)) {
                    OneButtonDialog oneButtonDialog = OneButtonDialog.newInstance("Số điện thoại bạn đang gọi \nkhông đúng", "OK", new OneButtonDialog.ClickButton() {
                        @Override
                        public void onClickButton() {

                        }
                    });
                    oneButtonDialog.show(getChildFragmentManager(), null);
                } else {
                    if (!F2Util.isOnline(mActivity)) {
                        F2Util.startCallIntent(mActivity, "1039" + binding.edtPhone.getText().toString());
                    }else {
                        checkNumber(mobile.replaceFirst("0","84"));
                    }

                }
            }
        });

        binding.btnCloseKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideKeyboard(mActivity, binding.edtPhone);
                mActivity.onBackPressed();
            }
        });


        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textString = binding.edtPhone.getText().toString();
                if (textString.length() > 0) {
                    binding.edtPhone.setText(textString.substring(0, textString.length() - 1));
                    binding.edtPhone.setSelection(binding.edtPhone.getText().length());
                }
            }
        });

        binding.imgHideKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideKeyboard(mActivity, binding.edtPhone);
                binding.layoutType.setVisibility(View.GONE);
            }
        });

        binding.imgShowKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutType.setVisibility(View.VISIBLE);
                binding.edtPhone.requestFocus();
                showSoftKeyboard(binding.edtPhone);
            }
        });
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        allContactAdapter = new AllContactAdapter(mActivity, null, this, this, false);
        binding.rclContent.setAdapter(allContactAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void getAllContact() {
        allContactAdapter.setData(initHeaderContact(mActivity.listContact));
    }

    private List<Contact> contactList = new ArrayList<>();

    private List<Contact> initHeaderContact(List<Contact> contacts) {

//        Locale lithuanian = new Locale("vi_VN");
//        Collator lithuanianCollator = Collator.getInstance(lithuanian);
//        Collections.sort(contacts, lithuanianCollator);

        Locale locale = new Locale("vi_VN");
        Collator collator = Collator.getInstance(locale);


        Collections.sort(contacts, new Comparator<Contact>() {
            public int compare(Contact one, Contact other) {
                return collator.compare(one.getContactName(), other.getContactName());
            }
        });

        List<Contact> contactList = new ArrayList<>();
        String poolCharacter = "";

        try {
            for (Contact contact : contacts) {
                String currentCharacter = String.valueOf(contact.getContactName().charAt(0)).toUpperCase();
                if (!poolCharacter.equals(currentCharacter)) {

                    poolCharacter = currentCharacter;

                    Contact headerContact = new Contact();
                    headerContact.setContactName(poolCharacter);
                    headerContact.setHeader(true);

                    contactList.add(headerContact);
                }
                contactList.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.contactList = contactList;
        return contactList;
    }


    @Override
    public void onClickInvite(int position, String phone, String phoneToShow) {

    }

    @Override
    public void onClickItem(int position, String phone, String phoneToShow) {
//        binding.edtPhone.setText(phoneToShow);

        String validatePhone;
        if ("84".equals(phoneToShow.substring(0, 2))) {
            validatePhone = phoneToShow.replaceFirst("84", "0");
        }else if ("+84".equals(phoneToShow.substring(0, 3))) {
            validatePhone = phoneToShow.replaceFirst("\\+84", "0");
        } else {
            validatePhone = phoneToShow;
        }
        binding.edtPhone.setText(validatePhone);
    }


    public void search(String s) {
        binding.layoutIntro.setVisibility(View.GONE);
        if (s == null || s.isEmpty()) {
            binding.btnCall.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_circle_cal));
            binding.btnCall.setEnabled(false);
            getAllContact();
        } else {
            binding.btnCall.setEnabled(true);
            binding.btnCall.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_circle_call_enable));
            List<Contact> contactsResultSearch = new ArrayList<>();
            for (int i = 0; i < mActivity.listContact.size(); i++) {
                Contact contact = mActivity.listContact.get(i);

                if (contact.getPhones() != null && contact.getPhones().size() > 0) {
                    String phone = contact.getPhones().get(0);
                    if (phone.contains(s)) {
                        contactsResultSearch.add(contact);
                    } else if (phone.replaceFirst("84", "0").contains(s)) {
                        contactsResultSearch.add(contact);
                    }else if (phone.replaceFirst("84", "+84").contains(s)) {
                        contactsResultSearch.add(contact);
                    }

                }
            }

            if (contactsResultSearch.size() == 0) {
                allContactAdapter.setData(initHeaderContact(contactsResultSearch));
                Toast.makeText(mActivity, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
            } else {
                allContactAdapter.setData(initHeaderContact(contactsResultSearch));
            }
        }

    }


    private void handleSearch() {
        RxTextView.afterTextChangeEvents(binding.edtPhone)
                .skipInitialValue()
                .debounce(450, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                        search(binding.edtPhone.getText().toString());
                    }
                });
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof SearchNumberViewModel && null != o) {
            if (o instanceof VerifyUserResponse) {
                VerifyUserResponse verifyUserResponse = (VerifyUserResponse) o;
                if(verifyUserResponse.isData()){
                    F2Util.startCallIntent(mActivity, "1039" + binding.edtPhone.getText().toString());
                }else {
                    TwoButtonDialog twoButtonDialog = TwoButtonDialog.newInstance("Số điện thoại bạn gọi \nkhông có trong hệ thống \nVTVTravel", "Mời", "Hủy", new TwoButtonDialog.ClickButton() {
                        @Override
                        public void onClickButton1() {
                            String mobile = binding.edtPhone.getText().toString();
//                            InviteDialog inviteDialog = new InviteDialog();
//                            inviteDialog.setPhone(mobile.replaceFirst("0", "84"));
//                            inviteDialog.setPhoneToShow(phone);
//                            inviteDialog.show(getChildFragmentManager(), null);


                            ClassForInvitedUser classForInvitedUser = MyApplication.getAppDatabase().foodDao().getInvitedUserFromId(phone);
                            if (classForInvitedUser != null) {
                                InviteDialog inviteDialog = new InviteDialog();
                                inviteDialog.setPhone(mobile.replaceFirst("0", "84"));
                                inviteDialog.setPhoneToShow(phone);
                                inviteDialog.setInvited(true);
                                inviteDialog.show(getChildFragmentManager(), null);
                            } else {
                                InviteDialog inviteDialog = new InviteDialog();
                                inviteDialog.setPhone(mobile.replaceFirst("0", "84"));
                                inviteDialog.setPhoneToShow(phone);
                                inviteDialog.setInvited(false);
                                inviteDialog.show(getChildFragmentManager(), null);
                            }
                        }

                        @Override
                        public void onClickButton2() {

                        }
                    });
                    twoButtonDialog.show(getChildFragmentManager(), null);
                }

            } else if (o instanceof ResponseError) {
                ResponseError responseError = (ResponseError) o;
                showMessage(responseError.getMessage());
            }
        }

    }
}
