package com.namviet.vtvtravel.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baseapp.utils.L;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.encode.RSA;
import com.namviet.vtvtravel.encode.RsaExample;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ShowAllResponse;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class EncodeDemoFragment extends MainFragment {

    private Button btEncode;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_encode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        btEncode = v.findViewById(R.id.btEncode);

        btEncode.setOnClickListener(this);
    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == btEncode) {
            String abc="aVarnlFbs25K0QyBD5TeX9hLuHQoY+BDd47ru3PXQu2phQJB/YGKgwELbkokJwTKTF/FEi8D+GraAx5HnNxK/1eA+rFTrbqvNLidzmpLdz614lSWVLfU/+gHsPmbiGhR8L2adJu2YDoXLViYqI9gLhmCWrewhPLlCYi9i5og8a8=";
            String userStr = "Demo mã hóa RSA";
            byte[] encodeStr = null;
            try {

                encodeStr = RSA.encryptByPublicKey(userStr.getBytes(), RSA.PUBLIC_KEY);
//                L.e("btEncode " + RSA.encryptBASE64(encodeStr));

                L.e("Decode " + new String(RSA.decryptByPrivateKey(RSA.decryptBASE64(abc), RSA.PRIVATE_KEY), "UTF-8"));
            }  catch (Exception e) {
                L.e("btEncode " + e.toString());
            }
            getListAll(WSConfig.URL_ENCODE);
        }
    }

    public void getListAll(String url) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.requesEncode(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse showAllResponse) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }
}
