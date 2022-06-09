package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.ornach.richtext.RichText;

import java.util.List;

public class SubRecentViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private List<DetailSmallLocationResponse> detailSmallLocationResponses;

    public SubRecentViewAdapter(Context context, List<DetailSmallLocationResponse> detailSmallLocationResponses) {
        this.context = context;
        this.detailSmallLocationResponses = detailSmallLocationResponses;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_home_suggestion_location, parent, false);
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
            return detailSmallLocationResponses.size();
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
        private TextView tvOpenState;
        private TextView tvAddress;

        private LinearLayout layoutOpen;
//        private LinearLayout linearPriceType;

        private int position;
        private View viewTime;
        private RichText viewStatus;
        private TextView tvOpenTime2;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.imgBanner);
            viewTime = itemView.findViewById(R.id.viewTime);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            tvName = itemView.findViewById(R.id.tvName);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvRateText = itemView.findViewById(R.id.tvRateText);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPriceRange = itemView.findViewById(R.id.tvPriceRange);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
            tvOpenState = itemView.findViewById(R.id.tvOpenState);
            layoutOpen = itemView.findViewById(R.id.layoutOpen);
//            linearPriceType = itemView.findViewById(R.id.linearPriceType);
            imgHeart = itemView.findViewById(R.id.imgHeart);
            viewStatus = itemView.findViewById(R.id.viewStatus);
            tvOpenTime2 = itemView.findViewById(R.id.tvOpenTime2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SmallLocationActivity.startScreenDetail((Activity) context, SmallLocationActivity.OpenType.DETAIL, detailSmallLocationResponses.get(position).getDetailLink());
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            DetailSmallLocationResponse.Data travel = detailSmallLocationResponses.get(position).getData();
            Glide.with(context).load(travel.getBanner_url()).into(imgBanner);
            tvName.setText(travel.getTabs().get(0).getName());
            tvRate.setText(travel.getTabs().get(0).getEvaluate());
            tvRateText.setText(travel.getTabs().get(0).getEvaluate_text());
            tvPlace.setText(travel.getTabs().get(0).getRegion_name());
            tvAddress.setText(travel.getTabs().get(0).getAddress());

            try {
                if (travel.getTabs().get(0).getComment_count().endsWith(".0")) {
                    tvCommentCount.setText(String.valueOf((int) Double.parseDouble(travel.getTabs().get(0).getComment_count())));
                } else {
                    tvCommentCount.setText(travel.getTabs().get(0).getComment_count());
                }
            } catch (Exception e) {
                e.printStackTrace();
                tvCommentCount.setText("");
            }


            layoutOpen.setVisibility(View.VISIBLE);
            tvOpenDate.setText(travel.getTabs().get(1).getOpen_week());
            tvOpenState.setText("("+travel.getTabs().get(1).getType_open()+")");


            try {
                tvOpenState.setTextColor(Color.parseColor(travel.getTabs().get(1).getTypeOpenColor()));
                viewStatus.setBackgroundColor(Color.parseColor(travel.getTabs().get(1).getTypeOpenColor()));
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
                if (travel.getTabs().get(1).getRange_time().isEmpty()) {
                    //    viewTime.setVisibility(View.GONE);
                    tvOpenTime.setVisibility(View.GONE);
                    tvOpenTime2.setVisibility(View.GONE);
                } else {
                    //    viewTime.setVisibility(View.VISIBLE);
                    if(travel.getTabs().get(1).getRange_time().contains("và")){
                        String[] strings = travel.getTabs().get(1).getRange_time().split("và");
                        tvOpenTime.setText(strings[0]);
                        tvOpenTime2.setText(strings[1]);
                        tvOpenTime.setVisibility(View.VISIBLE);
                        tvOpenTime2.setVisibility(View.VISIBLE);
                    }else {
                        tvOpenTime.setText(travel.getTabs().get(1).getRange_time());
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

//            }

            if(travel.getTabs().get(1).isHas_location()) {
                if (travel.getTabs().get(1).getDistance() != null && !"".equals(travel.getTabs().get(1).getDistance()) && Double.parseDouble(travel.getTabs().get(1).getDistance()) < 1000) {
                    tvDistance.setText(travel.getTabs().get(1).getDistance_text()+" " + travel.getTabs().get(1).getDistance() + " m");
                } else if (travel.getTabs().get(1).getDistance() != null && !"".equals(travel.getTabs().get(1).getDistance())) {
                    double finalValue = Math.round(Double.parseDouble(travel.getTabs().get(1).getDistance()) / 1000 * 10.0) / 10.0;
                    tvDistance.setText(travel.getTabs().get(1).getDistance_text()+" " + finalValue + " km");
                }
            }else {
                tvDistance.setText("Không xác định");
            }
        }
    }


}
