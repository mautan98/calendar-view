package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Details;
import com.namviet.vtvtravel.response.DetailResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.view.MainActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DetailViewModel extends BaseViewModel {
    private Details mDetails;

    public DetailViewModel(Context context) {
        this.context = context;
    }

    public void loadDetailNews(String url) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.getDetailNews(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetailResponse>() {
                    @Override
                    public void accept(DetailResponse detailResponse) throws Exception {
                        if (detailResponse != null && null != detailResponse.getData()) {
                            changeDetails(detailResponse.getData());
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

    private void changeDetails(Details details) {
        this.mDetails = details;
        setChanged();
        notifyObservers();
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

    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) context;
        if (view.getId() == R.id.tvSearch) {
            mainActivity.switchFragment(SlideMenu.MenuType.SEARCH_SCREEN);
        }
    }


    public Details getDetails() {
        return mDetails;
    }

    @BindingAdapter("imageUrl")
    public static void bannerImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
