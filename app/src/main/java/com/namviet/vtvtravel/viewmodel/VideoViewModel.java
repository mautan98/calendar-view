package com.namviet.vtvtravel.viewmodel;

import android.content.Context;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.response.CategoryPhotoResponse;
import com.namviet.vtvtravel.response.NewsResponse;
import com.namviet.vtvtravel.response.PlaylistVideoResponse;
import com.namviet.vtvtravel.response.VideoResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class VideoViewModel extends BaseViewModel {

    public void loadCategoryVideo() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.loadCategoryVideo(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CategoryPhotoResponse>() {
                    @Override
                    public void accept(CategoryPhotoResponse newsResponse) throws Exception {
                        if (newsResponse != null && null != newsResponse.getData()) {
                            loadSuccess(newsResponse);
                        } else {
                            loadSuccess(null);
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

    private void loadSuccess(Object response) {
        setChanged();
        notifyObservers(response);
    }

    public void loadListVideo(String link) {
//        MyApplication myApplication = MyApplication.getInstance();
//        TravelService newsService = myApplication.getTravelService();
//        Disposable disposable = newsService.loadListVideo(link)
//                .subscribeOn(myApplication.subscribeScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<VideoResponse>() {
//                    @Override
//                    public void accept(VideoResponse baseResponse) throws Exception {
//                        if (baseResponse != null && null != baseResponse.getData()) {
//                            loadSuccess(baseResponse);
//                        } else {
//                            loadSuccess(null);
//                        }
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        requestFailed(throwable);
//                    }
//                });
//
//        compositeDisposable.add(disposable);
    }

    public void loadPlaylistVideo(String detail_link) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.loadPlaylistVideo(detail_link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaylistVideoResponse>() {
                    @Override
                    public void accept(PlaylistVideoResponse baseResponse) throws Exception {
                        if (baseResponse != null && null != baseResponse.getData()) {
                            loadSuccess(baseResponse);
                        } else {
                            loadSuccess(null);
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
