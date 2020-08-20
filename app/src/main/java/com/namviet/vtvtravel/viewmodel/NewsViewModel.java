package com.namviet.vtvtravel.viewmodel;

import android.content.Context;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.response.BannerResponse;
import com.namviet.vtvtravel.response.CategoryPhotoResponse;
import com.namviet.vtvtravel.response.CommentResponse;
import com.namviet.vtvtravel.response.DetailNewsResponse;
import com.namviet.vtvtravel.response.NewsResponse;
import com.namviet.vtvtravel.response.PostCommentResponse;
import com.namviet.vtvtravel.response.SlideShowResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;


import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class NewsViewModel extends BaseViewModel {

    public NewsViewModel(Context mContext) {
        this.context = mContext;
    }


    public void getCategoryNews(String category, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getCategoryNews(category, page);
        Disposable disposable = newsService.getCategoryNews(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsResponse>() {
                    @Override
                    public void accept(NewsResponse newsResponse) throws Exception {
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

    public void getSlideShowNews(String category) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getCategoryNews(category, 1);
        Disposable disposable = newsService.getSlideshowNews(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SlideShowResponse>() {
                    @Override
                    public void accept(SlideShowResponse newsResponse) throws Exception {
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

    public void getNewsById(String url) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getNewsById(url + "?channel=ANDROID")
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetailNewsResponse>() {
                    @Override
                    public void accept(DetailNewsResponse baseResponse) throws Exception {
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

    public void loadCategoryPhotoNice() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getCategoryPhotoNice(queryMap)
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

    private void loadSuccess(Object obj) {
        setChanged();
        notifyObservers(obj);
    }

    public void loadListPhotoNice(String link) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.loadListPhotoNice(link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsResponse>() {
                    @Override
                    public void accept(NewsResponse baseResponse) throws Exception {
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

    public void loadSlideShow() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.loadSlideShow(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SlideShowResponse>() {
                    @Override
                    public void accept(SlideShowResponse baseResponse) throws Exception {
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

    public void loadComment(String contentId, Integer page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.commentsSearch(contentId, page)).toString());
        Disposable disposable = newsService.commentsSearch(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommentResponse>() {
                    @Override
                    public void accept(CommentResponse placeResponse) throws Exception {
                        if (placeResponse != null) {
                            loadSuccess(placeResponse);
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

    public void postComment(int userId, String content, String contentId, String contentType, Integer parentId) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.commentsCreate(userId, content, contentId, contentType, parentId)).toString());
        Disposable disposable = newsService.commentsCreate(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PostCommentResponse>() {
                    @Override
                    public void accept(PostCommentResponse placeResponse) throws Exception {
                        if (placeResponse != null) {
                            loadSuccess(placeResponse);
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

    public void getBannerList(String code) {
        String url = WSConfig.HOST + "ads/banners?uri=" + WSConfig.HOST + "news?category_code=" + code;
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getBannerList(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BannerResponse>() {
                    @Override
                    public void accept(BannerResponse baseResponse) throws Exception {
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

    public void getBannerDetail(String link) {
        String url = WSConfig.HOST + "ads/banners?uri=" + link;
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getBannerList(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BannerResponse>() {
                    @Override
                    public void accept(BannerResponse baseResponse) throws Exception {
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
