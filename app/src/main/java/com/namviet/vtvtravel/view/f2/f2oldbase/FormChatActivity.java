package com.namviet.vtvtravel.view.f2.f2oldbase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.FormChatFragment;
import com.namviet.vtvtravel.view.fragment.f2account.ForgetPassF2Fragment;
import com.namviet.vtvtravel.view.fragment.search.SearchFragment;

public class FormChatActivity extends MainActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new FormChatFragment()).commit();
    }

    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, FormChatActivity.class);
        activity.startActivity(intent);
    }
}
