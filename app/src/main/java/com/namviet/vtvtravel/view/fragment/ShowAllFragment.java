package com.namviet.vtvtravel.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ShowAllAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.ItemTravel;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.viewmodel.SearchViewModel;
import com.namviet.vtvtravel.viewmodel.ShowAllViewModel;
import com.namviet.vtvtravel.databinding.FragmentShowAllBinding;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.Observable;
import java.util.Observer;

public class ShowAllFragment extends MainFragment implements Observer {

    private ItemTravel itemTravel;
    private FragmentShowAllBinding binding;
    private ShowAllViewModel showAllViewModel;
    private ShowAllAdapter showAllAdapter;
    private int mPage = 1;
    private EndlessRecyclerViewScrollListener scrollListener;

    public static ShowAllFragment newInstance(Bundle bundle) {
        ShowAllFragment fragment = new ShowAllFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            itemTravel = getArguments().getParcelable(Constants.IntentKey.KEY_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_all, container, false);
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
        showAllViewModel = new ShowAllViewModel(getContext());
        binding.setShowAllViewModel(showAllViewModel);
        showAllViewModel.addObserver(this);
        showAllAdapter = new ShowAllAdapter();
        binding.rvShowAll.setAdapter(showAllAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvShowAll.setLayoutManager(layoutManager);

        binding.toolBar.ivBack.setOnClickListener(this);
        binding.toolBar.ivSearch.setOnClickListener(this);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                showAllViewModel.getListAll(itemTravel.getMore_link(), page);
            }
        };
        binding.rvShowAll.addOnScrollListener(scrollListener);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        if (itemTravel != null) {
            binding.toolBar.tvTitle.setText(itemTravel.getName());
            showAllViewModel.getListAll(itemTravel.getMore_link(), mPage);
        }

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ShowAllViewModel) {
            showAllAdapter.setTravelList(showAllViewModel.getTravelList());
            if (o instanceof ResponseError) {
                ResponseError responseError = (ResponseError) o;
                showMessage(responseError.getMessage());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showAllViewModel.reset();
    }
}
