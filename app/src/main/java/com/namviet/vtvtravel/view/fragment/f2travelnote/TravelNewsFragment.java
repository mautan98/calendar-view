package com.namviet.vtvtravel.view.fragment.f2travelnote;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.adapter.travelnews.HighLightTravelNoteAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentTravelNewsBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.travelnews.NewsCategoryResponse;
import com.namviet.vtvtravel.response.travelnews.NotebookResponse;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.f2oldbase.SearchActivity;
import com.namviet.vtvtravel.viewmodel.f2travelnews.TravelNewsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TravelNewsFragment extends BaseFragment<F2FragmentTravelNewsBinding> implements Observer {
    private TravelNewsViewModel travelNewsViewModel;
    private List<SubTravelNewsFragment> listSubTravelNewsFragment;
    private boolean isTravelNews;


    @SuppressLint("ValidFragment")
    public TravelNewsFragment(boolean isTravelNews) {
        this.isTravelNews = isTravelNews;
    }

    public TravelNewsFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_travel_news;
    }

    @Override
    public void initView() {
        travelNewsViewModel = new TravelNewsViewModel();
        listSubTravelNewsFragment = new ArrayList<>();
        getBinding().setTravelNewsViewModel(travelNewsViewModel);
        travelNewsViewModel.addObserver(this);
        if (isTravelNews) {
            travelNewsViewModel.getNewsCategory();
            getBinding().tvTitle.setText("Tin tức du lịch");
            getBinding().layoutLearnMore.setVisibility(View.GONE);
        } else {
            travelNewsViewModel.getNoteBook();
            getBinding().tvTitle.setText("Sổ tay du lịch");
            getBinding().layoutLearnMore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
        getBinding().vpContent.setOffscreenPageLimit(10);
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

        getBinding().btnScrollToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    listSubTravelNewsFragment.get(getBinding().vpContent.getCurrentItem()).scrollToTop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getBinding().btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.startScreen(mActivity);
            }
        });
    }

    @Override
    public void setObserver() {

    }


    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof TravelNewsViewModel && null != o) {
            if (o instanceof NewsCategoryResponse) {
                NewsCategoryResponse newsCategoryResponse = (NewsCategoryResponse) o;
                MainAdapter mainAdapter = new MainAdapter(getChildFragmentManager());

                listSubTravelNewsFragment.clear();
                for (int i = 0; i < newsCategoryResponse.getData().size(); i++) {
                    SubTravelNewsFragment subTravelNewsFragment = new SubTravelNewsFragment();
                    subTravelNewsFragment.setContentLink(newsCategoryResponse.getData().get(i).getLink());
                    subTravelNewsFragment.setPosition(i);
                    listSubTravelNewsFragment.add(subTravelNewsFragment);
                    mainAdapter.addFragment(subTravelNewsFragment, "");
                }

                getBinding().vpContent.setAdapter(mainAdapter);

                getBinding().tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package));
                getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);
                for (int i = 0; i < newsCategoryResponse.getData().size(); i++) {
                    getBinding().tabLayout.getTabAt(i).setText(newsCategoryResponse.getData().get(i).getName());
                }

            } else if (o instanceof NotebookResponse) {
                NotebookResponse response = (NotebookResponse) o;

                MainAdapter mainAdapter = new MainAdapter(getChildFragmentManager());

                listSubTravelNewsFragment.clear();
                for (int i = 0; i < response.getData().getCategory_notebook().size(); i++) {
                    SubTravelNewsFragment subTravelNewsFragment = new SubTravelNewsFragment();
                    subTravelNewsFragment.setContentLink(response.getData().getCategory_notebook().get(i).getLink());
                    subTravelNewsFragment.setPosition(i);
                    listSubTravelNewsFragment.add(subTravelNewsFragment);
                    mainAdapter.addFragment(subTravelNewsFragment, "");
                }

                getBinding().vpContent.setAdapter(mainAdapter);

                getBinding().tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package));
                getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);
                for (int i = 0; i < response.getData().getCategory_notebook().size(); i++) {
                    getBinding().tabLayout.getTabAt(i).setText(response.getData().getCategory_notebook().get(i).getName());
                }

                HighLightTravelNoteAdapter highLightTravelNoteAdapter = new HighLightTravelNoteAdapter(mActivity, response.getData().getItems(), new HighLightTravelNoteAdapter.ClickItem() {
                    @Override
                    public void onClickItem(Travel travel) {
                        DetailNewsTravelFragment detailNewsTravelFragment = new DetailNewsTravelFragment();
                        detailNewsTravelFragment.setDetailLink(travel.getDetail_linkV2());
                        addFragment(detailNewsTravelFragment);
                    }
                });
                getBinding().rclHighLight.setAdapter(highLightTravelNoteAdapter);

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
