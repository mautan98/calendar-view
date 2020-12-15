package com.namviet.vtvtravel.view.fragment.f2callnow;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.offline.ContactAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentContactCallNowBinding;
import com.namviet.vtvtravel.model.f2.Contact;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.InviteDialog;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ContactCallNowFragment extends MainFragment implements ContactAdapter.ClickInvite, ContactAdapter.ClickItem {
    private F2FragmentContactCallNowBinding binding;
    private ContactAdapter contactAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_contact_call_now, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.CALL_NOW_CONTACT, TrackingAnalytic.ScreenTitle.CALL_NOW_CONTACT).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        updateViews();
//        setClick();

    }

    @Override
    protected void updateViews() {
        super.updateViews();
        contactAdapter = new ContactAdapter(mActivity, null, this, this);
        binding.rclContent.setAdapter(contactAdapter);
        getAllContact();
        handleScroll();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClickInvite(int position) {
        InviteDialog inviteDialog = new InviteDialog();
        inviteDialog.show(getChildFragmentManager(), null);
    }

    public void search(String s, int visiblePosition) {
        if (s == null || s.isEmpty()) {
            getAllContact();
        } else {
            List<Contact> contactsResultSearch = new ArrayList<>();
            for (int i = 0; i < contactList.size(); i++) {
                Contact contact = contactList.get(i);
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
                if(visiblePosition == 0) {
                    Toast.makeText(mActivity, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                }
                contactAdapter.setData(initHeaderContact(contactsResultSearch));
            } else {
                contactAdapter.setData(initHeaderContact(contactsResultSearch));
            }
        }
    }

    public void getAllContact() {
        List<Contact> contacts = new ArrayList<>(mActivity.listContactCallNow.values());
        contactAdapter.setData(initHeaderContact(contacts));
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
                }

                if(visiblePosition == 0){
                    binding.tvHeader.setVisibility(View.INVISIBLE);
                }else {
                    binding.tvHeader.setVisibility(View.VISIBLE);
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

    public void refreshCallNow() {
        getAllContact();
    }
}
