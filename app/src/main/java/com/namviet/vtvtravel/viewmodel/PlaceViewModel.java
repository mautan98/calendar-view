package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.util.Log;

import com.baseapp.utils.L;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.GroupSchedule;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.CategoryResponse;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.CommentResponse;
import com.namviet.vtvtravel.response.DetailPlaceResponse;
import com.namviet.vtvtravel.response.DetailScheduleCreateResponse;
import com.namviet.vtvtravel.response.DetailScheduleResponse;
import com.namviet.vtvtravel.response.FilterData;
import com.namviet.vtvtravel.response.FilterResponse;
import com.namviet.vtvtravel.response.NewestResponse;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.response.PostCommentResponse;
import com.namviet.vtvtravel.response.SaveScheduleResponse;
import com.namviet.vtvtravel.response.ScheduleResponse;
import com.namviet.vtvtravel.response.SuggestTravelResponse;
import com.namviet.vtvtravel.response.VideoMomentResponse;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class PlaceViewModel extends BaseViewModel {
    public PlaceViewModel(Context mContext) {
        this.context = mContext;
    }

    public void loadPlace(City city, ArrayList<FilterData> list, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.loadPlace(city, list, page);
        Disposable disposable = newsService.loadPlace(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaceResponse>() {
                    @Override
                    public void accept(PlaceResponse placeResponse) throws Exception {
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
                            loadSuccess(cityResponse);
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

    public void loadFilter(String category) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.loadFilter(category);
        Disposable disposable = newsService.loadFilter(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilterResponse>() {
                    @Override
                    public void accept(FilterResponse filterResponse) throws Exception {
                        if (filterResponse != null && null != filterResponse.getData()) {
                            loadSuccess(filterResponse);
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

    public void loadDetailPlace(String url) {
        Log.d("LamLV", url);
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.loadDetailPlace(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetailPlaceResponse>() {
                    @Override
                    public void accept(DetailPlaceResponse detailResponse) throws Exception {
                        if (detailResponse != null && null != detailResponse.getData()) {
                            loadSuccess(detailResponse);
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

    public void loadNearPlace(String link) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.loadNearPlace(link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaceResponse>() {
                    @Override
                    public void accept(PlaceResponse placeResponse) throws Exception {
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

    public void loadRestaurants(City city, ArrayList<FilterData> listFilterData, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.loadPlace(city, listFilterData, page);
        Disposable disposable = newsService.loadRestaurants(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaceResponse>() {
                    @Override
                    public void accept(PlaceResponse placeResponse) throws Exception {
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

    public void loadWhereStay(City city, ArrayList<FilterData> listFilterData, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.loadPlace(city, listFilterData, page);
        Disposable disposable = newsService.loadWhereStay(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaceResponse>() {
                    @Override
                    public void accept(PlaceResponse placeResponse) throws Exception {
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

    public void loadCategoryHighLight() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.loadCategoryHighLight(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CategoryResponse>() {
                    @Override
                    public void accept(CategoryResponse cityResponse) throws Exception {
                        if (cityResponse != null) {
                            loadSuccess(cityResponse);
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

    public void loadWhatPlay(City city, ArrayList<FilterData> listFilterData, int i) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.loadPlace(city, listFilterData, i);
        Disposable disposable = newsService.loadWhatPlay(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaceResponse>() {
                    @Override
                    public void accept(PlaceResponse placeResponse) throws Exception {
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

    public void loadVoucher(ArrayList<FilterData> listFilterData, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.loadPlace(null, listFilterData, page);
        Disposable disposable = newsService.loadVoucher(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaceResponse>() {
                    @Override
                    public void accept(PlaceResponse placeResponse) throws Exception {
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

    public void loadMoment(int page, int limit) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        if (page > 1) {
            queryMap.put(WSConfig.KeyParam.PAGE, page);
            queryMap.put(WSConfig.KeyParam.LIMIT, limit);
        }
        Disposable disposable = newsService.loadMoment(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaceResponse>() {
                    @Override
                    public void accept(PlaceResponse placeResponse) throws Exception {
                        if (placeResponse != null && null != placeResponse.getData()) {
                            loadSuccess(placeResponse);
                        } else {
                            loadSuccess(null);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        L.e("Throwable " + throwable.toString());
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void loadMomentNewest() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.loadMomentNewest(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewestResponse>() {
                    @Override
                    public void accept(NewestResponse placeResponse) throws Exception {
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

    public void loadVideoMoment() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.loadMomentVideo(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VideoMomentResponse>() {
                    @Override
                    public void accept(VideoMomentResponse placeResponse) throws Exception {
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

    public void loadTourSuggest(City regions, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.loadTourSuggest(regions, page)).toString());
        Disposable disposable = newsService.loadTourSuggest(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ScheduleResponse>() {
                    @Override
                    public void accept(ScheduleResponse placeResponse) throws Exception {
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

    public void loadDetailSchedule(String id) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.loadDetailSchedule(id)).toString());
        Disposable disposable = newsService.loadDetailSchedule(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetailScheduleResponse>() {
                    @Override
                    public void accept(DetailScheduleResponse placeResponse) throws Exception {
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

    public void loadScheduleRestaurant(double lat, double log, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        Map<String, Object> queryMap = Param.page(page);
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.loadNearByLocation(lat, log, page)).toString());
        Disposable disposable = newsService.loadScheduleRestaurant(jsonBodyObject, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ScheduleResponse>() {
                    @Override
                    public void accept(ScheduleResponse placeResponse) throws Exception {
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

    public void loadSchedulePlace(double lat, double log, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        Map<String, Object> queryMap = Param.page(page);
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.loadNearByLocation(lat, log, page)).toString());
        Disposable disposable = newsService.loadSchedulePlace(jsonBodyObject, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ScheduleResponse>() {
                    @Override
                    public void accept(ScheduleResponse placeResponse) throws Exception {
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

    public void loadScheduleCenter(double lat, double log, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        Map<String, Object> queryMap = Param.page(page);
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.loadNearByLocation(lat, log, page)).toString());
        Disposable disposable = newsService.loadScheduleCenter(jsonBodyObject, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ScheduleResponse>() {
                    @Override
                    public void accept(ScheduleResponse placeResponse) throws Exception {
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

    public void saveScheduleTravel(int id, ArrayList<GroupSchedule> groupSchedules) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.saveScheduleTravel(id, groupSchedules)).toString());
        Disposable disposable = newsService.saveScheduleTravel(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SaveScheduleResponse>() {
                    @Override
                    public void accept(SaveScheduleResponse placeResponse) throws Exception {
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

    public void loadScheduleCreate(Integer id) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.getUserId(id)).toString());
        Disposable disposable = newsService.loadScheduleCreate(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SaveScheduleResponse>() {
                    @Override
                    public void accept(SaveScheduleResponse placeResponse) throws Exception {
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

    public void loadTourCreate(Integer id) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.getUserId(id)).toString());
        Disposable disposable = newsService.loadTourCreate(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SaveScheduleResponse>() {
                    @Override
                    public void accept(SaveScheduleResponse placeResponse) throws Exception {
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

    public void saveTourTravel(Integer id, String tourId, String startTour, String endTour, boolean isCheckNotify) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.saveTourTravel(id, tourId, startTour, endTour, isCheckNotify)).toString());
        Disposable disposable = newsService.saveTourTravel(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SaveScheduleResponse>() {
                    @Override
                    public void accept(SaveScheduleResponse placeResponse) throws Exception {
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

    public void loadDetailScheduleCreate(String id) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.loadDetailSchedule(id)).toString());
        Disposable disposable = newsService.loadDetailScheduleCreate(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetailScheduleCreateResponse>() {
                    @Override
                    public void accept(DetailScheduleCreateResponse placeResponse) throws Exception {
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

    public void loadWeather(City city) {
        String url = WSConfig.HOST+"weather/forecast";
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

    public void loadYourMoment() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.loadYourMoment(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaceResponse>() {
                    @Override
                    public void accept(PlaceResponse placeResponse) throws Exception {
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

    public void loadHelloLocation(){
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getHelloLocation(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SuggestTravelResponse>() {
                    @Override
                    public void accept(SuggestTravelResponse suggestTravelResponse) throws Exception {
                        if (suggestTravelResponse != null) {
                            loadSuccess(suggestTravelResponse);
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
