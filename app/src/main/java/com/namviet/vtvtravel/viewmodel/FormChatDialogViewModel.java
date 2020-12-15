package com.namviet.vtvtravel.viewmodel;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.CreateRoomResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class FormChatDialogViewModel extends BaseViewModel {

    public void sendFormChat(String username, String mobile, String timeContact, String startTime, String endTime, String content) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.sendFormChat(username, mobile, timeContact, startTime, endTime, content, "").toString());
        Disposable disposable = newsService.sendFormChat(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        if (baseResponse != null) {
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