package com.namviet.vtvtravel.viewmodel;

import android.content.Context;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.response.ShowAllResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NearYouViewModel extends BaseViewModel {
    private List<Travel> travelList;

    public NearYouViewModel(Context context) {
        this.context = context;
    }

    public void loadNearYout(int page, String type, String distance, double lat, double log) {
        Map<String, Object> param = Param.getNearYou(page, type, distance, lat, log);
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getNearYou(param)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShowAllResponse>() {
                    @Override
                    public void accept(ShowAllResponse allResponse) throws Exception {
                        if (allResponse != null && null != allResponse.getData()) {
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

        compositeDisposable.add(disposable);
    }
}
