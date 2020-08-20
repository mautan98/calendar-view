package com.namviet.vtvtravel.adapter.smalllocation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;

import java.util.List;

public class DetailSmallLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_OVER_VIEW = 0;
    private static final int TYPE_ITEM_INFORMATION = 1;
    private static final int TYPE_ITEM_IMAGE = 2;
    private static final int TYPE_ITEM_RATING = 3;
    private static final int TYPE_ITEM_NEARBY_EXPERIENCE = 4;
    private Context context;
    private ClickItem clickItem;
    private List<GetReviewResponse.Data.Content> reviews;
    private String contentType;

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public class TypeString {
        public static final String TYPE_ITEM_OVER_VIEW = "ABOUT";
        public static final String TYPE_ITEM_INFORMATION = "Infomation";
        public static final String TYPE_ITEM_IMAGE = "GALLERY";
        public static final String TYPE_ITEM_RATING = "FEEDBACK";
        public static final String TYPE_ITEM_NEARBY_EXPERIENCE = "NEARPOINT";
    }

    private List<DetailSmallLocationResponse.Data.Tab> items;

    public DetailSmallLocationAdapter(List<DetailSmallLocationResponse.Data.Tab> items, List<GetReviewResponse.Data.Content> reviews, Context context, ClickItem clickItem) {
        this.context = context;
        this.items = items;
        this.clickItem = clickItem;
        this.reviews = reviews;
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
        private TextView btnShowMoreAndShowLess;
        private TextView tvShortDescription;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvShortDescription = itemView.findViewById(R.id.tvShortDescription);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvContentRating = itemView.findViewById(R.id.tvContentRating);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            webView = itemView.findViewById(R.id.webView);
            btnViewNews = itemView.findViewById(R.id.btnViewNews);
            btnShowMoreAndShowLess = itemView.findViewById(R.id.btnShowMoreAndShowLess);
            webView.getSettings().setJavaScriptEnabled(true);
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
            } else {
                btnShowMoreAndShowLess.setText("Xem thêm");
                webView.setVisibility(View.GONE);
                btnViewNews.setVisibility(View.GONE);
                tvShortDescription.setVisibility(View.VISIBLE);
            }

            btnShowMoreAndShowLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tab.isShow()) {
                        btnShowMoreAndShowLess.setText("Xem thêm");
                        webView.setVisibility(View.GONE);
                        btnViewNews.setVisibility(View.GONE);
                        tvShortDescription.setVisibility(View.VISIBLE);
                        items.get(position).setShow(false);
                    } else {
                        items.get(position).setShow(true);
                        btnShowMoreAndShowLess.setText("Ẩn bớt");
                        tvShortDescription.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        btnViewNews.setVisibility(View.VISIBLE);
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

    public class InformationViewHolder extends RecyclerView.ViewHolder {
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
        private RelativeLayout layoutPrice;
        private LinearLayout layoutOpenType;


        public InformationViewHolder(View itemView) {
            super(itemView);
            tvDistance = itemView.findViewById(R.id.tvDistance);
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

        public void bindItem(int position) {
            this.position = position;
            DetailSmallLocationResponse.Data.Tab tab = items.get(position);

            if(tab.getTel() == null || (tab.getTel() != null && tab.getTel().isEmpty())){
                tvPhone.setText("Số điện thoại: Đang cập nhật");
                btnCopy.setVisibility(View.GONE);
            }else {
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
            tvOpenTime.setText(tab.getRange_time());
            tvOpenState.setText(tab.getType_open());

            if ("Đang đóng".equals(tab.getType_open())) {
                tvOpenState.setTextColor(Color.parseColor("#FF0000"));
            } else {
                tvOpenState.setTextColor(Color.parseColor("#0FB403"));
            }

            tvPrice.setText("Từ " + tab.getPrice_from() + " - " + tab.getPrice_to());

            if(tab.getWebsite() == null ||(tab.getWebsite() != null && tab.getWebsite().isEmpty())){
                tvWebsite.setText("Đang cập nhật");
            }else {
                tvWebsite.setText(tab.getWebsite());
            }

            if (tab.getDistance() != null && !"".equals(tab.getDistance()) && Double.parseDouble(tab.getDistance()) < 1000) {
                tvDistance.setText("Cách bạn " + tab.getDistance() + " m");
            } else if (tab.getDistance() != null && !"".equals(tab.getDistance())) {
                double finalValue = Math.round(Double.parseDouble(tab.getDistance()) / 1000 * 10.0) / 10.0;
                tvDistance.setText("Cách bạn " + finalValue + " km");
            }


            if (Constants.TypeDestination.RESTAURANTS.equals(contentType) || Constants.TypeDestination.HOTELS.equals(contentType)) {
                layoutPrice.setVisibility(View.VISIBLE);
                layoutOpenType.setVisibility(View.GONE);
            } else {
                layoutPrice.setVisibility(View.GONE);
                layoutOpenType.setVisibility(View.VISIBLE);
            }

            tvAddress.setText(tab.getAddress());
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private RecyclerView rclContent;
        private ImageAdapter imageAdapter;

        public ImageViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openImageScreen(0, position);
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            imageAdapter = new ImageAdapter(items.get(position).getItemsGallery(), context, new ImageAdapter.ClickItem() {
                @Override
                public void onClickItem(int position) {
                    openImageScreen(position, ImageViewHolder.this.position);
                }
            });
            rclContent.setAdapter(imageAdapter);


        }

        private void openImageScreen(int position, int positionGet){
            try {
                if (items.get(positionGet).getItemsGallery().size() > 0) {
                    clickItem.onClickImage(position, items.get(positionGet).getItemsGallery());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class RatingViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private RecyclerView rclRating;
        private RatingAdapter ratingAdapter;

        private LinearLayout btnViewMoreComment;
        private TextView tvCommentLeft;
        private RelativeLayout layoutWriteComment;
        private LinearLayout layoutScore;
        private LinearLayout layoutReviewOverview;
        private LinearLayout layoutViewAllComment;


        public RatingViewHolder(View itemView) {
            super(itemView);
            rclRating = itemView.findViewById(R.id.rclRating);
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
                layoutScore.setVisibility(View.VISIBLE);
                layoutReviewOverview.setVisibility(View.VISIBLE);
                if (reviews.size() > 3) {
                    layoutViewAllComment.setVisibility(View.VISIBLE);
                } else {
                    layoutViewAllComment.setVisibility(View.GONE);
                }
            } else {
                layoutScore.setVisibility(View.GONE);
                layoutReviewOverview.setVisibility(View.GONE);
                layoutViewAllComment.setVisibility(View.GONE);
            }
            ratingAdapter = new RatingAdapter(reviews, context, null, true);
            rclRating.setAdapter(ratingAdapter);
            tvCommentLeft.setText("Xem tất cả " + reviews.size() + " đánh giá");
        }

    }

    public class NearbyExperienceViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private RecyclerView rclContent;
        private SubNearbyExperienceInSmallLocationDetailAdapter subNearbyExperienceInSmallLocationDetailAdapter;

        public NearbyExperienceViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
        }

        public void bindItem(int position) {
            this.position = position;
            subNearbyExperienceInSmallLocationDetailAdapter = new SubNearbyExperienceInSmallLocationDetailAdapter(items.get(position).getItems(), context, new SubNearbyExperienceInSmallLocationDetailAdapter.ClickItem() {
                @Override
                public void onClickItem(Travel travel) {

                }
            });

            rclContent.setAdapter(subNearbyExperienceInSmallLocationDetailAdapter);


        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);

        void onClickRating();

        void onClickWriteReview();

        void onClickImage(int position, List<String> listImage);

        void onClickLinkShare();
    }


}
