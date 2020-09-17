package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.L;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.CategoryHome;
import com.namviet.vtvtravel.model.MenuHeader;
import com.namviet.vtvtravel.response.HomeResponse;
import com.namviet.vtvtravel.response.ItemCategoryData;
import com.namviet.vtvtravel.response.ItemCategoryResponse;
import com.namviet.vtvtravel.response.MenuResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {

    private List<CategoryHome> listCategory;
    public ObservableInt prLoading;
    private ItemCategoryData itemCategoryData;
    private CategoryHome menuHeader;

    public HomeViewModel(Context context) {
        this.context = context;
        prLoading = new ObservableInt(View.GONE);
        listCategory = Collections.emptyList();
    }

    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) context;
        if (view.getId() == R.id.tvSearch) {
            mainActivity.switchFragment(SlideMenu.MenuType.SEARCH_SCREEN);
        }
    }


    public void loadDataHome() {
        prLoading.set(View.VISIBLE);
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getDataHome(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeResponse>() {
                    @Override
                    public void accept(HomeResponse homeResponse) throws Exception {
                        if (homeResponse != null && null != homeResponse.getData()) {
                            loadDataHomeSuccess(homeResponse.getData());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        L.e("accept "+throwable.toString());
                        loadDataHomeSuccess(null);
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void loadDataHomeSuccess(List<CategoryHome> data) {
        if (null != data && data.size() > 1) {
            menuHeader = data.get(0);
            this.listCategory = data.subList(1, data.size());
        }
        L.e("data size=" + this.listCategory.size());
        setChanged();
        notifyObservers();

    }

    private void loadItemCategorySuccess(ItemCategoryData data) {
        this.itemCategoryData = data;
        setChanged();
        notifyObservers(data);
    }

    public List<CategoryHome> getListCategory() {
        return listCategory;
    }

    public ItemCategoryData getItemCategoryData() {
        return itemCategoryData;
    }

    public void loadItemCategory(List<CategoryHome> listCategory) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        for (int i = 0; i < listCategory.size(); i++) {
            CategoryHome category = listCategory.get(i);
            newsService.getItemCategory(category.getContent_link())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ItemCategoryResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ItemCategoryResponse itemCategoryResponse) {
                            prLoading.set(View.GONE);
                            if (itemCategoryResponse != null && null != itemCategoryResponse.getData()) {
                                loadItemCategorySuccess(itemCategoryResponse.getData());
                            }

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void getListMenu() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
//        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getMenuList()
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MenuResponse>() {
                    @Override
                    public void accept(MenuResponse homeResponse) throws Exception {
                        if (homeResponse != null && null != homeResponse.getData()) {
                            setChanged();
                            notifyObservers(homeResponse.getData().getMenu());
                        }else {
                            setChanged();
                            notifyObservers(null);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setChanged();
                        notifyObservers(null);
                    }
                });

        compositeDisposable.add(disposable);
    }




    public CategoryHome getMenuHeader() {
        return menuHeader;
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
