package com.namviet.vtvtravel.viewmodel.f2video;

import android.util.Log;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse;
import com.namviet.vtvtravel.response.f2video.VideoResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class VideoViewModel extends BaseViewModel {
    public void getCategoryVideo() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getCategoryVideo(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VideoResponse>() {
                    @Override
                    public void accept(VideoResponse videoResponse) throws Exception {
                        if (videoResponse != null) {
                            requestSuccess(videoResponse);
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
    private void requestSuccess(Object object) {
        setChanged();
        notifyObservers(object);
    }

    private void requestFailed(Throwable throwable) {
        try {
            onLoadFail();
        } catch (Exception e) {
            Log.e("xxx", "requestFailed: " );
        }

//        try {
//            HttpException error = (HttpException) throwable;
//            String errorBody = error.response().errorBody().string();
//            ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);
//            setChanged();
//            notifyObservers(errorResponse);
//        } catch (Exception e) {
//            setChanged();
//            notifyObservers();
//        }
        try {
            ErrorResponse errorResponse = new ErrorResponse();
            setChanged();
            notifyObservers(errorResponse);
        }
        catch (Exception e){

        }
    }
}
