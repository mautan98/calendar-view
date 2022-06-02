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
import com.ornach.richtext.RichText;

import java.util.List;

public class HeaderBigLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;

    private List<Travel> items;

    public HeaderBigLocationAdapter(List<Travel> items, Context context, ClickItem clickItem) {
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_big_location_header, parent, false);
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
        private int position;
        private ImageView imgBanner;
        private TextView tvName;
        private TextView tvRate;
        private TextView tvRateText;
        private TextView tvCommentCount;
        private TextView tvAddress;
        private TextView tvDistance;
        private TextView tvPlace;
        private TextView tvOpenDate;
        private TextView tvOpenTime;
//        private View viewTime;
        private LikeButton imgHeart;
        private LinearLayout layoutStandardRate;
        private TextView tvStandardRate;
        private LinearLayout layoutOpen;
        private TextView tvOpenState;
        private RichText viewStatus;
        private TextView tvOpenTime2;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.imgBanner);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
//            viewTime = itemView.findViewById(R.id.viewTime);
            tvName = itemView.findViewById(R.id.tvName);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            tvRateText = itemView.findViewById(R.id.tvRateText);
            imgHeart = itemView.findViewById(R.id.imgHeart);
            layoutOpen = itemView.findViewById(R.id.layoutOpen);
            layoutStandardRate = itemView.findViewById(R.id.layoutStandardRate);
            tvStandardRate = itemView.findViewById(R.id.tvStandardRate);
            tvOpenState = itemView.findViewById(R.id.tvOpenState);
            viewStatus = itemView.findViewById(R.id.viewStatus);
            tvOpenTime2 = itemView.findViewById(R.id.tvOpenTime2);
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
            tvAddress.setText(travel.getAddress());
            tvPlace.setText(travel.getRegion_name());


            try {
                if (travel.getComment_count().endsWith(".0")) {
                    tvCommentCount.setText(String.valueOf((int) Double.parseDouble(travel.getComment_count())));
                } else {
                    tvCommentCount.setText(travel.getComment_count());
                }
            } catch (Exception e) {
                e.printStackTrace();
                tvCommentCount.setText("");
            }



            layoutOpen.setVisibility(View.VISIBLE);
            tvOpenDate.setText(travel.getOpen_week());
            tvOpenState.setText(travel.getType_open());



            try {
                tvOpenState.setTextColor(Color.parseColor(travel.getTypeOpenColor()));
                viewStatus.setBackgroundColor(Color.parseColor(travel.getTypeOpenColor()));
            } catch (Exception e) {
                try {
                    tvOpenState.setTextColor(Color.parseColor("#FF0000"));
                    viewStatus.setBackgroundColor(Color.parseColor("#FF0000"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }


            try {
                if (travel.getRange_time().isEmpty()) {
                    //    viewTime.setVisibility(View.GONE);
                    tvOpenTime.setVisibility(View.GONE);
                    tvOpenTime2.setVisibility(View.GONE);
                } else {
                    //    viewTime.setVisibility(View.VISIBLE);
                    if(travel.getRange_time().contains("và")){
                        String[] strings = travel.getRange_time().split("và");
                        tvOpenTime.setText(strings[0]);
                        tvOpenTime2.setText(strings[1]);
                        tvOpenTime.setVisibility(View.VISIBLE);
                        tvOpenTime2.setVisibility(View.VISIBLE);
                    }else {
                        tvOpenTime.setText(travel.getRange_time());
                        tvOpenTime.setVisibility(View.VISIBLE);
                        tvOpenTime2.setVisibility(View.GONE);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                //   viewTime.setVisibility(View.GONE);
                tvOpenTime.setVisibility(View.GONE);
                tvOpenTime2.setVisibility(View.GONE);
            }


            if (travel.isHas_location()) {
                if (travel.getDistance() != null && !"".equals(travel.getDistance()) && Double.parseDouble(travel.getDistance()) < 1000) {
                    tvDistance.setText(travel.getDistance_text() + " " + travel.getDistance() + " m");
                } else if (travel.getDistance() != null && !"".equals(travel.getDistance())) {
                    double finalValue = Math.round(Double.parseDouble(travel.getDistance()) / 1000 * 10.0) / 10.0;
                    tvDistance.setText(travel.getDistance_text() + " " + finalValue + " km");
                }
            } else {
                tvDistance.setText("Không xác định");
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


            try {
                if(Constants.TypeDestination.HOTELS.equals(travel.getContent_type())){
                    layoutStandardRate.setVisibility(View.VISIBLE);
                    tvStandardRate.setText(travel.getStandard_rate());
                }else {
                    layoutStandardRate.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                layoutStandardRate.setVisibility(View.GONE);
            }


        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);

        void likeEvent(int position);
    }


}
