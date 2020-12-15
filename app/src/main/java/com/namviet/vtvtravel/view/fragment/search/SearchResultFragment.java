package com.namviet.vtvtravel.view.fragment.search;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.SearchKeywordAdapter;
import com.namviet.vtvtravel.adapter.SearchNearResultAdapter;
import com.namviet.vtvtravel.adapter.SearchResultVoucherAdaper;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentSearchResultBinding;
import com.namviet.vtvtravel.listener.FilterSelectListener;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.Filter;
import com.namviet.vtvtravel.model.NearItem;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.model.SearchVoucher;
import com.namviet.vtvtravel.response.FilterData;
import com.namviet.vtvtravel.response.FilterSearchData;
import com.namviet.vtvtravel.response.FilterSearchResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.SearchResultResponse;
import com.namviet.vtvtravel.view.dialog.FilterSearchDialogFragment;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.SearchViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SearchResultFragment extends MainFragment implements Observer, FilterSelectListener, TravelSelectListener {
    private FragmentSearchResultBinding binding;
    private Object intObj;
    private SearchViewModel searchViewModel;
    private List<Travel> travelList = new ArrayList<>();
    private SearchResultVoucherAdaper resultVoucherAdapter;
    private SearchNearResultAdapter searchNearResultAdapter;
    private SearchKeywordAdapter searchKeywordAdapter;
    private ArrayList<FilterSearchData> filterList;
    private EndlessRecyclerViewScrollListener scrollListener;
    private String moreLink;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            intObj = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar.myToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        searchViewModel = new SearchViewModel(getContext());
        searchViewModel.addObserver(this);


        binding.toolBar.ivBack.setOnClickListener(this);
        binding.toolBar.ivSearch.setVisibility(View.GONE);
        if (intObj instanceof SearchVoucher) {
            SearchVoucher searchVoucher = (SearchVoucher) intObj;
            binding.toolBar.tvTitle.setText(searchVoucher.getTitle());
            binding.tvTitleVoucher.setVisibility(View.VISIBLE);
            binding.tvTitleVoucher.setText(Html.fromHtml(getString(R.string.title_voucher)));
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            binding.rvResult.setLayoutManager(gridLayoutManager);
            resultVoucherAdapter = new SearchResultVoucherAdaper(getContext(), travelList);
            binding.rvResult.setAdapter(resultVoucherAdapter);
            resultVoucherAdapter.setTravelSelectListener(this);
            showDialogLoading();
            searchViewModel.loadListSearchVoucher(searchVoucher.getLink());
        } else if (intObj instanceof NearItem) {
            final NearItem nearItem = (NearItem) intObj;
            if (null != nearItem.getLink() && null == nearItem.getKeyword()) {
                binding.toolBar.tvTitle.setText(nearItem.getName());
                binding.llWelcome.setVisibility(View.VISIBLE);
                binding.tvWelcome.setText(nearItem.getTitleBar());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                binding.rvResult.setLayoutManager(layoutManager);
                searchNearResultAdapter = new SearchNearResultAdapter(getContext(), travelList);
                binding.rvResult.setAdapter(searchNearResultAdapter);
                searchNearResultAdapter.setTravelSelectListener(this);
                showDialogLoading();
                searchViewModel.loadListSearchVoucher(nearItem.getLink());
                searchViewModel.loadFilterSearch();
                scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        if (null != moreLink) {
                            searchViewModel.loadListSearchVoucher(moreLink);
                        }
                    }
                };
            } else if (null != nearItem.getKeyword()) {
                binding.toolBar.tvTitle.setText(nearItem.getKeyword());
                binding.tvTotalSearchKeyword.setVisibility(View.VISIBLE);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                binding.rvResult.setLayoutManager(gridLayoutManager);
                searchKeywordAdapter = new SearchKeywordAdapter(getContext(), travelList);
                binding.rvResult.setAdapter(searchKeywordAdapter);
                searchKeywordAdapter.setTravelSelectListener(this);
                showDialogLoading();
                searchViewModel.loadSearchResult(nearItem.getKeyword(), 1);
                scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        if (null != moreLink) {
                            searchViewModel.loadSearchResult(nearItem.getKeyword(), page);
                        }

                    }
                };
            }


        }
        if (null != scrollListener) {
            binding.rvResult.addOnScrollListener(scrollListener);
        }
        binding.llFilter.setOnClickListener(this);
    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.llFilter) {
            if (null != filterList) {
                FilterSearchDialogFragment filterDialogFragment = FilterSearchDialogFragment.newInstance(filterList);
                filterDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                filterDialogFragment.setFilterSelectListener(this);
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        dimissDialogLoading();
        if (observable instanceof SearchViewModel) {
            if (o instanceof SearchResultResponse) {
                if (intObj instanceof SearchVoucher) {
                    SearchResultResponse response = (SearchResultResponse) o;
                    travelList.addAll(response.getData().getItems());
                    resultVoucherAdapter.notifyDataSetChanged();
                } else if (intObj instanceof NearItem) {
                    SearchResultResponse response = (SearchResultResponse) o;
                    moreLink = response.getData().getMore_link();
                    binding.tvTotal.setText(response.getData().getTotal() + " Kết quả");
                    travelList.addAll(response.getData().getItems());
                    if (null != searchNearResultAdapter) {
                        searchNearResultAdapter.notifyDataSetChanged();
                    } else if (null != searchKeywordAdapter) {
                        NearItem nearItem = (NearItem) intObj;
                        if (response.getData().getTotal() > 0) {
                            binding.tvTotalSearchKeyword.setText(Html.fromHtml(getString(R.string.title_search_keyword, "" + response.getData().getTotal(), nearItem.getKeyword())));
                        } else {
                            binding.tvTotalSearchKeyword.setText(Html.fromHtml(getString(R.string.title_search_keyword_empty, nearItem.getKeyword())));
                        }

                        searchKeywordAdapter.notifyDataSetChanged();
                    }

                }
            } else if (o instanceof FilterSearchResponse) {
                FilterSearchResponse response = (FilterSearchResponse) o;
                filterList = response.getData();
            } else if (o instanceof ResponseError) {
                ResponseError responseError = (ResponseError) o;
                showMessage(responseError.getMessage());
            } else {
                if (intObj instanceof NearItem) {
                    NearItem nearItem = (NearItem) intObj;
                    binding.tvTotalSearchKeyword.setVisibility(View.VISIBLE);
                    // binding.llWelcome.setVisibility(View.GONE);
                    // binding.tvTotalSearchKeyword.setText(Html.fromHtml(getString(R.string.title_search_keyword_empty, nearItem.getKeyword())));
                }

            }
        } else {
//            moreLink = null;
        }
    }

    @Override
    public void onSelect(ArrayList<FilterData> list) {

    }

    @Override
    public void onSelectItem(Filter filter, String title) {
        moreLink = null;
        binding.toolBar.tvTitle.setText(title);
        binding.tvFilter.setText(filter.getName());
        travelList.clear();
        showDialogLoading();
        searchViewModel.loadListSearchVoucher(filter.getLink());
    }

    @Override
    public void onSelectTravel(Travel travel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
        mActivity.setBundle(bundle);
        if (travel.getContent_type().equals(Constants.TypePlace.places)) {
            mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.restaurants)) {
            mActivity.switchFragment(SlideMenu.MenuType.DETAIL_EAT_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.hotels)) {
            mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_STAY_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.centers)) {
            mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
        } else if (travel.getType().equals(Constants.TypePlace.post)) {
            News news = new News(travel.getId(), travel.getDetail_link(), travel.getContent_type());
            bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
            mActivity.setBundle(bundle);
            mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
        } else if (travel.getType().equals(Constants.TypePlace.link)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(travel.getDetail_link()));
            startActivity(intent);
        }
    }
}
