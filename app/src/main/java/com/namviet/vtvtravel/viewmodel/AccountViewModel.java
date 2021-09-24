package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.baseapp.utils.L;
import com.google.android.gms.common.internal.ResourceUtils;
import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelFactory;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;

public class AccountViewModel extends BaseViewModel {
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Account mAccount;
    private Account mRegister;
    private BaseResponse baseResponse;

    public AccountViewModel() {

    }

    public void login(String mobile, String pass, String deviceToken) {
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.login(mobile, pass, deviceToken)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.login(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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


    public void refreshToken() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAccNoneToken();
        String refreshToken = String.valueOf(MyApplication.getInstance().getAccount().getRefreshToken());

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(new JSONObject()).toString());


        Disposable disposable = newsService.refreshToken(jsonBodyObject, refreshToken)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse response) throws Exception {
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




    public void register(String mobile, String name, String packageCode) {

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.register(mobile, name, packageCode)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.register(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    public void resendOtp(String mobile) {

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.resendOtp(mobile)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.resend(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void verifyOtpRegister(String mobile, String otp, String packageCode) {

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.verifyOtp(mobile, otp, packageCode)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.registerOtp(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    public void verifyOtpResetPass(String mobile, String otp, String packageCode) {
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.verifyOtp(mobile, otp, packageCode)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.verifyResetPasswordOtp(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    private void loginSuccess(Account account) {
        this.mAccount = account;
        setChanged();
        notifyObservers();
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


    public Account getAccount() {
        return mAccount;
    }

    public Account getRegister() {
        return mRegister;
    }

    public void setPassRegister(Integer id, String mobile, String pass, String packageCode, String fullName) {
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.setPassRegister(id, mobile, pass, packageCode, fullName)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.registerAccount(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    public void setPassReset(Integer id, String mobile, String pass, String token) {
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.setPassReset(id, mobile, pass)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.resetPass(token, jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    public void resetPassword(String mobile) {
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.resetPassword(mobile)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.resetPassword(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    public void updateInfo(Integer id, String name, String mobile, String mail, String birthday, Integer gender, String address, String cmnd, String about) {

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.updateInfo(id, name, mobile, mail, birthday, gender, address, cmnd, about)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.updateInfo(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    public void changePassword(String token, String oldPass, String newPass, String mobile) {
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.changePassword(oldPass, newPass, mobile)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.changePassword(token, jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    public void loginGoogle(String uid, String displayName, String email, String avatar) {
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.loginGoogle(uid, displayName, email, avatar)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.login(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    public void loginFacebook(String id, String fullName, String email) {
        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.loginFace(id, fullName, email)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.login(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
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

    public void notificationReg(String deviceID, String token, String platform) {
        Map<String, Object> queryMap = Param.notificationReg(deviceID, token, platform);
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.notificationReg(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        L.e("Success");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        L.e("Throwable " + throwable.toString());
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void updateAvatar(File file_param, Account account, String type) {
        final File file = file_param;
        Map<String, Object> queryMap = Param.uploadAvatar(account.getId());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(type, file.getName(), requestFile);
        MyApplication myApplication = MyApplication.getInstance();
        TravelService service = TravelFactory.createServiceAcc(TravelService.class);
        Disposable disposable = service.uploadAvatar(queryMap, body)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccountResponse>() {
                    @Override
                    public void accept(AccountResponse response) throws Exception {
                        if (response != null && null != response.getData() && response.isSuccess()) {
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

}
