package com.namviet.vtvtravel.view.fragment.newhome;

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
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.newhome.Voucher;

public class VoucherViewFragment extends Fragment {
    private ImageView imageView;
    private TextView textView;
    private Voucher travel;


    public static VoucherViewFragment newInstance(Voucher url) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, url);
        VoucherViewFragment fragment = new VoucherViewFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    public static VoucherViewFragment newInstance() {
        VoucherViewFragment fragment = new VoucherViewFragment();
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
        View rootView = inflater.inflate(R.layout.f2_fragment_voucher, null);
        imageView = rootView.findViewById(R.id.image);
        textView = rootView.findViewById(R.id.tvName);
        imageView.setImageResource(travel.getImage());

        return rootView;
    }

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(getContext()).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);

    }
}