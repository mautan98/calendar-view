package com.namviet.vtvtravel.view.fragment.imagepart;

import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.imagepart.HighLightestImagesAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentHighLightestImagesBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.imagepart.ItemImagePartResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.imagepart.ImagePartViewModel;

import java.util.ArrayList;
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

            @Override
            public void likeEvent(int position) {
                try {
                    Account account = MyApplication.getInstance().getAccount();
                    ItemImagePartResponse.Data.Item video = items.get(position);
                    if (null != account && account.isLogin()) {
                        viewModel.likeEvent(video.getId(), video.getContent_type());

                        try {
                            TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.HIGH_LIGHTEST_IMAGE, TrackingAnalytic.ScreenTitle.HIGH_LIGHTEST_IMAGE)
                                    .setContent_type(video.getContent_type())
                                    .setContent_id(video.getId())
                                    .setScreen_class(this.getClass().getName()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (video.isLiked()) {
                            video.setLiked(false);
                            if (null != video.getLikeCount()) {
                                String likeCount = (Integer.parseInt(video.getLikeCount()) - 1) + "";
                                video.setLikeCount(likeCount);
                            }
                        } else {
                            video.setLiked(true);
                            if (null != video.getLikeCount()) {
                                String likeCount = (Integer.parseInt(video.getLikeCount()) + 1) + "";
                                video.setLikeCount(likeCount);
                            }
                        }
                        highLightestImagesAdapter.notifyItemChanged(position);
                    } else {
                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        try {
            getBinding().shimmerViewContainer.stopShimmer();
            getBinding().shimmerViewContainer.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.HIGH_LIGHTEST_IMAGE, TrackingAnalytic.ScreenTitle.HIGH_LIGHTEST_IMAGE);
    }
}
