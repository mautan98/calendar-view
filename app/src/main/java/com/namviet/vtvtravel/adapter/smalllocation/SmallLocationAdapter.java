package com.namviet.vtvtravel.adapter.smalllocation;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2smalllocation.Travel;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;

import java.util.List;

public class SmallLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;

    private List<Travel> items;

    public SmallLocationAdapter(List<Travel> items, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.items = items;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_eating, parent, false);
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
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvRating;
        private TextView tvRateText;
        private TextView tvCommentCount;
        private TextView tvDistance;
        private TextView tvAddress;
        private TextView tvOpenDate;
        private TextView tvOpenTime;
        private TextView tvOpenState;
        private TextView tvPrice;

        private LinearLayout layoutOpen;
        private LinearLayout layoutPrice;
        private View viewTime;
        private LikeButton imgHeart;

        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            viewTime = itemView.findViewById(R.id.viewTime);
            tvName = itemView.findViewById(R.id.tvName);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvRateText = itemView.findViewById(R.id.tvRateText);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
            tvOpenState = itemView.findViewById(R.id.tvOpenState);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgHeart = itemView.findViewById(R.id.imgHeart);

            layoutOpen = itemView.findViewById(R.id.layoutOpen);
            layoutPrice = itemView.findViewById(R.id.layoutPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(items.get(position));
                }
            });
        }

        private void clickHeart(){
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

        public void bindItem(int position) {
            this.position = position;

            Travel travel = items.get(position);

            try {
                if (travel.isLiked()) {
//                    imgHeart.setImageResource(R.drawable.f2_ic_heart);
                    imgHeart.setLiked(true);
                } else {
//                    imgHeart.setImageResource(R.drawable.f2_ic_transparent_heart);
                    imgHeart.setLiked(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            tvName.setText(travel.getName());
            tvDescription.setText(travel.getShort_description());
            Glide.with(context).load(travel.getLogo_url()).into(imgAvatar);
            tvCommentCount.setText(travel.getComment_count());
            tvAddress.setText(travel.getAddress());
            tvRating.setText(travel.getEvaluate());
            tvRateText.setText(travel.getEvaluate_text());

            if (Constants.TypeDestination.RESTAURANTS.equals(travel.getContent_type()) || Constants.TypeDestination.HOTELS.equals(travel.getContent_type())) {
                layoutPrice.setVisibility(View.VISIBLE);
                layoutOpen.setVisibility(View.GONE);
                tvPrice.setText(travel.getPrice_from() + " đ" + " - " + travel.getPrice_to() + " đ");
            } else {
                layoutPrice.setVisibility(View.GONE);
                layoutOpen.setVisibility(View.VISIBLE);
                tvOpenDate.setText(travel.getOpen_week());
                tvOpenState.setText(travel.getType_open());


                try {
                    tvOpenState.setTextColor(Color.parseColor(travel.getTypeOpenColor()));
                } catch (Exception e) {
                    try {
                        tvOpenState.setTextColor(Color.parseColor("#FF0000"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
                }


                try {
                    if (travel.getRange_time().isEmpty()) {
                        viewTime.setVisibility(View.GONE);
                        tvOpenTime.setVisibility(View.GONE);
                    } else {
                        viewTime.setVisibility(View.VISIBLE);
                        tvOpenTime.setText(travel.getRange_time());
                        tvOpenTime.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    viewTime.setVisibility(View.GONE);
                    tvOpenTime.setVisibility(View.GONE);
                }
            }

            try {
                if (travel.isHas_location()) {
                    if (travel.getDistance() != null && !"".equals(travel.getDistance()) && Double.parseDouble(travel.getDistance()) < 1000) {
                        tvDistance.setText(travel.getDistance_text()+" " + travel.getDistance() + " m");
                    } else if (travel.getDistance() != null && !"".equals(travel.getDistance())) {
                        double finalValue = Math.round(Double.parseDouble(travel.getDistance()) / 1000 * 10.0) / 10.0;
                        tvDistance.setText(travel.getDistance_text()+" " + finalValue + " km");
                    }
                } else {
                    tvDistance.setText("Không xác định");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface ClickItem {
        void onClickItem(Travel travel);

        void likeEvent(int position);
    }

}
