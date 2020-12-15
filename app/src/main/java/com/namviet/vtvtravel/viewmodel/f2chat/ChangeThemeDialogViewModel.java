package com.namviet.vtvtravel.viewmodel.f2chat;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.f2chat.GetListThemeResponse;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2comment.CreateCommentResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class ChangeThemeDialogViewModel extends BaseViewModel {

    public void getListTheme() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(new JSONObject()).toString());
        Disposable disposable = newsService.getListTheme(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetListThemeResponse>() {
                    @Override
                    public void accept(GetListThemeResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            loadSuccess(response);
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

    public void updateTheme(String themeId) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.updateTheme(themeId)).toString());
        Disposable disposable = newsService.updateTheme(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    loadSuccess(new BaseResponse());
                }, throwable -> {
                });

        compositeDisposable.add(disposable);
    }

    private void loadSuccess(Object obj) {
        setChanged();
        notifyObservers(obj);
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
