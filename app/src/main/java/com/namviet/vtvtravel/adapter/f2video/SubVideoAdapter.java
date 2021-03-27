package com.namviet.vtvtravel.adapter.f2video;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.activity.BaseActivity;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.f2.DetailVideoActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.ShareActivity;
import com.namviet.vtvtravel.view.f2.TagVideoActivity;
import com.namviet.vtvtravel.view.fragment.share.ContactShareFragment;
import com.namviet.vtvtravel.view.fragment.share.ShareBottomDialog;

import java.util.List;

public class SubVideoAdapter extends RecyclerView.Adapter<SubVideoAdapter.HeaderViewHolder> {
    private Context context;
    private List<Video> videos;
    private ClickItem clickItem;

    public SubVideoAdapter(Context context, List<Video> videos, ClickItem clickItem) {
        this.videos = videos;
        this.context = context;
        this.clickItem = clickItem;
    }


    @NonNull
    @Override
    public SubVideoAdapter.HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_video, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubVideoAdapter.HeaderViewHolder holder, int position) {
        try {
            holder.bindItem(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return videos.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rclTag;
        private TextView tvTitle, tvType, tvDate, tvView;
        private ImageView imgBanner;
        private ImageView btnShare;
        private TagVideoAdapter tagVideoAdapter;
        private LikeButton imgHeart;
        private TextView tvCountLike;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            rclTag = itemView.findViewById(R.id.rclTag);
            imgBanner = itemView.findViewById(R.id.imgBanner);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvType = itemView.findViewById(R.id.tvType);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvView = itemView.findViewById(R.id.tvView);
            btnShare = itemView.findViewById(R.id.btnShare);
            rclTag = itemView.findViewById(R.id.rclTag);
            imgHeart = itemView.findViewById(R.id.imgHeart);
            tvCountLike = itemView.findViewById(R.id.tvCountLike);
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context);
            rclTag.setLayoutManager(flexboxLayoutManager);
        }

        public void bindItem(int position) {
            try {
                Glide.with(context).load(videos.get(position).getLogo_url()).into(imgBanner);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                int value = videos.get(position).getView_count();
                if (value > 1000) {
                    int finalValue = Math.round(value / 1000 * 10) / 10;
                    tvView.setText(String.valueOf(finalValue) + "k");
                } else {
                    tvView.setText(String.valueOf(value));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tvDate.setText(DateUtltils.timeToString(videos.get(position).getCreated()));
                tvTitle.setText(videos.get(position).getName());
                if(videos.get(position).getCategory() != null){
                    tvType.setText(videos.get(position).getCategory().getName());
                }
                tvType.setText(videos.get(position).getCategory_tree_name());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                tagVideoAdapter = new TagVideoAdapter(context, videos.get(position).getHash_tags(), new TagVideoAdapter.ClickItemTagVideo() {
                    @Override
                    public void onClickItemTagVideo(String tag) {

                        TagVideoActivity.startScreen((Activity) context, tag);
                    }
                });
                rclTag.setAdapter(tagVideoAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailVideoActivity.startScreen((Activity) context, videos.get(position).getDetail_link());
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
                                    ShareActivity.startScreen(context, videos.get(position).getName(), videos.get(position).getDetail_link(), videos.get(position).getLogo_url(), "videos");
                                }else {
//                                    String linkShare = WSConfig.HOST_LANDING+F2Util.genEndPointShareLink(Constants.ShareLinkType.VIDEO, videos.get(position).getDetail_link());
//                                    F2Util.startSenDataText((Activity) context, linkShare);
                                    F2Util.startSenDataText((Activity) context, videos.get(position).getLink_share());
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
                        TrackingAnalytic.postEvent(TrackingAnalytic.SHARE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.VIDEOS, TrackingAnalytic.ScreenTitle.VIDEOS)
                                .setContent_type(videos.get(position).getContent_type())
                                .setContent_id(videos.get(position).getId())
                                .setScreen_class(this.getClass().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            try {
                tvCountLike.setText(videos.get(position).getLikeCount());
                if (videos.get(position).isLiked()) {
//                    imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
                    imgHeart.setLiked(true);
                } else {
//                    imgHeart.setImageResource(R.drawable.f2_ic_gray_heart);
                    imgHeart.setLiked(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            imgHeart.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickHeart(position);
                        }
                    }, 100);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickHeart(position);
                        }
                    }, 100);
                }
            });

//            imgHeart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        Account account = MyApplication.getInstance().getAccount();
//                        if (null != account && account.isLogin()) {
//                            clickItem.likeEvent(position);
//                        } else {
//                            LoginAndRegisterActivityNew.startScreen(context, 0, false);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

        }

        private void clickHeart(int position){
            try {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    clickItem.likeEvent(position);
                } else {
                    LoginAndRegisterActivityNew.startScreen(context, 0, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public interface ClickItem {
        void onClickItem(Video video);

        void likeEvent(int position);
    }


}
