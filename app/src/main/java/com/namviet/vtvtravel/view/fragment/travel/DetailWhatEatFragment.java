package com.namviet.vtvtravel.view.fragment.travel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.L;
import com.daimajia.slider.library.Travel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.CommentAdapter;
import com.namviet.vtvtravel.adapter.FacilitiesAdapter;
import com.namviet.vtvtravel.adapter.NearPlaceAdapter;
import com.namviet.vtvtravel.adapter.PhotoGalleryAdapter;
import com.namviet.vtvtravel.adapter.SuggestPlaceAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentDetailWhatEatBinding;
import com.namviet.vtvtravel.help.MySupportMapFragment;
import com.namviet.vtvtravel.listener.PhotoSelectListener;
import com.namviet.vtvtravel.listener.PlaceSelectLisntener;
import com.namviet.vtvtravel.listener.PostCommentListener;
import com.namviet.vtvtravel.model.AboutPlace;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Comment;
import com.namviet.vtvtravel.model.Gallery;
import com.namviet.vtvtravel.model.NearPlace;
import com.namviet.vtvtravel.model.Suggestion;
import com.namviet.vtvtravel.model.TabItem;
import com.namviet.vtvtravel.model.VideoPlace;
import com.namviet.vtvtravel.response.CommentResponse;
import com.namviet.vtvtravel.response.DetailPlaceData;
import com.namviet.vtvtravel.response.DetailPlaceResponse;
import com.namviet.vtvtravel.response.PostCommentResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.dialog.CommentDialogFragment;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.WebviewFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.widget.CustomNestedScrollView;
import com.namviet.vtvtravel.widget.NestedScrollViewListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DetailWhatEatFragment extends MainFragment implements Observer, PlaceSelectLisntener, NestedScrollViewListener, PostCommentListener, PhotoSelectListener {
    private FragmentDetailWhatEatBinding binding;
    private ArrayList<TabItem> arrTabs = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private PhotoGalleryAdapter photoGalleryAdapter;
    private SuggestPlaceAdapter suggestPlaceAdapter;
    private NearPlaceAdapter nearPlaceAdapter;
    private FacilitiesAdapter facilitiesAdapter;
    private Travel mTravel;
    private PlaceViewModel placeViewModel;
    private MySupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private List<Comment> listComment = new ArrayList<>();
    private int page = 0;
    private int totalPage;
    private boolean isLoadingMore = true;
    private int countComment = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mTravel = getArguments().getParcelable(Constants.IntentKey.KEY_TRAVEL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_what_eat, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.myToolbar);
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
        placeViewModel = new PlaceViewModel(getContext());
        binding.setPlaceViewModel(placeViewModel);
        placeViewModel.addObserver(this);
//        binding.tvTitle.setText("Ăn gì?");
        binding.ivSearch.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.nesScroll.setScrollViewListener(this);
        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                double percentage = (double) Math.abs(verticalOffset) / binding.myToolbar.getHeight();
                if (percentage > 0.8) {

                } else {


                }
            }
        });

        mapFragment = MySupportMapFragment.newInstance();
        mapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                binding.nesScroll.requestDisallowInterceptTouchEvent(true);
            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
            }
        });

        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.KeyBroadcast.KEY_LOGIN_SCREEN);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, intentFilter);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        setUiTab();
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            setImageUrl(account.getImageProfile(), binding.avatar);
        }
        binding.rvFacility.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFacility.setNestedScrollingEnabled(false);

        binding.rvPhotos.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvPhotos.setNestedScrollingEnabled(false);

        binding.rvSuggest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        binding.rvNearPlace.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvNearPlace.setNestedScrollingEnabled(false);


        binding.rvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvComment.setNestedScrollingEnabled(false);
        commentAdapter = new CommentAdapter(mActivity, listComment, this);
        binding.rvComment.setAdapter(commentAdapter);

        if (null != mTravel) {
            showDialogLoading();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    placeViewModel.loadDetailPlace(mTravel.getDetail_link());
                }
            }, Constants.TimeDelay);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.tvComment) {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                CommentDialogFragment commentDialogFragment = CommentDialogFragment.newInstance();
                commentDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                commentDialogFragment.setPostCommentListener(this);
            } else {
                mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setUiTab() {
        binding.tabLayout.removeAllTabs();

        for (int i = 0; i < arrTabs.size(); i++) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(arrTabs.get(i).getTitle()));
        }
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                L.e("Position= " + tab.getPosition());
                int position = tab.getPosition();
                binding.nesScroll.scrollTo(0, arrTabs.get(position).getmView().getTop());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (null != mTravel && !"".equals(mTravel.getContent_type())) {
            setTitle();
        }
    }

    private void setTitle() {
        if ("restaurants".equals(mTravel.getContent_type())) {
            binding.tvTitle.setText("Ăn uống");
        } else if ("centers".equals(mTravel.getContent_type())) {
            binding.tvTitle.setText("Vui chơi giải trí");
        } else {
            binding.tvTitle.setText("Thăm quan mua sắm");
        }
    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof PlaceViewModel) {
            if (o != null) {
                if (o instanceof DetailPlaceResponse) {
                    DetailPlaceResponse detailPlaceResponse = (DetailPlaceResponse) o;
                    if (detailPlaceResponse.isSuccess() || "SUCCESS".equals(detailPlaceResponse.getCode())){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateUI((DetailPlaceResponse) o);
                            }
                        }, 1000);
                    } else {
                        showMessage(detailPlaceResponse.getMessage());
                    }

                } else if (o instanceof CommentResponse) {
                    CommentResponse commentResponse = (CommentResponse) o;
                    totalPage = commentResponse.getData().getTotalPages();
                    countComment = commentResponse.getData().getTotalElements();
                    binding.tvTitleComment.setText(getText(R.string.comment) + " (" + countComment + ")");
                    listComment.addAll(commentResponse.getData().getContent());
                    commentAdapter.notifyDataSetChanged();
                } else if (o instanceof PostCommentResponse) {
                    countComment++;
                    binding.tvTitleComment.setText(getText(R.string.comment) + " (" + countComment + ")");
                    PostCommentResponse response = (PostCommentResponse) o;
                    Account account = MyApplication.getInstance().getAccount();
                    Comment comment = response.getData();
                    comment.setUser(account);
                    listComment.add(0, comment);
                    commentAdapter.notifyDataSetChanged();
                } else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                }
            } else {

            }
        }
    }

    private void updateUI(DetailPlaceResponse response) {
        dimissDialogLoading();
        arrTabs.clear();
        DetailPlaceData data = response.getData();
        setImageUrl(data.getBanner_url(), binding.ivBanner);
        for (Object obj : data.getTabs()) {
            String json = new Gson().toJson(obj);
            try {

                JSONObject tabItem = new JSONObject(json);
                if (tabItem.getString(Constants.KeyJSON.CODE).equals(Constants.TypeDetail.ABOUT)) {
                    AboutPlace aboutPlace = new Gson().fromJson(json, AboutPlace.class);
                    arrTabs.add(new TabItem(aboutPlace.getTitle(), binding.tvTitleWhere));
                    binding.tvTitleWhere.setText(aboutPlace.getName());
                    binding.tvAddress.setText(aboutPlace.getAddress());
                    if (null != aboutPlace.getMobile()) {
                        binding.llPhone.setVisibility(View.VISIBLE);
                        binding.tvPhone.setText(aboutPlace.getMobile());
                    } else {
                        binding.llPhone.setVisibility(View.GONE);
                    }
                    String webViewContent = tabItem.getJSONObject("footer").getString("description");
                    String webViewUrl = tabItem.getJSONObject("footer").getString("url");
                    String webViewUrlApi = tabItem.getJSONObject("footer").getString("url_api");
                    if (webViewContent.length() > 0 && webViewUrl.length() > 0) {
                        binding.webDes.loadData(aboutPlace.getDescription() + webViewContent, "text/html; charset=utf-8", "UTF-8");
                        binding.webDes.setWebViewClient(new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                if (url.startsWith(webViewUrl)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(WebviewFragment.URL_WEBVIEW, webViewUrlApi);
                                    mActivity.setBundle(bundle);
                                    mActivity.switchFragment(SlideMenu.MenuType.WEBVIEW_SCREEN);
                                    return true;
                                }
                                return false;
                            }
                        });
                    } else {
                        binding.webDes.loadData(aboutPlace.getDescription(), "text/html; charset=utf-8", "UTF-8");
                    }

                    if (null != aboutPlace.getFacilities() && aboutPlace.getFacilities().size() > 0) {
                        facilitiesAdapter = new FacilitiesAdapter(getContext(), aboutPlace.getFacilities());
                        binding.rvFacility.setAdapter(facilitiesAdapter);
                    }
                    addMyLocation(data.getLat(), data.getLng(), aboutPlace.getAddress());
                } else if (tabItem.getString(Constants.KeyJSON.CODE).equals(Constants.TypeDetail.GALLERY)) {
                    Gallery gallery = new Gson().fromJson(json, Gallery.class);
                    arrTabs.add(new TabItem(gallery.getTitle(), binding.tvTitlePhoto));
                    photoGalleryAdapter = new PhotoGalleryAdapter(getContext(), true, gallery.getItems());
                    binding.rvPhotos.setAdapter(photoGalleryAdapter);
                    photoGalleryAdapter.setPhotoSelectListener(this);
                } else if (tabItem.getString(Constants.KeyJSON.CODE).equals(Constants.TypeDetail.SUGGESTION)) {
                    Suggestion suggestion = new Gson().fromJson(json, Suggestion.class);
                    arrTabs.add(new TabItem(suggestion.getTitle(), binding.tvTitleSuggest));
                    suggestPlaceAdapter = new SuggestPlaceAdapter(getContext(), suggestion.getItems());
                    binding.rvSuggest.setAdapter(suggestPlaceAdapter);
                    suggestPlaceAdapter.setPlaceSelectLisntener(this);
                } else if (tabItem.getString(Constants.KeyJSON.CODE).equals(Constants.TypeDetail.NEARPOINT)) {
                    NearPlace nearPlace = new Gson().fromJson(json, NearPlace.class);
                    arrTabs.add(new TabItem(nearPlace.getTitle(), binding.tvTitleNearPlace));
                    nearPlaceAdapter = new NearPlaceAdapter(getContext(), nearPlace.getItems());
                    binding.rvNearPlace.setAdapter(nearPlaceAdapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        binding.appBar.setExpanded(true);
        setUiTab();
    }

    @Override
    public void onSelect(Travel travel) {
        showDialogLoading();
        arrTabs.clear();
        placeViewModel.loadDetailPlace(travel.getDetail_link());
    }

    private void addMyLocation(double lat, double lng, String address) {
        try {
            LatLng sydney = new LatLng(lat, lng);
            CameraUpdate center = CameraUpdateFactory.newLatLng(sydney);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
            mGoogleMap.addMarker(new MarkerOptions().position(sydney).title(address));
            mGoogleMap.moveCamera(center);
            mGoogleMap.animateCamera(zoom);
        } catch (Exception e) {

        }
    }

    @Override
    public void onScrollChanged(CustomNestedScrollView scrollView, int x, int y, int oldx, int oldy) {
        View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff == 0) {
            if (isLoadingMore) {
                isLoadingMore = false;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //load comment
                        placeViewModel.loadComment(mTravel.getId(), page);
                        page++;
                    }
                }, 500);

            }
        }
    }

    @Override
    public void onPostComment(String comment, Integer parentId) {
        Account account = MyApplication.getInstance().getAccount();
        placeViewModel.postComment(account.getId(), comment, mTravel.getId(), mTravel.getContent_type(), parentId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.KeyBroadcast.KEY_LOGIN_SCREEN)) {
                Account account = MyApplication.getInstance().getAccount();
                setImageUrl(account.getImageProfile(), binding.avatar);
            }
        }

    };

    @Override
    public void onSelect() {
        if (null != photoGalleryAdapter) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(Constants.IntentKey.KEY_FRAGMENT, photoGalleryAdapter.getListPhoto());
            mActivity.setBundle(bundle);
            mActivity.switchFragment(SlideMenu.MenuType.PHOTO_GALLERY_SCREEN);
        }
    }
}
