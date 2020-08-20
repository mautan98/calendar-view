package com.namviet.vtvtravel.view.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.CityAdapter;
import com.namviet.vtvtravel.adapter.WeatherAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.DialogCityBinding;
import com.namviet.vtvtravel.databinding.DialogWeatherBinding;
import com.namviet.vtvtravel.listener.CitySelectListener;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.ItemWeather;
import com.namviet.vtvtravel.model.Weather;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.ArrayList;
import java.util.List;

public class WeatherDialog extends BaseDialogFragment {
    private DialogWeatherBinding binding;
    private WeatherAdapter weatherAdapter;
    private ArrayList<ItemWeather> itemWeather;
    private CitySelectListener citySelectListener;

    public void setCitySelectListener(CitySelectListener citySelectListener) {
        this.citySelectListener = citySelectListener;
    }

    public static WeatherDialog newInstance(ArrayList<ItemWeather> itemWeather) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.IntentKey.KEY_DIALOG, itemWeather);
        WeatherDialog cityDialogFragment = new WeatherDialog();
        cityDialogFragment.setArguments(bundle);
        return cityDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            itemWeather = getArguments().getParcelableArrayList(Constants.IntentKey.KEY_DIALOG);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_weather, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected void initView(View v) {
        binding.ivClose.setOnClickListener(this);
        binding.rvWeather.setLayoutManager(new LinearLayoutManager(getContext()));
        if (null != itemWeather && itemWeather.size() > 0) {
            weatherAdapter = new WeatherAdapter(getContext(), itemWeather.subList(1, itemWeather.size()));
            binding.rvWeather.setAdapter(weatherAdapter);
        }


        updateView();
    }

    protected void updateView() {
        if (null != itemWeather) {
            binding.tvTemp.setText(Math.round(itemWeather.get(0).getTemp_min()) + " - " + Math.round(itemWeather.get(0).getTemp_max()) + " °C");
            setImageUrl(itemWeather.get(0).getWeather().getIcon_url(), binding.ivConTemp);
            binding.tvDate.setText(DateUtltils.timeToStringWeather(itemWeather.get(0).getDate()));
        }


    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivClose) {
            dismiss();
        }
    }
}
