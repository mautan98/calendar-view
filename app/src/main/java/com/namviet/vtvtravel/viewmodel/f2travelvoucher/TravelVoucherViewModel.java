package com.namviet.vtvtravel.viewmodel.f2travelvoucher;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelFactory;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.f2review.CreateReviewResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2review.UploadImageResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.RankVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.RegionVoucherResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class TravelVoucherViewModel extends BaseViewModel {

    public void getOwnedVoucher(String service, String sort, String regionId, String memberRankId, String categoryId, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getMobileFromViettel().toString());
        Map<String, Object> queryMap = Param.getOwnedVoucher(service, sort, regionId, memberRankId, categoryId,  page);

        Disposable disposable = newsService.getOwnedVoucher(jsonBodyObject, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListVoucherResponse>() {
                    @Override
                    public void accept(ListVoucherResponse response) throws Exception {
                        if (response.isSuccess()) {
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


    public void getOwnedVoucherStore(String service, String sort, String regionId, String memberRankId, String categoryId, int page) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getMobileFromViettel().toString());
        Map<String, Object> queryMap = Param.getOwnedVoucher(service, sort, regionId, memberRankId, categoryId,  page);

        Disposable disposable = newsService.getOwnedVoucherStore(jsonBodyObject, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListVoucherResponse>() {
                    @Override
                    public void accept(ListVoucherResponse response) throws Exception {
                        if (response.isSuccess()) {
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

    public void getCategoryVoucher() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(
                new JSONObject()).toString());
        Disposable disposable = newsService.getCategoryVoucher(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CategoryVoucherResponse>() {
                    @Override
                    public void accept(CategoryVoucherResponse response) throws Exception {
                        if (response.isSuccess()) {
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


    public void getRank() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(
                new JSONObject()).toString());
        Disposable disposable = newsService.getRank(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RankVoucherResponse>() {
                    @Override
                    public void accept(RankVoucherResponse response) throws Exception {
                        if (response.isSuccess()) {
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

    public void getRegionVoucher() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(
                new JSONObject()).toString());
        Disposable disposable = newsService.getRegionVoucher(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegionVoucherResponse>() {
                    @Override
                    public void accept(RegionVoucherResponse response) throws Exception {
                        if (response.isSuccess()) {
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


//    public void createReview(String parentId, String userId, String content, String contentId, String contentType, String postRate, List<String> galleryUris) {
//        MyApplication myApplication = MyApplication.getInstance();
//        TravelService newsService = myApplication.getTravelServiceAcc();
//        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams(
//                Param.createReview(parentId, userId, content, contentId, contentType, postRate, galleryUris)).toString());
//        Disposable disposable = newsService.createReview(jsonBodyObject)
//                .subscribeOn(myApplication.subscribeScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<CreateReviewResponse>() {
//                    @Override
//                    public void accept(CreateReviewResponse response) throws Exception {
//                        if (response.isSuccess()) {
//                            requestSuccess(response);
//                        } else {
//                            requestSuccess(null);
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
//
//    public void getReview(String contentId, String parentId) {
//        MyApplication myApplication = MyApplication.getInstance();
//        TravelService newsService = myApplication.getTravelServiceAcc();
//        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams(
//                Param.getReview(contentId, parentId)).toString());
//        Disposable disposable = newsService.getReview(jsonBodyObject)
//                .subscribeOn(myApplication.subscribeScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<GetReviewResponse>() {
//                    @Override
//                    public void accept(GetReviewResponse response) throws Exception {
//                        if (response.isSuccess()) {
//                            requestSuccess(response);
//                        } else {
//                            requestSuccess(null);
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
//
//    public void updateImage(File file_param, String type) {
//        final File file = file_param;
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData(type, file.getName(), requestFile);
//        MyApplication myApplication = MyApplication.getInstance();
//        TravelService service = TravelFactory.createServiceAcc(TravelService.class);
//        Disposable disposable = service.uploadImage(body)
//                .subscribeOn(myApplication.subscribeScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<UploadImageResponse>() {
//                    @Override
//                    public void accept(UploadImageResponse response) throws Exception {
//                        if (response.isSuccess()) {
//                            requestSuccess(response);
//                        } else {
//                            requestSuccess(null);
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        requestFailed(throwable);
//                    }
//                });
//        compositeDisposable.add(disposable);
//    }

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
