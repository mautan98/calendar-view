package com.namviet.vtvtravel.viewmodel;

import android.content.Context;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class RegisterViewModel extends BaseViewModel {

    public RegisterViewModel(Context context) {
        this.context = context;
    }

    public void register(String mobile, String pass) {

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.register(mobile, pass)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.register(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
//                        if (null != baseResponse && baseResponse.isSuccess() && null != baseResponse.getData()) {
//                            loginSuccess(baseResponse.getData());
//                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        loginSuccess(null);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void registerSuccess(Account account) {
        setChanged();
        notifyObservers();
    }

}
