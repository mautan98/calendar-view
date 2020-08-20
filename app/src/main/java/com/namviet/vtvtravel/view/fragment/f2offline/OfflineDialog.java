package com.namviet.vtvtravel.view.fragment.f2offline;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.offline.OfflineOptionAdapter;
import com.namviet.vtvtravel.databinding.F2DialogOfflineBinding;
import com.namviet.vtvtravel.model.offline.Items;
import com.namviet.vtvtravel.model.offline.ItemsPopup;
import com.namviet.vtvtravel.model.offline.OfflineDynamic;
import com.namviet.vtvtravel.model.offline.OfflineOption;
import com.namviet.vtvtravel.view.dialog.BaseDialogFragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OfflineDialog extends BaseDialogFragment {
    private F2DialogOfflineBinding binding;
    private OfflineOptionAdapter offlineOptionAdapter;
    private OfflineDynamic offlineDynamic;
    private ArrayList<ItemsPopup> itemsPopups = new ArrayList<>();
    private String parentLink;

    public static OfflineDialog newInstance() {
        OfflineDialog offlineDialog = new OfflineDialog();
        return offlineDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_offline, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        offlineDynamic = new Gson().fromJson(loadJSONFromAsset(), OfflineDynamic.class);
        offlineDynamic = mActivity.offlineDynamic;
        getParenLink();
        getList();
        setData();
        initViews();
    }

    private void setData() {
        Glide.with(mActivity).load(parentLink+offlineDynamic.getPopupScreen().getBanner().getImage()).into(binding.imgBanner);
    }

    private void getParenLink(){
        parentLink = mActivity.getExternalFilesDir(null) + File.separator + "/VTVTravelDataCall_Now/Call_Now/";
    }
    private void initViews() {
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        offlineOptionAdapter = new OfflineOptionAdapter(getContext(), itemsPopups, new OfflineOptionAdapter.ItemClick() {
            @Override
            public void onItemClick(int position) {
                mActivity.goToOffline(Integer.parseInt(offlineDynamic.getPopupScreen().getItems().get(position).getId()));
                dismiss();
            }
        }, parentLink);
        binding.recyclerOfflineOption.setAdapter(offlineOptionAdapter);

        try {
            String mess = offlineDynamic.getPopupScreen().getDescription();
            binding.txtWelcome.setText(spannableString(mess, mess.length() - 11, mess.length() - 1));
        } catch (Exception e) {

        }
    }

    private void getList() {
        itemsPopups = (ArrayList<ItemsPopup>) offlineDynamic.getPopupScreen().getItems();
    }

    private SpannableString spannableString(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("offline.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
