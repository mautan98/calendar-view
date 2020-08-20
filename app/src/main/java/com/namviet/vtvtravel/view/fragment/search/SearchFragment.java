package com.namviet.vtvtravel.view.fragment.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.KeyboardUtils;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.GroupSearchAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentSearchBinding;
import com.namviet.vtvtravel.listener.SearchSelectListener;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.NearItem;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.model.SearchVoucher;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.SearchResponse;
import com.namviet.vtvtravel.ultils.FirebaseAnalytic;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.SearchViewModel;

import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class SearchFragment extends MainFragment implements Observer, SearchSelectListener, TravelSelectListener {
    private FragmentSearchBinding binding;
    private SearchViewModel searchViewModel;
    private List<Object> suggestList;
    private GroupSearchAdapter groupSearchAdapter;

    public SearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        suggestList = mActivity.getListSearchTrend();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.myToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mActivity.pushEvent(FirebaseAnalytic.SEARCH);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        searchViewModel = new SearchViewModel(getActivity());
        searchViewModel.loadSearchTrend();
        binding.setSearchViewModel(searchViewModel);
        searchViewModel.addObserver(this);
        binding.ivClear.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.edSearch.addTextChangedListener(new TextWatcher() {

            private Timer timer = new Timer();
            private final long DELAY = 300; // milliseconds

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       mActivity.runOnUiThread(() -> {
                                           if (charSequence.length() > 0) {
                                               binding.ivClear.setVisibility(View.VISIBLE);
                                               searchViewModel.loadSearch(charSequence.toString().trim());
                                           } else {
                                               binding.ivClear.setVisibility(View.GONE);
                                               searchViewModel.loadSearchTrend();
                                           }
                                       });
                                   }
                               },
                        DELAY
                );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!v.getText().toString().trim().equals("")) {
                        NearItem nearItem = new NearItem();
                        nearItem.setKeyword(v.getText().toString().trim());
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, nearItem);
                        mActivity.setBundle(bundle);
                        mActivity.switchFragment(SlideMenu.MenuType.RESULT_SEARCH_SCREEN);
                    }
//                    KeyboardUtils.hideKeyboard(getContext(), binding.edSearch);
                    return true;
                }

                return false;
            }
        });
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();

        binding.rvSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupSearchAdapter = new GroupSearchAdapter(getContext(), suggestList);
        binding.rvSearch.setAdapter(groupSearchAdapter);
        groupSearchAdapter.setSearchSelectListener(this);
        groupSearchAdapter.setTravelSelectListener(this);
    }

    @Override
    public void update(Observable observable, final Object o) {
        if (observable instanceof SearchViewModel) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (o != null) {
                        if (o instanceof SearchResponse) {
                            SearchResponse response = (SearchResponse) o;
                            updateUI(response);
                        } else if (o instanceof ResponseError) {
                            ResponseError responseError = (ResponseError) o;
                            showMessage(responseError.getMessage());
                        }
                    }
                }
            });
        }
    }

    private void updateUI(SearchResponse response) {
        mActivity.setListSearchTrend(response.getData());
        groupSearchAdapter.setSuggestList(response.getData());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.ivClear) {
            binding.edSearch.setText("");
        } else if (view == binding.ivBack) {
            mActivity.onBackPressed();
            KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchViewModel.reset();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSearchSelect(Object object) {
        if (object instanceof SearchVoucher) {
            SearchVoucher searchVoucher = (SearchVoucher) object;
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, searchVoucher);
            mActivity.setBundle(bundle);
            mActivity.switchFragment(SlideMenu.MenuType.RESULT_SEARCH_SCREEN);
        } else if (object instanceof NearItem) {
            NearItem nearSuggest = (NearItem) object;
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, nearSuggest);
            mActivity.setBundle(bundle);
            mActivity.switchFragment(SlideMenu.MenuType.RESULT_SEARCH_SCREEN);
        }
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
        } else if ((null != travel.getType() && travel.getType().equals(Constants.TypePlace.post)) ||
                (null != travel.getContent_type() && travel.getContent_type().equals(Constants.TypePlace.sponsored_posts))) {
            News news = new News(travel.getId(), travel.getDetail_link(), travel.getContent_type());
            bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
            mActivity.setBundle(bundle);
            mActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
        } else if (null != travel.getType() && travel.getType().equals(Constants.TypePlace.link)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(travel.getDetail_link()));
            startActivity(intent);
        }
    }
}
