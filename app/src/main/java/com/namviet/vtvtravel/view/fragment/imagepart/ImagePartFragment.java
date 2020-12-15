package com.namviet.vtvtravel.view.fragment.imagepart;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.imagepart.ImagePartAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentImagePartBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2imagepart.ImagePart;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.imagepart.ImagePartResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.viewmodel.imagepart.ImagePartViewModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ImagePartFragment extends BaseFragment<F2FragmentImagePartBinding> implements Observer {
    private ImagePartAdapter imagePartAdapter;
    private ImagePartViewModel viewModel;
    private List<Travel> travels = new ArrayList<>();
    private String loadMoreLink;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_image_part;
    }

    @Override
    public void initView() {
        viewModel = new ImagePartViewModel();
        getBinding().setImagePartViewModel(viewModel);
        viewModel.addObserver(this);
        viewModel.getGallery(false);
    }

    @Override
    public void initData() {
        imagePartAdapter = new ImagePartAdapter(imageParts, mActivity, new ImagePartAdapter.ClickItem() {
            @Override
            public void onClickItem(int position) {
                travels.get(position);
                addFragment(new HighLightestImagesFragment(travels.get(position).getId()));
            }
        });
        getBinding().rclContent.setAdapter(imagePartAdapter);
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


        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getGalleryMore(loadMoreLink, true);
                    loadMoreLink = "";
                }
            }
        });
    }

    @Override
    public void setObserver() {

    }

    private List<ImagePart> imageParts = new ArrayList<>();

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof ImagePartViewModel && null != o) {
            if (o instanceof ImagePartResponse) {
                ImagePartResponse response = (ImagePartResponse) o;
                loadMoreLink = response.getData().getMore_link();

                LinkedHashMap<Integer, ImagePart> imagePartLinkedHashMap = new LinkedHashMap<>();


                if (response != null && response.getData() != null && response.getData().getItems() != null && response.getData().getItems().size() > 0) {
                    travels.addAll(response.getData().getItems());
                    for (int i = 0; i < travels.size(); i++) {
                        if (imagePartLinkedHashMap.size() == 0) {
                            ImagePart imagePart = new ImagePart();
                            List<Travel> items = new ArrayList<>();
                            items.add(travels.get(i));
                            imagePart.setItems(items);
                            imagePartLinkedHashMap.put(0, imagePart);
                        } else {
                            ImagePart imagePart = imagePartLinkedHashMap.get(imagePartLinkedHashMap.size() - 1);
                            if (imagePart == null) {
                                imagePart = new ImagePart();
                                List<Travel> items = new ArrayList<>();
                                items.add(travels.get(i));
                                imagePart.setItems(items);
                                imagePartLinkedHashMap.put(0, imagePart);
                            } else {
                                if (imagePart.getItems().size() < 9) {
                                    List<Travel> items = imagePart.getItems();
                                    items.add(travels.get(i));
                                    imagePart.setItems(items);
                                    imagePartLinkedHashMap.put(imagePartLinkedHashMap.size() - 1, imagePart);
                                } else {
                                    ImagePart imagePartNew = new ImagePart();
                                    List<Travel> items = new ArrayList<>();
                                    items.add(travels.get(i));
                                    imagePartNew.setItems(items);
                                    imagePartLinkedHashMap.put(imagePartLinkedHashMap.size(), imagePartNew);
                                }
                            }
                        }

                    }
                }

                if(response.isLoadMore()){
                    imageParts.clear();
                    imageParts.addAll(new ArrayList<>(imagePartLinkedHashMap.values()));
                }else {
                    imageParts.clear();
                    imageParts.addAll(new ArrayList<>(imagePartLinkedHashMap.values()));
                }

                imagePartAdapter.notifyDataSetChanged();


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
        setDataScreen(TrackingAnalytic.ScreenCode.CORNER_PHOTO, TrackingAnalytic.ScreenTitle.CORNER_PHOTO);
    }
}
