package com.namviet.vtvtravel.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.baseapp.activity.BaseActivity;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.newhome.FavorPlaceAdapter;
import com.namviet.vtvtravel.adapter.newhome.VoucherAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentHomeBinding;
import com.namviet.vtvtravel.model.newhome.Voucher;
import com.namviet.vtvtravel.widget.CustPagerTransformer;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private F2FragmentHomeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.f2_fragment_home);
        initView();
    }

    private void initView() {
//        ViewPager  viewPager = findViewById(R.id.vpSlideShow);
//        viewPager.setPageTransformer(false, new CustPagerTransformer(this));
//
//        VoucherAdapter adapter = new VoucherAdapter(getSupportFragmentManager(), getData());
//        viewPager.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.recyclerFavorPlace);
        FavorPlaceAdapter favorPlaceAdapter = new FavorPlaceAdapter(this, getData());
        recyclerView.setAdapter(favorPlaceAdapter);
    }

//    private List<Voucher> getData(){
//        List<Voucher> vouchers = new ArrayList<>();
//        for (int i = 0; i < 3; i++){
//            vouchers.add(new Voucher(R.drawable.f2_item_voucher));
//        }
//        return vouchers;
//    }

    private List<Object> getData() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            objects.add(new Object());
        }
        return objects;
    }

}
