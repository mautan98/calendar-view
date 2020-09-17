package com.namviet.vtvtravel.viewmodel.f2comment;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2comment.CreateCommentResponse;
import com.namviet.vtvtravel.response.f2comment.DeleteCommentResponse;
import com.namviet.vtvtravel.response.f2comment.UpdateCommentResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class CommentViewModel extends BaseViewModel {

    public void getComment(String contentId) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.getComment(contentId)).toString());
        Disposable disposable = newsService.getComment(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommentResponse>() {
                    @Override
                    public void accept(CommentResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
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

    public void postComment(String parentId, String userId, String content, String contentId, String contentType) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.createComment(parentId, userId, content, contentId, contentType)).toString());
        Disposable disposable = newsService.createComment(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CreateCommentResponse>() {
                    @Override
                    public void accept(CreateCommentResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
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

    public void deleteComment(String id) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.deleteComment(id)).toString());
        Disposable disposable = newsService.deleteComment(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeleteCommentResponse>() {
                    @Override
                    public void accept(DeleteCommentResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
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

    public void updateComment(String id, String content) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.updateComment(id, content)).toString());
        Disposable disposable = newsService.updateComment(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateCommentResponse>() {
                    @Override
                    public void accept(UpdateCommentResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
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
