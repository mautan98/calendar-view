package com.namviet.vtvtravel.view.fragment.topexperience;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelnews.NearByInTravelDetailAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentSubTopExperienceBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2topexperience.SubTopExperienceResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.viewmodel.f2topexperience.SubTopExperienceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SubTopExperienceFragment extends BaseFragment<F2FragmentSubTopExperienceBinding> implements Observer, NearByInTravelDetailAdapter.ClickItem {
    private String link;
    private SubTopExperienceViewModel viewModel;
    private NearByInTravelDetailAdapter nearByInTravelDetailAdapter;
    private List<Travel> travels = new ArrayList<>();
    private String loadMoreLink;

    @SuppressLint("ValidFragment")
    public SubTopExperienceFragment(String link) {
        this.link = link;
    }

    public SubTopExperienceFragment() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_sub_top_experience;
    }

    @Override
    public void initView() {
        viewModel = new SubTopExperienceViewModel();
        getBinding().setSubTopExperienceViewModel(viewModel);
        viewModel.addObserver(this);

        viewModel.getSubTopExperience(link, false);
    }

    @Override
    public void initData() {
        nearByInTravelDetailAdapter = new NearByInTravelDetailAdapter(mActivity, travels, this);
        getBinding().rclContent.setAdapter(nearByInTravelDetailAdapter);


        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getSubTopExperience(loadMoreLink, true);
                    loadMoreLink = "";
                }
            }
        });
    }

    public void scrollToTop() {
        getBinding().rclContent.smoothScrollToPosition(0);
    }

    @Override
    public void inject() {

    }

    private void clearRclData(){
        travels.clear();
        nearByInTravelDetailAdapter.notifyDataSetChanged();
    }

    public void loadData(String regionId){
        clearRclData();
        viewModel.getSubTopExperience(link+"&region_id="+regionId, false);
    }

    @Override
    public void setClickListener() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof SubTopExperienceViewModel && null != o) {
            if (o instanceof SubTopExperienceResponse) {
                SubTopExperienceResponse response = (SubTopExperienceResponse) o;
                loadMoreLink = response.getData().getMore_link();
                if(response.isLoadMore()){
                    travels.addAll(response.getData().getItems());
                }else {
                    travels.clear();
                    travels.addAll(response.getData().getItems());
                }
                nearByInTravelDetailAdapter.notifyDataSetChanged();
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
    public void onClickItem(Travel travel) {
        try {
            SmallLocationActivity.startScreenDetail(mActivity, SmallLocationActivity.OpenType.DETAIL, travel.getDetail_link());
        } catch (Exception e) {
            e.printStackTrace();
        }    }
}
