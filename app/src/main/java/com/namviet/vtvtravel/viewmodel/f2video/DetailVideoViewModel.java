package com.namviet.vtvtravel.viewmodel.f2video;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;
import com.namviet.vtvtravel.response.f2video.DetailVideoResponse;
import com.namviet.vtvtravel.response.f2video.VideoDetailResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class DetailVideoViewModel extends BaseViewModel {

    public void viewVideo(String url) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.viewVideo(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VideoDetailResponse>() {
                    @Override
                    public void accept(VideoDetailResponse response) throws Exception {
                        if (response != null && null != response.getData()) {
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
