package com.namviet.vtvtravel.viewmodel;

import android.content.Context;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.response.ShowAllResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ShowAllViewModel extends BaseViewModel {
    private List<Travel> travelList;

    public ShowAllViewModel(Context mContext) {
        this.context = mContext;
        travelList = new ArrayList<>();
    }


    public void getListAll(String url, int page) {
        Map<String, Object> param = Param.getShowAll(page);
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getListAll(url, param)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShowAllResponse>() {
                    @Override
                    public void accept(ShowAllResponse showAllResponse) throws Exception {
                        if (showAllResponse != null && null != showAllResponse.getData()) {
                            changeListAll(showAllResponse.getData().getItems());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
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
                            changeListAll(allResponse.getData().getItems());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void changeListAll(List<Travel> travelList) {
        this.travelList.addAll(travelList);
        setChanged();
        notifyObservers();
    }

    public List<Travel> getTravelList() {
        return travelList;
    }


    public void clearList() {
        this.travelList.clear();
    }


    @Override
    public String getTitleToolBar() {
        return super.getTitleToolBar();
    }

    @Override
    public void setTitleToolBar(String title) {
        super.setTitleToolBar(title);
    }

    private void requestFailed(Throwable throwable) {
        try {
            onLoadFail();
        } catch (Exception e) {

        }
        try {
            setChanged();
            notifyObservers(ResponseUltils.requestFailed(throwable));
        } catch (Exception e) {
            setChanged();
            notifyObservers();
        }
    }
}
