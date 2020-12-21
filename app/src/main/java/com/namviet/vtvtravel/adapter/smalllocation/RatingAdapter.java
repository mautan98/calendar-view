package com.namviet.vtvtravel.adapter.smalllocation;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private boolean inDetailScreen;

    private List<GetReviewResponse.Data.Content> items;

    public RatingAdapter(List<GetReviewResponse.Data.Content> items, Context context, ClickItem clickItem, boolean inDetailScreen) {
        this.context = context;
        this.clickItem = clickItem;
        this.items = items;
        this.inDetailScreen = inDetailScreen;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_review, parent, false);
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
        if (items == null) {
            return 0;
        } else if (inDetailScreen) {
            if (items.size() > 3) {
                return 3;
            } else {
                return items.size();
            }
        } else {
            return items.size();
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private int position;
        private RecyclerView rclImage;
        private ImageAdapter imageAdapter;

        private TextView tvName;
        private ImageView imgLevel;
        private TextView tvRating;
        private TextView tvContentRating;
        private TextView tvTime;
        private TextView tvComment;
        private TextView tvCountLike;
        private TextView tvReply;
        private LikeButton imgHeart;
        private TextView getTvCountLike;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            imgLevel = itemView.findViewById(R.id.imgLevel);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvContentRating = itemView.findViewById(R.id.tvContentRating);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvComment = itemView.findViewById(R.id.tvComment);
            rclImage = itemView.findViewById(R.id.rclImage);
            tvCountLike = itemView.findViewById(R.id.tvCountLike);
            tvReply = itemView.findViewById(R.id.tvReply);
            imgHeart = itemView.findViewById(R.id.imgHeart);
            tvCountLike = itemView.findViewById(R.id.tvCountLike);
        }

        public void bindItem(int position) {
            this.position = position;
            GetReviewResponse.Data.Content content = items.get(position);

            try {
                if(content.getGalleryUris().size() == 0){
                    rclImage.setVisibility(View.GONE);
                }else {
                    rclImage.setVisibility(View.VISIBLE);
                    imageAdapter = new ImageAdapter(content.getGalleryUris(), context, new ImageAdapter.ClickItem() {
                        @Override
                        public void onClickItem(int position) {

                        }
                    });
                    rclImage.setAdapter(imageAdapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            tvName.setText(content.getUser().getFullname());
            tvComment.setText(content.getContent());
            Glide.with(context).load(content.getUser().getImageProfile()).into(imgAvatar);
            try {
                tvTime.setText(DateUtltils.convertTime(content.getCreated()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvRating.setText(content.getPostRate() == null ? "0" : content.getPostRate());
            try {
                tvContentRating.setText(genRatingText(content.getPostRate() == null ? 0 : Integer.parseInt(content.getPostRate())));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (content.isLiked()) {
//                    imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
                    imgHeart.setLiked(true);
                } else {
//                    imgHeart.setImageResource(R.drawable.f2_ic_gray_heart);
                    imgHeart.setLiked(false);
                }
                tvCountLike.setText(content.getLikeCount());
            } catch (Exception e) {
                e.printStackTrace();
            }
//            imgHeart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clickItem.likeEvent(position);
//                }
//            });
            imgHeart.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickHeart();
                        }
                    }, 100);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickHeart();
                        }
                    }, 100);
                }
            });
        }

        private void clickHeart(){
            clickItem.likeEvent(position);
        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);

        void likeEvent(int position);
    }

    private String genRatingText(int rate) {
        switch (rate) {
            case 0:
                return "Kém";
            case 1:
                return "Kém";
            case 2:
                return "Trung bình";
            case 3:
                return "Hài lòng";
            case 4:
                return "Rất tốt";
            case 5:
                return "Tuyệt vời";


        }
        return "Kém";

    }
}
