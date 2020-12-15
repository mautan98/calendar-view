package com.namviet.vtvtravel.viewmodel.f2chat;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ChatResponse;
import com.namviet.vtvtravel.response.CreateRoomResponse;
import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;
import com.namviet.vtvtravel.response.f2chat.HelloMessageResponse;
import com.namviet.vtvtravel.response.f2chat.PostUserGuildResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class ReviewChatViewModel extends BaseViewModel {

    public void reviewChat(String admin, String note, List<String> reasons, String rate) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(Param.reviewChat(admin, note, reasons, rate)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.reviewChat(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        loadSuccess(baseResponse);
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
