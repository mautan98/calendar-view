package com.namviet.vtvtravel.viewmodel;

import android.content.Context;

import com.baseapp.utils.L;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelFactory;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.CustomGallery;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ShareMomentResponse;
import com.namviet.vtvtravel.response.TypeMomentResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ShareMomentViewModel extends BaseViewModel {

    public ShareMomentViewModel(Context context) {
        this.context = context;
    }

    public void shareMoment(CustomGallery cover, ArrayList<CustomGallery> listPhoto, String title, String shortDes, String description, ArrayList<String> tags, String type) {
        File fileCover = new File(cover.getSdcardPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), fileCover);
        MultipartBody.Part coverBody = MultipartBody.Part.createFormData("logo", fileCover.getName(), requestFile);

        Map<String, RequestBody> queryMap = Param.shareMoment(title, shortDes, description, listPhoto, tags, type);
        MultipartBody.Part[] surveyImagesParts = null;
        if (type.equals(Constants.TypeLeftMenu.PHOTO)) {
            surveyImagesParts = new MultipartBody.Part[listPhoto.size()];
            for (int index = 0; index < listPhoto.size(); index++) {
                File file = new File(listPhoto.get(index).getSdcardPath());
                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                surveyImagesParts[index] = MultipartBody.Part.createFormData("photo_files[]", file.getName(), surveyBody);
            }
        }

        TravelService service = TravelFactory.createService(TravelService.class);
        Call<ShareMomentResponse> call = service.shareMoment(queryMap, coverBody, surveyImagesParts);
        call.enqueue(new Callback<ShareMomentResponse>() {
            @Override
            public void onResponse(Call<ShareMomentResponse> call, retrofit2.Response<ShareMomentResponse> response) {
                ShareMomentResponse model = response.body();
                loadSuccess(model);
            }

            @Override
            public void onFailure(Call<ShareMomentResponse> call, Throwable t) {
                requestFailed(t);
            }
        });
    }

    public void loadTypeMoment() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.loadTypeMoment()
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TypeMomentResponse>() {
                    @Override
                    public void accept(TypeMomentResponse filterResponse) throws Exception {
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
