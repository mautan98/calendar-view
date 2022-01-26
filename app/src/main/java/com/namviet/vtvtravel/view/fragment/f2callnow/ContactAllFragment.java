package com.namviet.vtvtravel.view.fragment.f2callnow;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.offline.AllContactAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentContactAllBinding;
import com.namviet.vtvtravel.model.f2.CheckCountInviteFail;
import com.namviet.vtvtravel.model.f2.ClassForInvitedUser;
import com.namviet.vtvtravel.model.f2.Contact;
import com.namviet.vtvtravel.model.f2callnow.CallList;
import com.namviet.vtvtravel.model.f2event.OnLoadContactSuccess;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2callnow.CallListResponse;
import com.namviet.vtvtravel.response.f2callnow.CheckNumberHaveInvitedOrNot;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.InviteDialog;
import com.namviet.vtvtravel.viewmodel.f2callnow.ContactAllViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class ContactAllFragment extends MainFragment implements AllContactAdapter.ClickInvite, AllContactAdapter.ClickItem, Observer {
    private F2FragmentContactAllBinding binding;
    private AllContactAdapter allContactAdapter;
    private ContactAllViewModel contactAllViewModel;
    private LinkedHashSet<Contact> contactsToCheck = new LinkedHashSet<>();
    private int positionRoot = -10;
    private boolean haveChange = false;
    private List<Contact> contactListMain = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_contact_all, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.ALL_CONTACT, TrackingAnalytic.ScreenTitle.ALL_CONTACT).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        String json = new Gson().toJson(mActivity.listContact);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Contact>>() {
        }.getType();
        contactListMain = gson.fromJson(json, type);
        updateViews();
        getAllContact();
        handleScroll();
        setClick();

        contactAllViewModel = new ContactAllViewModel();
        binding.setContactAllViewModel(contactAllViewModel);
        contactAllViewModel.addObserver(this);


    }

    private void setClick() {
        binding.layoutTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutTryAgain.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
                                mActivity.checkPermission();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                                builder.setMessage("Bạn hãy cấp quyền truy cập danh bạ trong phần cài đặt để ứng dụng có thể lấy danh sách liên hệ.");
                                builder.setPositiveButton("Cấp quyền", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                        intent.setData(uri);
                                        mActivity.startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.create();
                                builder.show();


                            }

                            binding.layoutTryAgain.setVisibility(View.VISIBLE);
                        } else {

                            getAllContact();
                        }

                    }
                }, 500);

            }
        });
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        allContactAdapter = new AllContactAdapter(mActivity, null, this, this, true);
        binding.rclContent.setAdapter(allContactAdapter);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void getAllContact() {
        String json = new Gson().toJson(mActivity.listContact);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Contact>>() {
        }.getType();
        contactListMain = gson.fromJson(json, type);
        allContactAdapter.setData(initHeaderContact(contactListMain));
        if (contactListMain == null || (contactListMain != null && contactListMain.size() == 0)) {
            binding.layoutTryAgain.setVisibility(View.VISIBLE);
        } else {
            if (contactListMain.size() == 0) {
                binding.layoutTryAgain.setVisibility(View.VISIBLE);
            } else {
                binding.layoutTryAgain.setVisibility(View.GONE);
            }
        }
    }

    private List<Contact> contactList = new ArrayList<>();

    private List<Contact> initHeaderContact(List<Contact> contacts) {
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


    private String phoneInvite;
    private String phoneToShowInvite;

    @Override
    public void onClickInvite(int position, String phone, String phoneToShow) {
        this.phoneInvite = phone;
        this.phoneToShowInvite = phoneToShow;

//        checkNumberHaveInvitedOrNot(phone);

//        InviteDialog inviteDialog = new InviteDialog();
//        inviteDialog.setPhone(phone);
//        inviteDialog.setPhoneToShow(phoneToShow);
//        inviteDialog.show(getChildFragmentManager(), null);


        ClassForInvitedUser classForInvitedUser = MyApplication.getAppDatabase().foodDao().getInvitedUserFromId(phone);
        if (classForInvitedUser != null) {
            InviteDialog inviteDialog = new InviteDialog();
            inviteDialog.setPhone(phoneInvite);
            inviteDialog.setPhoneToShow(phoneToShowInvite);
            inviteDialog.setInvited(true);
            inviteDialog.show(getChildFragmentManager(), null);
        } else {
            InviteDialog inviteDialog = new InviteDialog();
            inviteDialog.setPhone(phoneInvite);
            inviteDialog.setPhoneToShow(phoneToShowInvite);
            inviteDialog.setInvited(false);
            inviteDialog.show(getChildFragmentManager(), null);
        }

    }

    public void search(String s, int visiblePosition) {
        if (s == null || s.isEmpty()) {
            getAllContact();
        } else {
            List<Contact> contactsResultSearch = new ArrayList<>();
            for (int i = 0; i < contactListMain.size(); i++) {
                Contact contact = contactListMain.get(i);
                if (F2Util.removeAccent(contact.getContactName().toLowerCase()).contains(F2Util.removeAccent(s.toLowerCase()))) {
                    contactsResultSearch.add(contact);
                } else if (contact.getPhones() != null && contact.getPhones().size() > 0) {
                    String phone = contact.getPhones().get(0);
                    if (phone.contains(s)) {
                        contactsResultSearch.add(contact);
                    } else if (phone.replaceFirst("84", "0").contains(s)) {
                        contactsResultSearch.add(contact);
                    } else if (phone.replaceFirst("84", "+84").contains(s)) {
                        contactsResultSearch.add(contact);
                    }

                }
            }

            if (contactsResultSearch.size() == 0) {
                if(visiblePosition == 1) {
                    Toast.makeText(mActivity, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                }
                allContactAdapter.setData(initHeaderContact(contactsResultSearch));
            } else {
                allContactAdapter.setData(initHeaderContact(contactsResultSearch));
            }
        }

    }

    private void handleScroll() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) binding.rclContent.getLayoutManager();
        binding.rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visiblePosition = layoutManager.findFirstVisibleItemPosition();
                if (visiblePosition > -1) {
                    Log.e("position", visiblePosition + "");
                    if (contactList.get(visiblePosition).getContactName().length() > 0) {
                        String name = String.valueOf(contactList.get(visiblePosition).getContactName().charAt(0));
                        binding.tvHeader.setText(name);
                    }

                    try {
                        if (visiblePosition == 0) {
                            binding.tvHeader.setVisibility(View.INVISIBLE);
                        } else {
                            binding.tvHeader.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (positionRoot != visiblePosition) {
                        positionRoot = visiblePosition;

                        if (visiblePosition % 50 == 0) {
                            Log.e("visiblePosition", visiblePosition + "");
                            try {
                                for (int i = 0; i < 50; i++) {
                                    if (!contactList.get(visiblePosition + i).isChecked()) {
                                        contactsToCheck.add(contactList.get(visiblePosition + i));
                                        contactList.get(visiblePosition + i).setChecked(true);
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("size", new Gson().toJson(contactsToCheck));
                                checkPhone();
                                contactsToCheck.clear();
                            }

                            Log.e("size", new Gson().toJson(contactsToCheck));
                            checkPhone();
                            contactsToCheck.clear();


                        }
                    }
                }
            }
        });
    }


    @Override
    public void onClickItem(int position, String phone, String phoneToShow) {
        SearchNumberFragment searchNumberFragment = new SearchNumberFragment();
        searchNumberFragment.setPhone(phone);
        mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, searchNumberFragment).addToBackStack(null).commit();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ContactAllViewModel && null != o) {
            if (o instanceof CallListResponse) {
                CallListResponse callListResponse = (CallListResponse) o;
                if (callListResponse.isSuccess()) {
                    try {
                        ArrayList<CallList> callLists = (ArrayList<CallList>) callListResponse.getData();

                        HashMap<String, CallList> callListHashMap = new HashMap<>();
                        for (CallList product : callLists) {
                            callListHashMap.put(product.getMobile(), product);
                        }


                        for (int i = 0; i < mActivity.listContact.size(); i++) {
                            try {
                                if (callListHashMap.get(mActivity.listContact.get(i).getPhones().get(0)).getId() != null) {
                                    mActivity.listContactCallNow.put(mActivity.listContact.get(i).getContactClientId(), mActivity.listContact.get(i));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        for (int i = 0; i < contactList.size(); i++) {
                            try {
                                if (callListHashMap.get(contactList.get(i).getPhones().get(0)).getId() != null) {
                                    contactList.get(i).setCallNow(true);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        allContactAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } else if (o instanceof ResponseError) {
                ResponseError responseError = (ResponseError) o;
                showMessage(responseError.getMessage());
            } else if (o instanceof CheckCountInviteFail) {
                CheckCountInviteFail responseError = (CheckCountInviteFail) o;
                InviteDialog inviteDialog = new InviteDialog();
                inviteDialog.setPhone(phoneInvite);
                inviteDialog.setPhoneToShow(phoneToShowInvite);
                inviteDialog.setInvited(false);
                inviteDialog.show(getChildFragmentManager(), null);
            } else if (o instanceof CheckNumberHaveInvitedOrNot) {
                CheckNumberHaveInvitedOrNot checkNumberHaveInvitedOrNot = (CheckNumberHaveInvitedOrNot) o;
                if (checkNumberHaveInvitedOrNot.getCallListResponse().getData().isEmpty()) {
                    InviteDialog inviteDialog = new InviteDialog();
                    inviteDialog.setPhone(phoneInvite);
                    inviteDialog.setPhoneToShow(phoneToShowInvite);
                    inviteDialog.setInvited(false);
                    inviteDialog.show(getChildFragmentManager(), null);
                } else if (checkNumberHaveInvitedOrNot.getCallListResponse().getData().get(0).getIsInvitedToday().equals("1")) {
                    InviteDialog inviteDialog = new InviteDialog();
                    inviteDialog.setPhone(phoneInvite);
                    inviteDialog.setPhoneToShow(phoneToShowInvite);
                    inviteDialog.setInvited(true);
                    inviteDialog.show(getChildFragmentManager(), null);
                } else {
                    InviteDialog inviteDialog = new InviteDialog();
                    inviteDialog.setPhone(phoneInvite);
                    inviteDialog.setPhoneToShow(phoneToShowInvite);
                    inviteDialog.setInvited(false);
                    inviteDialog.show(getChildFragmentManager(), null);
                }

            }
        }

    }

    private void checkPhone() {
        try {
            List<String> list = new ArrayList<>();
            ArrayList<Contact> contacts = new ArrayList<>(contactsToCheck);
            for (int i = 0; i < contacts.size(); i++) {
                if (!contacts.get(i).isHeader()) {
                    list.add(contacts.get(i).getPhones().get(0));
                }
            }
            contactAllViewModel.callList(list, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkNumberHaveInvitedOrNot(String number) {
        List<String> list = new ArrayList<>();
        list.add(number);
        contactAllViewModel.callList(list, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            } else {
                if(contactListMain == null || (contactListMain != null && contactListMain.size() == 0)){
                    mActivity.checkPermission();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onLoadContactSuccess(OnLoadContactSuccess onLoadContactSuccess){
        getAllContact();
    }
}
