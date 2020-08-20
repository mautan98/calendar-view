package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.view.View;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.listener.F2LoadFailListener;
import com.namviet.vtvtravel.model.f2event.OnLoadFail;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.view.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class BaseViewModel extends Observable implements F2LoadFailListener {
    private static BaseViewModel baseViewModel;

    public static BaseViewModel getInstance() {
        if (baseViewModel == null) {
            baseViewModel = new BaseViewModel();
        }
        return baseViewModel;
    }

    private String title = "Địa điểm";

    public String getTitleToolBar() {
        return title;
    }

    public void setTitleToolBar(String title) {
        this.title = title;
    }

    public void onClickBack(View view) {
    }

    protected Context context;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }

    public void trackLocation(double lat, double lng, String deviceId) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.trackLocation(lat, lng, "APP", "ANDROID", deviceId)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {


                }, throwable -> {
                });

        compositeDisposable.add(disposable);

    }

    @Override
    public void onLoadFail() {
        EventBus.getDefault().post(new OnLoadFail());
    }

}
