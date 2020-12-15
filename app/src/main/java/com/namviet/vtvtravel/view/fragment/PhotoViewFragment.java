package com.namviet.vtvtravel.view.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;

public class PhotoViewFragment extends MainFragment {
    private ImageView imageView;
    private TextView textView;
    private Travel travel;


    public static PhotoViewFragment newInstance(Travel url) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, url);
        PhotoViewFragment fragment = new PhotoViewFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    public static PhotoViewFragment newInstance() {
        PhotoViewFragment fragment = new PhotoViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            travel = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, null);
        imageView = rootView.findViewById(R.id.image);
        textView = rootView.findViewById(R.id.tvName);
        if (null != travel) {
            if (null != travel.getLogo_url()) {
                setImageUrl(travel.getLogo_url(), imageView);
            } else if (null != travel.getBanner_url()) {
                setImageUrl(travel.getBanner_url(), imageView);
            } else if (null != travel.getPhoto_url()) {
                setImageUrl(travel.getPhoto_url(), imageView);
            }
            if (null != travel.getName()){
                textView.setText(travel.getName());
            }
            if (null == travel.getPhoto_url()) {
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, travel);
                        mActivity.setBundle(bundle);
                        mActivity.switchFragment(SlideMenu.MenuType.DETAIL_MOMENT_SCREEN);
                    }
                });
            }
        }

        return rootView;
    }

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(getContext()).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);

    }
}
