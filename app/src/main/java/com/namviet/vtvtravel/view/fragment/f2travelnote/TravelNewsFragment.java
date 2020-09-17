package com.namviet.vtvtravel.view.fragment.f2travelnote;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.adapter.travelnews.HighLightTravelNoteAdapter;
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentTravelNewsBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnScrollTravelNews;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.travelnews.NewsCategoryResponse;
import com.namviet.vtvtravel.response.travelnews.NotebookResponse;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.f2oldbase.SearchActivity;
import com.namviet.vtvtravel.viewmodel.f2travelnews.TravelNewsViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TravelNewsFragment extends BaseFragment<F2FragmentTravelNewsBinding> implements Observer {
    private TravelNewsViewModel travelNewsViewModel;
    private List<SubTravelNewsFragment> listSubTravelNewsFragment;
    private boolean isTravelNews;
    private VTVTabStyleAdapter vtvTabStyleAdapter;


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

        getBinding().vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getBinding().btnScrollToTop.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
                vtvTabStyleAdapter = new VTVTabStyleAdapter(getChildFragmentManager());

                listSubTravelNewsFragment.clear();
                for (int i = 0; i < newsCategoryResponse.getData().size(); i++) {
                    SubTravelNewsFragment subTravelNewsFragment = new SubTravelNewsFragment();
                    subTravelNewsFragment.setContentLink(newsCategoryResponse.getData().get(i).getLink());
                    subTravelNewsFragment.setPosition(i);
                    listSubTravelNewsFragment.add(subTravelNewsFragment);
                    vtvTabStyleAdapter.addFragment(subTravelNewsFragment, "");
                }

                getBinding().vpContent.setAdapter(vtvTabStyleAdapter);
                getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);
                for (int i = 0; i < newsCategoryResponse.getData().size(); i++) {

                    View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style, null);
                    TextView tvHome = tabHome.findViewById(R.id.tvTitle);
                    tvHome.setText((newsCategoryResponse.getData().get(i).getName()));
                    if (i == 0) {
                        tvHome.setTextColor(Color.parseColor("#00918D"));
                    } else {
                        tvHome.setTextColor(Color.parseColor("#101010"));
                    }
                    View view = tabHome.findViewById(R.id.indicator);
                    if (i == 0) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.INVISIBLE);
                    }
                    getBinding().tabLayout.getTabAt(i).setCustomView(tabHome);
                }

                getBinding().tabLayout.addOnTabSelectedListener(OnTabSelectedListener);

            } else if (o instanceof NotebookResponse) {
                NotebookResponse response = (NotebookResponse) o;

                vtvTabStyleAdapter = new VTVTabStyleAdapter(getChildFragmentManager());

                listSubTravelNewsFragment.clear();
                for (int i = 0; i < response.getData().getCategory_notebook().size(); i++) {
                    SubTravelNewsFragment subTravelNewsFragment = new SubTravelNewsFragment();
                    subTravelNewsFragment.setContentLink(response.getData().getCategory_notebook().get(i).getLink());
                    subTravelNewsFragment.setPosition(i);
                    listSubTravelNewsFragment.add(subTravelNewsFragment);
                    vtvTabStyleAdapter.addFragment(subTravelNewsFragment, "");
                }

                getBinding().vpContent.setAdapter(vtvTabStyleAdapter);
                getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);
                for (int i = 0; i < response.getData().getCategory_notebook().size(); i++) {
                    View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style, null);
                    TextView tvHome = tabHome.findViewById(R.id.tvTitle);
                    tvHome.setText((response.getData().getCategory_notebook().get(i).getName()));
                    if (i == 0) {
                        tvHome.setTextColor(Color.parseColor("#00918D"));
                    } else {
                        tvHome.setTextColor(Color.parseColor("#101010"));
                    }
                    View view = tabHome.findViewById(R.id.indicator);
                    if (i == 0) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.INVISIBLE);
                    }
                    getBinding().tabLayout.getTabAt(i).setCustomView(tabHome);
                }
                getBinding().tabLayout.addOnTabSelectedListener(OnTabSelectedListener);

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onScroll(OnScrollTravelNews onScrollTravelNews) {
        if (onScrollTravelNews.isShow()) {
            getBinding().btnScrollToTop.setVisibility(View.VISIBLE);
        } else {
            getBinding().btnScrollToTop.setVisibility(View.GONE);
        }
    }

//    private void setupTabIcons() {
//        View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style, null);
//        View tabNew = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style, null);
//
//
//        ImageView imgHome = tabHome.findViewById(R.id.imgIconTab);
//        ImageView imgNew = tabNew.findViewById(R.id.imgIconTab);
//
//        TextView tvHome = tabHome.findViewById(R.id.tvIconTab);
//        TextView tvNew = tabNew.findViewById(R.id.tvIconTab);
//
//        imgHome.setImageResource(R.drawable.f2_missing_call_change_icon_control);
//        imgNew.setImageResource(R.drawable.f2_contact_change_icon_control);
//
//        tvHome.setText("Lịch sử cuộc gọi");
//        tvHome.setTextColor(Color.parseColor("#999999"));
//        tvNew.setText("Danh bạ");
//
//        tabHome.setSelected(true);
//        binding.tabLayout.getTabAt(0).setCustomView(tabHome);
//        binding.tabLayout.getTabAt(1).setCustomView(tabNew);
//
//        binding.tabLayout.addOnTabSelectedListener(OnTabSelectedListener);
//    }


    private TabLayout.OnTabSelectedListener OnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            vtvTabStyleAdapter.SetOnSelectView(getBinding().tabLayout, c);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            vtvTabStyleAdapter.SetUnSelectView(getBinding().tabLayout, c);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}
