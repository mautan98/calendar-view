package com.namviet.vtvtravel.viewmodel.f2smalllocation;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SortSmallLocationResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SmallLocationViewModel extends BaseViewModel {

//    public void getSmallLocation() {
//        MyApplication myApplication = MyApplication.getInstance();
//        TravelService newsService = myApplication.getTravelService();
//        Map<String, Object> queryMap = Param.getDefault();
//        Disposable disposable = newsService.getSmallLocation(queryMap)
//                .subscribeOn(myApplication.subscribeScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<SmallLocationResponse>() {
//                    @Override
//                    public void accept(SmallLocationResponse response) throws Exception {
//                        if (response != null) {
//                            requestSuccess(response);
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        requestFailed(throwable);
//                    }
//                });
//
//        compositeDisposable.add(disposable);
//    }

    public void getAllLocation() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getAllLocation(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AllLocationResponse>() {
                    @Override
                    public void accept(AllLocationResponse response) throws Exception {
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

    public void getFilterByCode() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getFilterByCode(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilterByCodeResponse>() {
                    @Override
                    public void accept(FilterByCodeResponse response) throws Exception {
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

    public void getSmallLocation(String url, boolean isLoadMore) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getSmallLocation(url, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SmallLocationResponse>() {
                    @Override
                    public void accept(SmallLocationResponse response) throws Exception {
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

    public void createPlace(String scheduleCustomId, String note, String stt, String arrivalTime, String freeTime, String durationVisit, String typePlace, String placeId, String detail_link, String name, String status, String day) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(Param.createPlace(scheduleCustomId, note, stt, arrivalTime, freeTime, durationVisit, typePlace, placeId, detail_link, name, status, day)).toString());
        Disposable disposable = newsService.createPlace(requestBody)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
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

    public void updateSchedulePlace(String schedulePlaceId, List<String> lstCordinate, String detailLink, String name, String logoUrl){
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        JSONObject jsonObject = new JSONObject();
        JSONObject locJsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray(lstCordinate);
        try {
            locJsonObject.put("type","Point");
            locJsonObject.putOpt("coordinates",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("id",schedulePlaceId);
            jsonObject.putOpt("loc",locJsonObject);
            jsonObject.put("detailLink",detailLink);
            jsonObject.put("name",name);
            jsonObject.put("logoUrl",logoUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject param = Param.getParams(jsonObject);
        RequestBody resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString());
        Disposable dispose = newsService.updateSchedulePlace(resquestBody)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        requestSuccess(baseResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });
        compositeDisposable.add(dispose);
    }
    public void sortSmallLocation() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.sortSmallLocation()
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SortSmallLocationResponse>() {
                    @Override
                    public void accept(SortSmallLocationResponse response) throws Exception {
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
