package com.namviet.vtvtravel.viewmodel.imagepart;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.imagepart.ImagePartResponse;
import com.namviet.vtvtravel.response.imagepart.ItemImagePartResponse;
import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class ImagePartViewModel extends BaseViewModel {

    public void getItemGallery(String galleryId) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Map<String, Object> queryMap = Param.getGallery(galleryId);
        Disposable disposable = newsService.getGalleryById(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ItemImagePartResponse>() {
                    @Override
                    public void accept(ItemImagePartResponse response) throws Exception {
                        if (response != null) {
                            requestSuccess(response);
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

    public void getGalleryByUrl(String url, boolean isLoadMore) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.getGalleryByUrl(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ItemImagePartResponse>() {
                    @Override
                    public void accept(ItemImagePartResponse response) throws Exception {
                        if (response != null) {
                            response.setLoadMore(true);
                            requestSuccess(response);
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

    public void getGallery(boolean isLoadMore) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getGallery(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ImagePartResponse>() {
                    @Override
                    public void accept(ImagePartResponse response) throws Exception {
                        if (response != null) {
                            response.setLoadMore(isLoadMore);
                            requestSuccess(response);
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


    public void getGalleryMore(String url, boolean isLoadMore) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getGalleryMore(url, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ImagePartResponse>() {
                    @Override
                    public void accept(ImagePartResponse response) throws Exception {
                        if (response != null) {
                            response.setLoadMore(isLoadMore);
                            requestSuccess(response);
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
        setChanged();
        notifyObservers(new ErrorResponse());
    }
}
