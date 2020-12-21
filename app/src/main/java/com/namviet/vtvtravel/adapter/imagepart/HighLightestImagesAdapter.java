package com.namviet.vtvtravel.adapter.imagepart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.activity.BaseActivity;
import com.devs.readmoreoption.ReadMoreOption;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.response.imagepart.ItemImagePartResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.f2.CommentActivity;
import com.namviet.vtvtravel.view.f2.ShareActivity;
import com.namviet.vtvtravel.view.fragment.share.ShareBottomDialog;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

import java.util.List;

public class HighLightestImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private ReadMoreOption readMoreOption;

    private List<ItemImagePartResponse.Data.Item> images;

    public HighLightestImagesAdapter(List<ItemImagePartResponse.Data.Item> images, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.images = images;

        readMoreOption = new ReadMoreOption.Builder(context)
                .labelUnderLine(true)
                .lessLabel(" Ẩn bớt")
                .lessLabelColor(Color.parseColor("#000000"))
                .moreLabel(" Xem thêm")
                .moreLabelColor(Color.parseColor("#000000"))
                .build();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_gallery, parent, false);
            return new HeaderViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return images.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvScrollPosition;
        private TextView tvDate;
        private TextView tvAuthor;
        private TextView tvView;
        private ViewPager vpGallery;
        private TextView tvCommentCount;
        private ImageView btnShare;
        private ImageView imgComment;
        private TextView tvDescription;
        private TextView tvReadMore;
        private TextView tvCountLike;
        private LikeButton imgHeart;
        private int position;
        private SlideImageInHighLightestImageAdapter slideImageInHighLightestImageAdapter;
        private IndefinitePagerIndicator vpIndicator;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvScrollPosition = itemView.findViewById(R.id.tvScrollPosition);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvView = itemView.findViewById(R.id.tvView);
            vpGallery = itemView.findViewById(R.id.vpGallery);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            btnShare = itemView.findViewById(R.id.btnShare);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            imgComment = itemView.findViewById(R.id.imgComment);
            tvCountLike = itemView.findViewById(R.id.tvCountLike);
            imgHeart = itemView.findViewById(R.id.imgHeart);
            vpIndicator = itemView.findViewById(R.id.vpIndicator);
        }

        public void bindItem(int position) {
            this.position = position;
            ItemImagePartResponse.Data.Item item = images.get(position);
            tvName.setText(item.getName());
            tvAuthor.setText(item.getAuthor());

            readMoreOption.addReadMoreTo(tvDescription, item.getShort_description());

            tvCommentCount.setText(item.getCount_comment());
            tvView.setText(item.getView_count());
            tvDate.setText("" + DateUtltils.timeToString(Long.valueOf(item.getCreated())));


            slideImageInHighLightestImageAdapter = new SlideImageInHighLightestImageAdapter(context, item.getThumb_url(), SlideImageInHighLightestImageAdapter.LIST_TYPE, new SlideImageInHighLightestImageAdapter.ClickItem() {
                @Override
                public void onClickItem() {
                    try {
                        TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.CORNER_PHOTO, TrackingAnalytic.ScreenTitle.CORNER_PHOTO)
                                .setContent_id(item.getId())
                                .setContent_type(item.getContent_type())
                                .setCategory_tree_name(item.getCategory_tree_name())
                                .setCategory_tree_code(item.getCategory_tree_code()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            vpGallery.setAdapter(slideImageInHighLightestImageAdapter);
            try {
                tvScrollPosition.setText("1/" + item.getThumb_url().size());
            } catch (Exception e) {
                tvScrollPosition.setText("0/0");
            }

            imgComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailTravelNewsResponse detailTravelNewsResponse = new DetailTravelNewsResponse();
                    DetailTravelNewsResponse.Data data = new DetailTravelNewsResponse().new Data();
                    data.setId(item.getId());
                    data.setContent_type(item.getContent_type());
                    detailTravelNewsResponse.setData(data);
                    CommentActivity.startScreen((Activity) context, detailTravelNewsResponse, null);
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        ShareBottomDialog shareBottomDialog = new ShareBottomDialog(new ShareBottomDialog.DoneClickShare() {
                            @Override
                            public void onDoneClickShare(boolean isVTVApp) {
                                if (isVTVApp) {
                                    ShareActivity.startScreen(context, item.getName(), "http://domain.vtv?gallery_id="+item.getId(), item.getLogo_url(), "galleries");
                                }else {
                                    F2Util.startSenDataText((Activity) context, item.getLink_share());
                                }
                            }
                        });
                        if(context instanceof BaseActivity) {
                            shareBottomDialog.show(((BaseActivity) context).getSupportFragmentManager(), null);
                        }else {
                            shareBottomDialog.show(((BaseActivityNew) context).getSupportFragmentManager(), null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        TrackingAnalytic.postEvent(TrackingAnalytic.SHARE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.HIGH_LIGHTEST_IMAGE, TrackingAnalytic.ScreenTitle.HIGH_LIGHTEST_IMAGE)
                                .setContent_id(item.getId())
                                .setContent_type(item.getContent_type())
                                .setScreen_class(this.getClass().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            vpGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    try {
                        tvScrollPosition.setText((String.valueOf(position + 1) + "/" + item.getThumb_url().size()));
                    } catch (Exception e) {
                        tvScrollPosition.setText("0/0");
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            imgHeart.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    clickItem.likeEvent(position);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    clickItem.likeEvent(position);
                }
            });

//            imgHeart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clickItem.likeEvent(position);
//                }
//            });

            try {
                if (item.isLiked()){
//                    imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
                    imgHeart.setLiked(true);
                } else {
//                    imgHeart.setImageResource(R.drawable.f2_ic_gray_heart);
                    imgHeart.setLiked(false);
                }
                tvCountLike.setText(item.getLikeCount());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                vpIndicator.attachToViewPager(vpGallery);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public interface ClickItem {
        void onClickItem(int position);
        void likeEvent(int position);
    }


}
