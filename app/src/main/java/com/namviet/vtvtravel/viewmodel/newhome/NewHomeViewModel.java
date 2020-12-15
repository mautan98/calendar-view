package com.namviet.vtvtravel.viewmodel.newhome;

import com.google.gson.Gson;
import com.namviet.vtvtravel.adapter.newhome.NewHomeAdapter;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.SuggestTravelResponse;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2menu.MenuResponse;
import com.namviet.vtvtravel.response.f2systeminbox.CountSystemInbox;
import com.namviet.vtvtravel.response.f2systeminbox.SystemInbox;
import com.namviet.vtvtravel.response.newhome.BaseResponseNewHome;
import com.namviet.vtvtravel.response.newhome.BaseResponseSecondNewHome;
import com.namviet.vtvtravel.response.newhome.BaseResponseSpecialNewHome;
import com.namviet.vtvtravel.response.newhome.ConfigPopupResponse;
import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.response.newhome.MobileFromViettelResponse;
import com.namviet.vtvtravel.response.newhome.SettingResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class NewHomeViewModel extends BaseViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public void getSystemInboxCount() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.getCountSystemInbox()
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CountSystemInbox>() {
                    @Override
                    public void accept(CountSystemInbox countSystemInbox) throws Exception {
                        if (countSystemInbox != null) {
                            if (countSystemInbox.isSuccess()) {
                                requestSuccess(countSystemInbox);
                            }
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


    public void getConfigRegion() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getConfigPopup(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ConfigPopupResponse>() {
                    @Override
                    public void accept(ConfigPopupResponse suggestTravelResponse) throws Exception {
                        if (suggestTravelResponse != null) {
                            requestSuccess(suggestTravelResponse);
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

    public void getHomeService() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getHomeService(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeServiceResponse>() {
                    @Override
                    public void accept(HomeServiceResponse homeServiceResponse) throws Exception {
                        if (homeServiceResponse != null) {
                            requestSuccess(homeServiceResponse);
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

    public void appFavoriteDestination(String view_link, String code) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.appFavoriteDestination(view_link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponseNewHome>() {
                    @Override
                    public void accept(BaseResponseNewHome baseResponse) throws Exception {
                        if (baseResponse != null) {
                            baseResponse.setCodeData(code);
                            NewHomeViewModel.this.requestSuccess(baseResponse);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, throwable -> {
                    requestFailed(throwable);
                });

        compositeDisposable.add(disposable);
    }

    public void appFavoriteDestinationForAppDeal(String view_link, String code) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.appFavoriteDestinationForAppDeal(view_link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponseSpecialNewHome>() {
                    @Override
                    public void accept(BaseResponseSpecialNewHome baseResponse) throws Exception {
                        if (baseResponse != null) {
                            baseResponse.setCodeData(code);
                            NewHomeViewModel.this.requestSuccess(baseResponse);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, throwable -> {
                    requestFailed(throwable);
                });

        compositeDisposable.add(disposable);
    }


    public void getDataSecond(String view_link, String code, boolean isSave) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.getDataFloorSecond(view_link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponseSecondNewHome>() {
                    @Override
                    public void accept(BaseResponseSecondNewHome baseResponse) throws Exception {
                        try {
                            if (baseResponse != null) {
                                baseResponse.setCodeData(code);
                                baseResponse.setSave(isSave);
                                NewHomeViewModel.this.requestSuccess(baseResponse);
                            } else {
                                requestSuccess(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, throwable -> {
                    requestFailed(throwable);
                });

        compositeDisposable.add(disposable);
    }

    public void getMobileFromViettel() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getMobileFromViettel().toString());

        Disposable disposable = newsService.getMobileFromViettel(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MobileFromViettelResponse>() {
                    @Override
                    public void accept(MobileFromViettelResponse mobileFromViettelResponse) throws Exception {
                        if (mobileFromViettelResponse != null && mobileFromViettelResponse.isSuccess()) {
                            requestSuccess(mobileFromViettelResponse);
                        } else {
                            requestSuccess(newsService);
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

    public void getSetting() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.getSetting()
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MenuResponse>() {
                    @Override
                    public void accept(MenuResponse settingResponse) throws Exception {
                        if (settingResponse != null) {
                            requestSuccess(settingResponse);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, throwable -> {
                    requestFailed(throwable);
                });

        compositeDisposable.add(disposable);
    }


    public void loadWeather(City city) {
        String url = WSConfig.HOST + "weather/forecast";
        if (null != city) {
            url = url + "/" + city.getId();
        }
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.loadWeather(url, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResponse>() {
                    @Override
                    public void accept(WeatherResponse placeResponse) throws Exception {
                        if (placeResponse != null && null != placeResponse.getData()) {
                            requestSuccess(placeResponse);
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

    public void loadCity() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.loadCity(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CityResponse>() {
                    @Override
                    public void accept(CityResponse cityResponse) throws Exception {
                        if (cityResponse != null && null != cityResponse.getData()) {
                            requestSuccess(cityResponse);
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
