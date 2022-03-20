package com.namviet.vtvtravel.adapter.smalllocation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse;
import com.namviet.vtvtravel.view.f2.MapActivity;
import com.namviet.vtvtravel.view.f2.SlideImageActivity;
import com.namviet.vtvtravel.view.f3.smalllocation.activities.NearByExperienceActivity;
import com.namviet.vtvtravel.view.fragment.f2smalllocation.DetailSmallLocationFragment;
import com.namviet.vtvtravel.viewmodel.f2smalllocation.DetailSmallLocationViewModel;
import com.namviet.vtvtravel.viewmodel.f2smalllocation.SmallLocationViewModel;
import com.namviet.vtvtravel.widget.AppBarStateChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DetailSmallLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_OVER_VIEW = 0;
    private static final int TYPE_ITEM_INFORMATION = 1;
    private static final int TYPE_ITEM_IMAGE = 2;
    private static final int TYPE_ITEM_RATING = 3;
    private static final int TYPE_ITEM_NEARBY_EXPERIENCE = 4;
    private static final int TYPE_ITEM_MORE = 5;
    private BitmapDescriptor mMarkerIcon;
    private Context context;
    private ClickItem clickItem;
    private List<GetReviewResponse.Data.Content> reviews;
    private String contentType;
    private DetailSmallLocationFragment detailSmallLocationFragment;
    private SmallLocationViewModel viewModel;

    public void setContentType(String contentType) {
        this.contentType = contentType;
        this.mMarkerIcon = getMarkerFromContentType(contentType);
    }


    public class TypeString {
        public static final String TYPE_ITEM_OVER_VIEW = "ABOUT";
        public static final String TYPE_ITEM_INFORMATION = "Infomation";
        public static final String TYPE_ITEM_IMAGE = "GALLERY";
        public static final String TYPE_ITEM_RATING = "FEEDBACK";
        public static final String TYPE_ITEM_NEARBY_EXPERIENCE = "NEARPOINT";
        public static final String TYPE_ITEM_MORE = "MORE";
    }

    private List<DetailSmallLocationResponse.Data.Tab> items;

    public DetailSmallLocationAdapter(DetailSmallLocationFragment detailSmallLocationFragment,
                                      List<DetailSmallLocationResponse.Data.Tab> items,
                                      List<GetReviewResponse.Data.Content> reviews,
                                      Context context, ClickItem clickItem) {
        this.context = context;
        this.items = items;
        this.clickItem = clickItem;
        this.reviews = reviews;
        this.detailSmallLocationFragment = detailSmallLocationFragment;
        MapsInitializer.initialize(context);
        this.mMarkerIcon = getMarkerFromContentType("");
        viewModel = new SmallLocationViewModel();


    }

    private BitmapDescriptor getMarkerFromContentType(String contentType) {


        switch (contentType) {
            case Constants.TypeSchedule.RESTAURANTS:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what);
            case Constants.TypeSchedule.CENTERS:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what);
            case Constants.TypeSchedule.PLACES:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where);
            case Constants.TypeSchedule.HOTEL:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what);
            default:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (items.get(position).getCode()) {
            case TypeString.TYPE_ITEM_OVER_VIEW:
                return TYPE_ITEM_OVER_VIEW;
            case TypeString.TYPE_ITEM_INFORMATION:
                return TYPE_ITEM_INFORMATION;
            case TypeString.TYPE_ITEM_IMAGE:
                return TYPE_ITEM_IMAGE;

            case TypeString.TYPE_ITEM_RATING:
                return TYPE_ITEM_RATING;
            case TypeString.TYPE_ITEM_NEARBY_EXPERIENCE:
                return TYPE_ITEM_NEARBY_EXPERIENCE;

        }
        return TYPE_ITEM_OVER_VIEW;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM_OVER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_detail_small_location_overview, parent, false);
            return new HeaderViewHolder(v);
        }
        if (viewType == TYPE_ITEM_INFORMATION) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_detail_small_location_information, parent, false);
            return new InformationViewHolder(v);
        }
        if (viewType == TYPE_ITEM_IMAGE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_detail_small_location_image, parent, false);
            return new ImageViewHolder(v);
        }
        if (viewType == TYPE_ITEM_RATING) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_detail_small_location_rating, parent, false);
            return new RatingViewHolder(v);
        }
        if (viewType == TYPE_ITEM_NEARBY_EXPERIENCE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_detail_small_location_nearby_experience, parent, false);
            return new NearbyExperienceViewHolder(v);
        }
        if (viewType == TYPE_ITEM_MORE ) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_detail_small_location_nearby_experience, parent, false);
            return new NearbyExperienceViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM_OVER_VIEW) {
                ((HeaderViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_ITEM_INFORMATION) {
                ((InformationViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_ITEM_IMAGE) {
                ((ImageViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_ITEM_RATING) {
                ((RatingViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_ITEM_NEARBY_EXPERIENCE) {
                ((NearbyExperienceViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return items.size();
        } catch (Exception e) {
            return 0;
        }

    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private TextView tvName;
        private TextView tvRating;
        private TextView tvContentRating;
        private TextView tvCommentCount;
        private WebView webView;
        private TextView btnViewNews;
        private View btnViewRating;
        private TextView btnShowMoreAndShowLess;
        private TextView tvShortDescription;
        private RelativeLayout btnControl;
        private ImageView imgDown;
        private ImageView imgUp;
        private int count;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvShortDescription = itemView.findViewById(R.id.tvShortDescription);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvContentRating = itemView.findViewById(R.id.tvContentRating);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            webView = itemView.findViewById(R.id.webView);
            btnViewRating = itemView.findViewById(R.id.btnViewRating);
            btnViewNews = itemView.findViewById(R.id.btnViewNews);
            btnShowMoreAndShowLess = itemView.findViewById(R.id.btnShowMoreAndShowLess);
            btnControl = itemView.findViewById(R.id.btnControl);
            imgDown = itemView.findViewById(R.id.imgDown);
            imgUp = itemView.findViewById(R.id.imgUp);
            webView.getSettings().setJavaScriptEnabled(true);
            btnViewRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItem.onClickViewRating();
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            DetailSmallLocationResponse.Data.Tab tab = items.get(position);
            tvName.setText(tab.getName());
            tvRating.setText(tab.getEvaluate());
            tvContentRating.setText(tab.getEvaluate_text());
            tvCommentCount.setText(tab.getComment_count());
            webView.loadDataWithBaseURL("", tab.getDescription(), "text/html", "UTF-8", null);
            tvShortDescription.setText(tab.getShort_description());

            if (tab.isShow()) {
                btnShowMoreAndShowLess.setText("Ẩn bớt");
                webView.setVisibility(View.VISIBLE);
                btnViewNews.setVisibility(View.VISIBLE);
                tvShortDescription.setVisibility(View.GONE);
                imgDown.setVisibility(View.GONE);
                imgUp.setVisibility(View.VISIBLE);
            } else {
                btnShowMoreAndShowLess.setText("Xem thêm");
                webView.setVisibility(View.GONE);
                btnViewNews.setVisibility(View.GONE);
                tvShortDescription.setVisibility(View.VISIBLE);
                imgDown.setVisibility(View.VISIBLE);
                imgUp.setVisibility(View.GONE);
            }

            btnControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tab.isShow()) {
                        btnShowMoreAndShowLess.setText("Xem thêm");
                        webView.setVisibility(View.GONE);
                        btnViewNews.setVisibility(View.GONE);
                        tvShortDescription.setVisibility(View.VISIBLE);
                        items.get(position).setShow(false);
                        imgDown.setVisibility(View.VISIBLE);
                        imgUp.setVisibility(View.GONE);
                    } else {
                        items.get(position).setShow(true);
                        btnShowMoreAndShowLess.setText("Ẩn bớt");
                        tvShortDescription.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        btnViewNews.setVisibility(View.VISIBLE);
                        imgDown.setVisibility(View.GONE);
                        imgUp.setVisibility(View.VISIBLE);
                    }
                }
            });


            btnViewNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickLinkShare();
                }
            });
        }
    }

    public class InformationViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        private int position;
        private TextView tvDistance;
        private TextView tvAddress;
        private TextView tvWebsite;
        private TextView tvPhone;
        private TextView btnCopy;
        private TextView tvOpenDate;
        private TextView tvOpenTime;
        private TextView tvOpenState;
        private ImageView imgIcon;
        private TextView tvPrice;
        private LinearLayout layoutPhone;
        private LinearLayout layoutURL;
        private RelativeLayout layoutPrice;
        private LinearLayout layoutOpenType;
        private MapView mapView;

        private SupportMapFragment mapFragment;
        private GoogleMap mGoogleMap;



        public InformationViewHolder(View itemView) {
            super(itemView);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            mapView = itemView.findViewById(R.id.mapView);
            layoutPhone = itemView.findViewById(R.id.layoutPhone);
            layoutURL = itemView.findViewById(R.id.layoutURL);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvWebsite = itemView.findViewById(R.id.tvWebsite);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            btnCopy = itemView.findViewById(R.id.btnCopy);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
            tvOpenState = itemView.findViewById(R.id.tvOpenState);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            layoutPrice = itemView.findViewById(R.id.layoutPrice);
            layoutOpenType = itemView.findViewById(R.id.layoutOpenType);
        }

        private void addMyLocation(double lat, double lng, String address) {
            try {
                LatLng coordinate = new LatLng(lat, lng); //Store these lat lng values somewhere. These should be constant.
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(coordinate)
                        .icon(mMarkerIcon)
                        .title(address));
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lat + 0.001, lng), 15);
                mGoogleMap.moveCamera(location);

