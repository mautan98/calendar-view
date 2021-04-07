package com.namviet.vtvtravel.view.fragment.f2travelnote;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelnews.relation.SubRelationNewsAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentSubRelationNewsBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnScrollTravelNews;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.travelnews.DetailNewsCategoryResponse;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.viewmodel.f2travelnews.SubTravelNewsViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SubRelationNewsFragment extends BaseFragment<F2FragmentSubRelationNewsBinding> implements Observer {
    private String contentLink;

    private SubTravelNewsViewModel subTravelNewsViewModel;
    private SubRelationNewsAdapter subRelationNewsAdapter;
    private int position;
    private List<Travel> travels = new ArrayList<>();
    private String loadMoreLink;
    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    public SubRelationNewsFragment(){

    }



    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_sub_relation_news;
    }

    @Override
    public void initView() {
        subTravelNewsViewModel = new SubTravelNewsViewModel();
        subTravelNewsViewModel.addObserver(this);
        subTravelNewsViewModel.getDetailNewsCategory(contentLink, false);
    }

    @Override
    public void initData() {
        subRelationNewsAdapter = new SubRelationNewsAdapter(mActivity, travels, new SubRelationNewsAdapter.ClickItem() {
            @Override
            public void onClickItem(Travel travel) {
//                DetailNewsTravelFragment detailNewsTravelFragment = new DetailNewsTravelFragment();
//                detailNewsTravelFragment.setDetailLink(travel.getDetail_link());
//                addFragment(detailNewsTravelFragment);
                try {
                    TravelNewsActivity.openScreenDetail(mActivity, TravelNewsActivity.OpenType.DETAIL, travel.getDetail_link());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getBinding().rclContent.setAdapter(subRelationNewsAdapter);
    }

    public void scrollToTop(){
        getBinding().rclContent.smoothScrollToPosition(0);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    subTravelNewsViewModel.getDetailNewsCategory(loadMoreLink, true);
                    loadMoreLink = "";
                }


            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                try {
                    if(dy > 0){
                        //means user finger is moving up but the list is going down
                        EventBus.getDefault().post(new OnScrollTravelNews(false));
                    }
                    else{
                        //means user finger is moving down but the list is going up
                        EventBus.getDefault().post(new OnScrollTravelNews(true));
                    }

//                    int pastVisibleItems = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
//                    if (pastVisibleItems  == 0) {
//                        EventBus.getDefault().post(new OnScrollTravelNews(false));
//                    }

                    if (!recyclerView.canScrollVertically(-1)) {
                        EventBus.getDefault().post(new OnScrollTravelNews(false));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setObserver() {

    }


    @Override
    public void update(Observable observable, Object o) {
        getBinding().shimmerViewContainer.setVisibility(View.GONE);
        hideLoading();
        if (observable instanceof SubTravelNewsViewModel && null != o) {
            if (o instanceof DetailNewsCategoryResponse) {
                DetailNewsCategoryResponse response = (DetailNewsCategoryResponse) o;
                loadMoreLink = response.getData().getMore_link();
                if(response.isLoadMore()){
                    travels.addAll(response.getData().getItems());
                }else {
                    travels.clear();
                    travels.addAll(response.getData().getItems());
                }
                subRelationNewsAdapter.notifyDataSetChanged();

            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((BaseActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }
}
