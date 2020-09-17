package com.namviet.vtvtravel.viewmodel.f2video;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2video.DetailVideoResponse;
import com.namviet.vtvtravel.response.travelnews.DetailNewsCategoryResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class SubVideoViewModel extends BaseViewModel {

    public void getDetailVideo(String link, boolean isLoadMore) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.getDetailCategoryVideo(link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetailVideoResponse>() {
                    @Override
                    public void accept(DetailVideoResponse response) throws Exception {
                        if (response != null && null != response.getData()) {
                            response.setLoadMore(isLoadMore);
                            requestSuccess(response);
                        }else {
                            requestSuccess(null);
                        }
                    }
                }, throwable -> {
                    requestFailed(throwable);
                });

        compositeDisposable.add(disposable);
    }

    public void getVideoByTag(String idTag) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Map<String, Object> queryMap = Param.getVideoByTag(idTag);

        Disposable disposable = newsService.getVideoByTag(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetailVideoResponse>() {
                    @Override
                    public void accept(DetailVideoResponse response) throws Exception {
                        if (response != null && null != response.getData()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, throwable -> {
                    requestFailed(throwable);
                });

        compositeDisposable.add(disposable);
    }

    private void requestSuccess(Object object) {
        setChanged();
        notifyObservers(object);
    }

    private void requestFailed(Throwable throwable) {
        try {
            onLoadFail();
        } catch (Exception e) {

        }

        try {
            HttpException error = (HttpException) throwable;
            String errorBody = error.response().errorBody().string();
            ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);
            setChanged();
            notifyObservers(errorResponse);
        } catch (Exception e) {
            setChanged();
            notifyObservers();
        }
    }
}
