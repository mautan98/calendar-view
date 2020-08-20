package com.namviet.vtvtravel.view.fragment.imagepart;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.imagepart.HighLightestImagesAdapter;
import com.namviet.vtvtravel.adapter.imagepart.ImagePartAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentHighLightestImagesBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2imagepart.ImagePart;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.imagepart.ImagePartResponse;
import com.namviet.vtvtravel.response.imagepart.ItemImagePartResponse;
import com.namviet.vtvtravel.viewmodel.imagepart.ImagePartViewModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class HighLightestImagesFragment extends BaseFragment<F2FragmentHighLightestImagesBinding> implements Observer {
    private HighLightestImagesAdapter highLightestImagesAdapter;
    private List<ItemImagePartResponse.Data.Item> items = new ArrayList<>();
    private ImagePartViewModel viewModel;
    private String loadMoreLink;

    @SuppressLint("ValidFragment")
    public HighLightestImagesFragment(String idGallery) {
        this.idGallery = idGallery;
    }

    public HighLightestImagesFragment() {
    }

    private String idGallery;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_high_lightest_images;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        viewModel = new ImagePartViewModel();
        getBinding().setImagePartViewModel(viewModel);
        viewModel.addObserver(this);

        viewModel.getItemGallery(idGallery);

//        itemImagePartResponse = new Gson().fromJson(json, ItemImagePartResponse.class);
        highLightestImagesAdapter = new HighLightestImagesAdapter(items, mActivity, new HighLightestImagesAdapter.ClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        getBinding().rclContent.setAdapter(highLightestImagesAdapter);

        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getGalleryByUrl(loadMoreLink, true);
                    loadMoreLink = "";
                }
            }
        });
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof ImagePartViewModel && null != o) {
            if (o instanceof ItemImagePartResponse) {
                ItemImagePartResponse response = (ItemImagePartResponse) o;
                loadMoreLink = response.getData().getMore_link();
                if (response.isLoadMore()) {
                    items.addAll(response.getData().getItems());
                } else {
                    items.clear();
                    items.addAll(response.getData().getItems());
                }
                highLightestImagesAdapter.notifyDataSetChanged();
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }
}
