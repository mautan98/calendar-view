package com.namviet.vtvtravel.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.GalleryPagerAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Gallery;

import java.util.ArrayList;

public class PhotoGalleryFragment extends MainFragment {
    private ArrayList<String> photoList;
    private ViewPager viewPager;
    private GalleryPagerAdapter galleryPagerAdapter;
    private int position = 0;
    private ImageView ivBack;
    private ImageView ivSearch;
    private TextView tvTitle;


//    public static PhotoGalleryFragment newInstance(ArrayList<String> url) {
//        Bundle bundle = new Bundle();
//        bundle.putStringArrayList(Constants.IntentKey.KEY_FRAGMENT, url);
//        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//
//    }
//
//    public static PhotoViewFragment newInstance() {
//        PhotoViewFragment fragment = new PhotoViewFragment();
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            photoList = getArguments().getStringArrayList(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        ivBack = v.findViewById(R.id.ivBack);
        ivSearch = v.findViewById(R.id.ivSearch);
        tvTitle = v.findViewById(R.id.tvTitle);
        viewPager = v.findViewById(R.id.view_pager);
        ivSearch.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.gallery));
        ivBack.setOnClickListener(this);
        galleryPagerAdapter = new GalleryPagerAdapter(getContext(), photoList);
        viewPager.setAdapter(galleryPagerAdapter);
        viewPager.setCurrentItem(position);
    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == ivBack) {
            mActivity.onBackPressed();
        }
    }
}
