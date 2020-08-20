package com.namviet.vtvtravel.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentDetailsBinding;
import com.namviet.vtvtravel.model.Details;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.response.DetailResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.viewmodel.DetailViewModel;

import java.util.Observable;
import java.util.Observer;

public class DetailsFragment extends MainFragment implements Observer {

    private FragmentDetailsBinding binding;
    private Travel mTravel;
    private DetailViewModel detailViewModel;

    public static DetailsFragment newInstance(Bundle bundle) {
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mTravel = getArguments().getParcelable(Constants.IntentKey.KEY_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        detailViewModel = new DetailViewModel(getContext());
        binding.setDetailViewModel(detailViewModel);
        detailViewModel.addObserver(this);
        binding.webDes.getSettings().setJavaScriptEnabled(true);
        updateViews();

    }

    @Override
    protected void updateViews() {
        super.updateViews();
        binding.toolBar.tvTitle.setText(MyApplication.getInstance().getTitleDetail());
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.toolBar.ivSearch.setOnClickListener(this);
        if (mTravel != null) {
            detailViewModel.loadDetailNews(mTravel.getDetail_link());
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof DetailViewModel) {
            Details details = detailViewModel.getDetails();
            if (!details.getBanner_url().equals("")) {
                binding.ivBanner.setVisibility(View.VISIBLE);
                detailViewModel.bannerImage(binding.ivBanner, details.getBanner_url());
            } else {
                binding.ivBanner.setVisibility(View.GONE);
            }
            if (null != details.getMap_url()) {
                binding.tvMap.setVisibility(View.VISIBLE);
                binding.ivMap.setVisibility(View.VISIBLE);
                detailViewModel.bannerImage(binding.ivMap, details.getMap_url());
            }

            binding.tvTime.setText(DateUtltils.timeToString(details.getCreated()));
            binding.tvTitleNews.setText(details.getName());
            binding.webDes.loadData(details.getDescription(), "text/html; charset=utf-8", "UTF-8");
        } else if (o instanceof ResponseError) {
            ResponseError responseError = (ResponseError) o;
            showMessage(responseError.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        detailViewModel.reset();
    }
}
