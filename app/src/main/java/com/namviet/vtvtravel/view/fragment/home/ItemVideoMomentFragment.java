package com.namviet.vtvtravel.view.fragment.home;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;

public class ItemVideoMomentFragment extends Fragment {
    private ImageView imageView;
    private TextView tvTitle;
    private Travel mTravel;


    public static ItemVideoMomentFragment newInstance(Travel travel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, travel);
        ItemVideoMomentFragment fragment = new ItemVideoMomentFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mTravel = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_video_moment, null);
        imageView = rootView.findViewById(R.id.image);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        if (null != mTravel) {
            setImageUrl(mTravel.getLogo_url(), imageView);
            tvTitle.setText(mTravel.getName());
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
