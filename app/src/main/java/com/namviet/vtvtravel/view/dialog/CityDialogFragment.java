package com.namviet.vtvtravel.view.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.CityAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.DialogCityBinding;
import com.namviet.vtvtravel.listener.CitySelectListener;
import com.namviet.vtvtravel.model.City;

import java.util.ArrayList;

public class CityDialogFragment extends BaseDialogFragment implements CitySelectListener {
    private DialogCityBinding binding;
    private CityAdapter cityAdapter;
    private ArrayList<City> cityArrayList;
    private CitySelectListener citySelectListener;

    public void setCitySelectListener(CitySelectListener citySelectListener) {
        this.citySelectListener = citySelectListener;
    }

    public static CityDialogFragment newInstance(ArrayList<City> cities) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.IntentKey.KEY_DIALOG, cities);
        CityDialogFragment cityDialogFragment = new CityDialogFragment();
        cityDialogFragment.setArguments(bundle);
        return cityDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            cityArrayList = getArguments().getParcelableArrayList(Constants.IntentKey.KEY_DIALOG);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_city, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected void initView(View v) {
        binding.ivClose.setOnClickListener(this);
        binding.ivClear.setOnClickListener(this);
        binding.rvCity.setLayoutManager(new LinearLayoutManager(getContext()));
        cityAdapter = new CityAdapter(getContext(), cityArrayList);
        cityAdapter.setCitySelectListener(this);
        binding.rvCity.setAdapter(cityAdapter);
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (null != cityAdapter) {
                    cityAdapter.filter(charSequence.toString());
                }
                if (charSequence.length() > 0) {
                    binding.ivClear.setVisibility(View.VISIBLE);
                } else {
                    binding.ivClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        updateView();
    }

    protected void updateView() {


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSelect(ArrayList<City> list, City city) {
        if (null != citySelectListener) {
            citySelectListener.onSelect(cityAdapter.getCityList(), city);
        }
        dismiss();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivClose) {
            dismiss();
        } else if (view == binding.ivClear) {
            binding.edSearch.setText("");
        }
    }
}
