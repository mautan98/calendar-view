package com.namviet.vtvtravel.view.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.namviet.vtvtravel.R;

public class MapChatFragment extends MainFragment {
    private WebView mMapWv;
    private String html;
    public static final String KEY_MAP = "KEY_MAP";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(KEY_MAP)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            html = getArguments().getString(KEY_MAP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_chat, container, false);
        mMapWv = view.findViewById(R.id.wv_chat);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapWv.getSettings().setJavaScriptEnabled(true);
        mMapWv.loadData(html, "text/html; charset=utf-8", "UTF-8");
    }
}