//                mGoogleMap.animateCamera(zoom);
            } catch (Exception e) {

            }
        }

        public void bindItem(int position) {
            this.position = position;
            DetailSmallLocationResponse.Data.Tab tab = items.get(position);


            try {
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        try {
                            mGoogleMap = googleMap;
                            addMyLocation(Double.parseDouble(tab.getLat()), Double.parseDouble(tab.getLng()), tab.getAddress());

                            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {
                                    try {
                                        MapActivity.startScreen(context, tab.getLat(), tab.getLng(), tab.getAddress(), contentType);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (tab.getTel() == null || (tab.getTel() != null && tab.getTel().isEmpty())) {
                tvPhone.setText("Số điện thoại: Đang cập nhật");
                btnCopy.setVisibility(View.GONE);
                layoutPhone.setVisibility(View.GONE);
            } else {
                layoutPhone.setVisibility(View.VISIBLE);
                tvPhone.setText("Số điện thoại: " + tab.getTel());
                btnCopy.setVisibility(View.VISIBLE);
            }


            btnCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", tab.getTel() + "");
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "Đã sao chép số điện thoại vào bộ nhớ tạm", Toast.LENGTH_SHORT).show();
                }
            });
            tvOpenDate.setText(tab.getOpen_week());

            try {
                if (tab.getRange_time().isEmpty()) {

                    tvOpenTime.setVisibility(View.GONE);
                } else {

                    tvOpenTime.setText(tab.getRange_time());
                    tvOpenTime.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {

                tvOpenTime.setVisibility(View.GONE);
                e.printStackTrace();
            }


            tvOpenState.setText(tab.getType_open());

            try {
                tvOpenState.setTextColor(Color.parseColor(tab.getTypeOpenColor()));
            } catch (Exception e) {
                try {
                    tvOpenState.setTextColor(Color.parseColor("#FF0000"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }


            if (tab.getWebsite() == null || (tab.getWebsite() != null && tab.getWebsite().isEmpty())) {
                layoutURL.setVisibility(View.GONE);
                tvWebsite.setText("Đang cập nhật");
            } else {
                layoutURL.setVisibility(View.VISIBLE);
                tvWebsite.setText(tab.getWebsite());
            }

            try {
                if (tab.isHas_location()) {
                    if (tab.getDistance() != null && !"".equals(tab.getDistance()) && Double.parseDouble(tab.getDistance()) < 1000) {
                        tvDistance.setText(tab.getDistance_text() + " " + tab.getDistance() + " m");
                    } else if (tab.getDistance() != null && !"".equals(tab.getDistance())) {
                        double finalValue = Math.round(Double.parseDouble(tab.getDistance()) / 1000 * 10.0) / 10.0;
                        tvDistance.setText(tab.getDistance_text() + " " + finalValue + " km");
                    }
                } else {
                    tvDistance.setText("Không xác định");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (Constants.TypeDestination.RESTAURANTS.equals(contentType) || Constants.TypeDestination.HOTELS.equals(contentType)) {
                layoutPrice.setVisibility(View.VISIBLE);
                layoutOpenType.setVisibility(View.GONE);
            } else {
                layoutPrice.setVisibility(View.GONE);
                layoutOpenType.setVisibility(View.VISIBLE);
            }

            tvAddress.setText(tab.getAddress());

            if ((tab.getPrice_from() == null || (tab.getPrice_from() != null && tab.getPrice_from().isEmpty()) &&
                    (tab.getPrice_to() == null || (tab.getPrice_to() != null && tab.getPrice_to().isEmpty())))) {
                layoutPrice.setVisibility(View.GONE);
            } else if ((tab.getPrice_to() == null || (tab.getPrice_to() != null && tab.getPrice_to().isEmpty()) || (tab.getPrice_to() != null && tab.getPrice_to().equals("0")))) {
                tvPrice.setText("Từ " + tab.getPrice_from());
            } else {
                tvPrice.setText("Từ " + tab.getPrice_from() + " - " + tab.getPrice_to());
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {

        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private RecyclerView rclContent;
        private GalleryImageAdapter imageAdapter;
        private TextView btnSeeAll;

        public ImageViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            btnSeeAll = itemView.findViewById(R.id.btnSeeAll);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openImageScreen(0, position);
                }
            });

            btnSeeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        SlideImageActivity.startScreen( context, (ArrayList<String>) items.get(position).getItemsGallery(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            imageAdapter = new GalleryImageAdapter(items.get(position).getItemsGallery(), context, new GalleryImageAdapter.ClickItem() {
                @Override
                public void onClickItem(int position) {
                    openImageScreen(position, ImageViewHolder.this.position);
                }
            });
            rclContent.setAdapter(imageAdapter);


        }

        private void openImageScreen(int position, int positionGet) {
            try {
                if (items.get(positionGet).getItemsGallery().size() > 0) {
                    clickItem.onClickImage(position, items.get(positionGet).getItemsGallery());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class RatingViewHolder extends RecyclerView.ViewHolder implements RatingAdapter.ClickItem {
        private int position;
        private RecyclerView rclRating;
        private RatingAdapter ratingAdapter;

        private LinearLayout btnViewMoreComment;
        private TextView tvCommentLeft;
        private RelativeLayout layoutWriteComment;
        private LinearLayout layoutScore;
        private LinearLayout layoutReviewOverview;
        private LinearLayout layoutViewAllComment;

        private ProgressBar progress1;
        private ProgressBar progress2;
        private ProgressBar progress3;
        private ProgressBar progress4;
        private ProgressBar progress5;


        private TextView tvCountRating5;
        private TextView tvCountRating4;
        private TextView tvCountRating3;
        private TextView tvCountRating2;
        private TextView tvCountRating1;

        private TextView tvRating;
        private TextView tvContentRating;
        private TextView tvCommentCount;


        public RatingViewHolder(View itemView) {
            super(itemView);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvContentRating = itemView.findViewById(R.id.tvContentRating);
            rclRating = itemView.findViewById(R.id.rclRating);

            progress1 = itemView.findViewById(R.id.progress1);
            progress2 = itemView.findViewById(R.id.progress2);
            progress3 = itemView.findViewById(R.id.progress3);
            progress4 = itemView.findViewById(R.id.progress4);
            progress5 = itemView.findViewById(R.id.progress5);

            tvCountRating5 = itemView.findViewById(R.id.tvCountRating5);
            tvCountRating4 = itemView.findViewById(R.id.tvCountRating4);
            tvCountRating3 = itemView.findViewById(R.id.tvCountRating3);
            tvCountRating2 = itemView.findViewById(R.id.tvCountRating2);
            tvCountRating1 = itemView.findViewById(R.id.tvCountRating1);

            layoutScore = itemView.findViewById(R.id.layoutScore);
            layoutReviewOverview = itemView.findViewById(R.id.layoutReviewOverview);
            layoutViewAllComment = itemView.findViewById(R.id.layoutViewAllComment);

            btnViewMoreComment = itemView.findViewById(R.id.btnViewMoreComment);
            tvCommentLeft = itemView.findViewById(R.id.tvCommentLeft);
            layoutWriteComment = itemView.findViewById(R.id.layoutWriteComment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickRating();
                }
            });

            btnViewMoreComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickRating();
                }
            });

            layoutWriteComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickWriteReview();
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            if (reviews.size() > 0) {
                try {
                    tvCommentCount.setText(items.get(position).getComment_count());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                layoutScore.setVisibility(View.VISIBLE);
                layoutReviewOverview.setVisibility(View.VISIBLE);
                if (reviews.size() > 3) {
                    layoutViewAllComment.setVisibility(View.VISIBLE);
                } else {
                    layoutViewAllComment.setVisibility(View.GONE);
                }
                progress1.setProgress(getPercent(items.get(position).getCount_1()));
                progress2.setProgress(getPercent(items.get(position).getCount_2()));
                progress3.setProgress(getPercent(items.get(position).getCount_3()));
                progress4.setProgress(getPercent(items.get(position).getCount_4()));
                progress5.setProgress(getPercent(items.get(position).getCount_5()));

                tvCountRating1.setText(items.get(position).getCount_1());
                tvCountRating2.setText(items.get(position).getCount_2());
                tvCountRating3.setText(items.get(position).getCount_3());
                tvCountRating4.setText(items.get(position).getCount_4());
                tvCountRating5.setText(items.get(position).getCount_5());

                tvRating.setText(items.get(position).getEvaluate());
                tvContentRating.setText(items.get(position).getEvaluate_text());
            } else {
                layoutScore.setVisibility(View.GONE);
                layoutReviewOverview.setVisibility(View.GONE);
                layoutViewAllComment.setVisibility(View.GONE);
            }
            ratingAdapter = new RatingAdapter(reviews, context, this, true);
            rclRating.setAdapter(ratingAdapter);
            tvCommentLeft.setText("Xem tất cả " + reviews.size() + " đánh giá");
        }


        private int getPercent(String star) {
            float aFloat = Float.parseFloat(star);
            int percent = (int) (aFloat / Float.parseFloat(items.get(position).getComment_count()) * 100);
            return percent;
        }

        @Override
        public void onClickItem(Travel travel) {

        }

        @Override
        public void likeEvent(int position) {

        }
    }

    public class NearbyExperienceViewHolder extends RecyclerView.ViewHolder implements Observer {
        private int position;
        private RecyclerView rclContent;
        private SubNearbyExperienceInSmallLocationDetailAdapter subNearbyExperienceInSmallLocationDetailAdapter;
        private TabLayout tabs;
        private ArrayList<String> tabsList = new ArrayList<>();
        private ShimmerFrameLayout shimmerFrameLayout;
        private TextView tvViewMore;
        private List<com.namviet.vtvtravel.model.f2smalllocation.Travel> travelList = new ArrayList<>();
        public NearbyExperienceViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            tvViewMore = itemView.findViewById(R.id.tv_view_more);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container);
            tabs = itemView.findViewById(R.id.tabs);
            viewModel.addObserver(this);
        }

        public void bindItem(int position) {
            this.position = position;
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            rclContent.setVisibility(View.INVISIBLE);
            viewModel.getSmallLocation(items.get(position).getMenu().get(0).getLink(),false);
            tvViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NearByExperienceActivity.startScreenDetail(context,NearByExperienceActivity.OpenType.LIST,items.get(position).getMenu());
                }
            });
            this.position = position;
            genTab();
            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    setOnSelectView(tabs, tab.getPosition());
                    loadData(tab.getPosition());
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    setUnSelectView(tabs, tab.getPosition());
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
            subNearbyExperienceInSmallLocationDetailAdapter = new SubNearbyExperienceInSmallLocationDetailAdapter(travelList, context, new SubNearbyExperienceInSmallLocationDetailAdapter.ClickItem() {
                @Override
                public void onClickItem(com.namviet.vtvtravel.model.f2smalllocation.Travel travel) {

                }
            });

            rclContent.setAdapter(subNearbyExperienceInSmallLocationDetailAdapter);
        }
        private void loadData(int i) {
            viewModel.getSmallLocation(items.get(position).getMenu().get(i).getLink(),false);
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            rclContent.setVisibility(View.INVISIBLE);
        }
        private void stopLoading(){
            shimmerFrameLayout.setVisibility(View.GONE);
            rclContent.setVisibility(View.VISIBLE);
        }
        public void setOnSelectView(TabLayout tabLayout, int position) {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View selected = tab.getCustomView();
            TextView iv_text = selected.findViewById(R.id.tvTitle);
            View view = selected.findViewById(R.id.indicator);
            view.setVisibility(View.VISIBLE);
            iv_text.setTextColor(Color.parseColor("#00918D"));

        }

        public void setUnSelectView(TabLayout tabLayout, int position) {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View selected = tab.getCustomView();
            TextView iv_text = selected.findViewById(R.id.tvTitle);
            View view = selected.findViewById(R.id.indicator);
            view.setVisibility(View.INVISIBLE);
            iv_text.setTextColor(Color.parseColor("#101010"));

        }
        private void genTab() {
            tabsList.clear();
            for (int i = 0; i < items.get(position).getMenu().size(); i++) {
                tabsList.add(items.get(position).getMenu().get(i).getName());
            }
            try {
                for (int i = 0; i < tabsList.size(); i++) {
                    View tabHome = LayoutInflater.from(context).inflate(R.layout.f2_custom_tab_vtv_style, null);
                    TextView tvHome = tabHome.findViewById(R.id.tvTitle);
                    tvHome.setText((tabsList.get(i)));
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
                    tabs.addTab(tabs.newTab().setCustomView(tabHome));
                    //            getBinding().tabs.addTab(getBinding().tabs.newTab().setText(detailSmallLocationResponse.getData().getTabs().get(i).getTitle()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(Observable observable, Object o) {
            if (o instanceof SmallLocationResponse) {
                SmallLocationResponse response = (SmallLocationResponse) o;
                if (response.isLoadMore()) {
                    travelList.addAll(response.getData().getItems());
                } else {
                    travelList.clear();
                    travelList.addAll(response.getData().getItems());
                }
                subNearbyExperienceInSmallLocationDetailAdapter.notifyDataSetChanged();
                stopLoading();
            }
        }
    }

    public interface ClickItem {
        void onClickItem(Travel travel);

        void onClickRating();

        void onClickViewRating();

        void onClickWriteReview();

        void onClickImage(int position, List<String> listImage);

        void onClickLinkShare();
    }


}
