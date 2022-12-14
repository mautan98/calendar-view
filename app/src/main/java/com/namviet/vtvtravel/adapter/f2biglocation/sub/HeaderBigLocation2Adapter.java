package com.namviet.vtvtravel.adapter.f2biglocation.sub;

import android.app.Activity;
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
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

import java.util.List;

public class HeaderBigLocation2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;

    private List<Travel> items;

    public HeaderBigLocation2Adapter(List<Travel> items, Context context, ClickItem clickItem) {
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_big_location_header_2, parent, false);
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
        private ImageView imgBanner;
        private TextView tvPlace;
        private TextView tvName;
        private TextView tvRate;
        private TextView tvRateText;
        private TextView tvCommentCount;
        private TextView tvDistance;
        private LikeButton imgHeart;

        private TextView tvPriceRange;
        private TextView tvOpenDate;
        private TextView tvOpenTime;
        private TextView tvStatus;

        private LinearLayout linearOpenType;
        private LinearLayout linearPriceType;

        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.imgBanner);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            tvName = itemView.findViewById(R.id.tvName);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvRateText = itemView.findViewById(R.id.tvRateText);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvPriceRange = itemView.findViewById(R.id.tvPriceRange);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            linearOpenType = itemView.findViewById(R.id.linearOpenType);
            linearPriceType = itemView.findViewById(R.id.linearPriceType);
            imgHeart = itemView.findViewById(R.id.imgHeart);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SmallLocationActivity.startScreenDetail((Activity) context, SmallLocationActivity.OpenType.DETAIL, items.get(position).getDetail_link());
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
            Glide.with(context).load(travel.getLogo_url()).into(imgBanner);
            tvName.setText(travel.getName());
            tvRate.setText(travel.getEvaluate());
            tvRateText.setText(travel.getEvaluate_text());
            tvPlace.setText(travel.getRegion_name());
            tvCommentCount.setText(travel.getComment_count());

            if (Constants.TypeDestination.RESTAURANTS.equals(travel.getContent_type()) || Constants.TypeDestination.HOTELS.equals(travel.getContent_type())) {
                linearPriceType.setVisibility(View.VISIBLE);
                linearOpenType.setVisibility(View.GONE);
                tvPriceRange.setText(travel.getPrice_from() + " ??" + " - " + travel.getPrice_to() + " ??");
            } else {
                linearPriceType.setVisibility(View.GONE);
                linearOpenType.setVisibility(View.VISIBLE);
                tvOpenDate.setText(travel.getOpen_week());
                tvOpenTime.setText(travel.getRange_time());
                tvStatus.setText(travel.getType_open());

                try {
                    tvStatus.setTextColor(Color.parseColor(travel.getTypeOpenColor()));
                } catch (Exception e) {
                    try {
                        tvStatus.setTextColor(Color.parseColor("#FF0000"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
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
                    tvDistance.setText("Kh??ng x??c ?????nh");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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
    }


    public interface ClickItem {
        void onClickItem(Travel travel);
        void likeEvent(int position);
    }


}
