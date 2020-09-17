package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.newhome.ItemAppExperienceResponse;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

import java.util.List;

public class SubSuggestionLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private List<Travel> travelList;

    public SubSuggestionLocationAdapter(Context context, List<Travel> travelList) {
        this.context = context;
        this.travelList = travelList;
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
            return travelList.size();
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

        private TextView tvPriceRange;
        private TextView tvOpenDate;
        private TextView tvOpenTime;
        private TextView tvStatus;

        private LinearLayout linearOpenType;
        private LinearLayout linearPriceType;

        private int position;
        private View viewTime;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.imgBanner);
            viewTime = itemView.findViewById(R.id.viewTime);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SmallLocationActivity.startScreenDetail((Activity) context, SmallLocationActivity.OpenType.DETAIL, travelList.get(position).getDetail_link());
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            Travel travel = travelList.get(position);
            Glide.with(context).load(travel.getLogo_url()).into(imgBanner);
            tvName.setText(travel.getName());
            tvRate.setText(travel.getEvaluate());
            tvRateText.setText(travel.getEvaluate_text());
            tvPlace.setText(travel.getRegion_name());

            try {
                if(travel.getComment_count().endsWith(".0")){
                    tvCommentCount.setText(String.valueOf((int)Double.parseDouble(travel.getComment_count())));
                }else {
                    tvCommentCount.setText(travel.getComment_count());
                }
            } catch (Exception e) {
                e.printStackTrace();
                tvCommentCount.setText("");
            }

            if (Constants.TypeDestination.RESTAURANTS.equals(travel.getContent_type()) || Constants.TypeDestination.HOTELS.equals(travel.getContent_type())) {
                linearPriceType.setVisibility(View.VISIBLE);
                linearOpenType.setVisibility(View.GONE);

                try {
                    String priceFrom = "";
                    String priceTo = "";

                    if(travel.getPrice_from().endsWith(".0")){
                        priceFrom = travel.getPrice_from().substring(0, travel.getPrice_from().length() - 2);
                    }

                    if(travel.getPrice_to().endsWith(".0")){
                        priceTo = travel.getPrice_to().substring(0, travel.getPrice_to().length() - 2);
                    }

                    tvPriceRange.setText(priceFrom + " đ" + " - " + priceTo + " đ");
                } catch (Exception e) {
                    e.printStackTrace();
                    tvPriceRange.setText(travel.getPrice_from() + " đ" + " - " + travel.getPrice_to() + " đ");
                }
            } else {
                linearPriceType.setVisibility(View.GONE);
                linearOpenType.setVisibility(View.VISIBLE);
                tvOpenDate.setText(travel.getOpen_week());


                tvStatus.setText(travel.getType_open());
                if ("Đang đóng".equals(travel.getType_open())) {
                    tvStatus.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    tvStatus.setTextColor(Color.parseColor("#0FB403"));
                }


                try {
                    if(travel.getRange_time().isEmpty()){
                        viewTime.setVisibility(View.GONE);
                        tvOpenTime.setVisibility(View.GONE);
                    }else {
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

            if(travel.isHas_location()) {
                if (travel.getDistance() != null && !"".equals(travel.getDistance()) && Double.parseDouble(travel.getDistance()) < 1000) {
                    tvDistance.setText("Cách bạn " + travel.getDistance() + " m");
                } else if (travel.getDistance() != null && !"".equals(travel.getDistance())) {
                    double finalValue = Math.round(Double.parseDouble(travel.getDistance()) / 1000 * 10.0) / 10.0;
                    tvDistance.setText("Cách bạn " + finalValue + " km");
                }
            }else {
                tvDistance.setText("Không xác định");
            }
        }
    }


}
