package com.namviet.vtvtravel.tracking;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.response.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TrackingViewModel {

    private static TrackingViewModel trackingViewModel;
    public static CompositeDisposable compositeDisposable;

    public static TrackingViewModel getInstance() {
        if (trackingViewModel == null) {
            trackingViewModel = new TrackingViewModel();
        }
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return trackingViewModel;
    }


    public void trackEvent(String eventName) {
        try {
            MyApplication myApplication = MyApplication.getInstance();
            TravelService newsService = myApplication.getTravelServiceAcc();

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    Param.getParams2(Param.postEvent(eventName)).toString());

            Disposable disposable = newsService.postEvent(requestBody)
                    .subscribeOn(myApplication.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseResponse -> {
                    }, throwable -> {
                    });
            compositeDisposable.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void trackEvent2(String eventName, Tracking tracking) {
        try {
            MyApplication myApplication = MyApplication.getInstance();
            TravelService newsService = myApplication.getTravelServiceAcc();

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    Param.getParams2(Param.postEvent2(eventName, tracking)).toString());

            Disposable disposable = newsService.postEvent(requestBody)
                    .subscribeOn(myApplication.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseResponse -> {
                        BaseResponse response = baseResponse;
                    }, throwable -> {
                        String a = throwable.toString();
                    });
            compositeDisposable.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
