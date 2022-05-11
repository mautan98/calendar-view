package com.namviet.vtvtravel.viewmodel;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ChatResponse;
import com.namviet.vtvtravel.response.CreateRoomResponse;
import com.namviet.vtvtravel.response.DetailScheduleCreateResponse;
import com.namviet.vtvtravel.response.f2chat.GetListThemeResponse;
import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;
import com.namviet.vtvtravel.response.f2chat.GetUserThemeResponse;
import com.namviet.vtvtravel.response.f2chat.HelloMessageResponse;
import com.namviet.vtvtravel.response.f2chat.PostUserGuildResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class ChatViewModel extends BaseViewModel {

    public void loadItemChat(String mess, String sessionId, String token) {
        Map<String, Object> map;
        MyLocation myLocation = MyApplication.getInstance().getMyLocation();
        if (myLocation != null) {
            map = Param.getChatMessage(mess, sessionId, myLocation.getLat() + "", myLocation.getLog() + "", token);
        } else {
            map = Param.getChatMessage(mess, sessionId, "", "", token);
        }
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.getChatBotData(map)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ChatResponse>() {
                    @Override
                    public void accept(ChatResponse baseResponse) throws Exception {
                        if (null != baseResponse) {
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

    public void createRoom(String mobile, String fullname) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceChat();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.createRoom(mobile, fullname).toString());
        Disposable disposable = newsService.createRoomChat(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CreateRoomResponse>() {
                    @Override
                    public void accept(CreateRoomResponse placeResponse) throws Exception {
                        if (placeResponse != null && null != placeResponse.getData()) {
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

    public void getUserGuild() {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(Param.userGuideNew("")).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.getUserGuild(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetUserGuildResponse>() {
                    @Override
                    public void accept(GetUserGuildResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
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

    public void helloMessage() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.helloMessage()
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HelloMessageResponse>() {
                    @Override
                    public void accept(HelloMessageResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
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

    public void postUserGuild(String code) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(Param.userGuideNew(code)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
//        Map<String, Object> map = Param.postUserGuild(code);
        Disposable disposable = newsService.postUserGuild(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PostUserGuildResponse>() {
                    @Override
                    public void accept(PostUserGuildResponse response) throws Exception {
                        if (response != null && null != response.getData() && response.isSuccess()) {
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

    public void yesNoReview(String satisfied) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(Param.yesNoReview(satisfied)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.yesNoReview(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    loadSuccess(new BaseResponse());
                }, throwable -> {
                });

        compositeDisposable.add(disposable);
    }

    public void getUserTheme() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(new JSONObject()).toString());
        Disposable disposable = newsService.getUserTheme(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetUserThemeResponse>() {
                    @Override
                    public void accept(GetUserThemeResponse response) throws Exception {
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
