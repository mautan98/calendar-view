package com.namviet.vtvtravel.adapter.travelnews;

import android.content.Context;
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
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;

import java.util.List;

public class NearByInTravelDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<Travel> travels;

    public NearByInTravelDetailAdapter(Context context, List<Travel> travels, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.travels = travels;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_experience, parent, false);
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
            return travels.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutForRestaurant;
        private LinearLayout linearPriceType;

        private ImageView imgAvatar;
        private ImageView imgType;
        private TextView tvName;
        private TextView tvRate;
        private TextView tvDistance;
        private TextView tvLocationName;
        private TextView tvCommentCount;
        private TextView tvAddress;
        private TextView tvType;
        private TextView tvStatus;
        private TextView tvOpenTime;
        private TextView tvOpenDate;
        private TextView tvPriceRange;
        private TextView tvTime;
        private TextView tvViewCount;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            tvPriceRange = itemView.findViewById(R.id.tvPriceRange);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            layoutForRestaurant = itemView.findViewById(R.id.layoutForRestaurant);
            linearPriceType = itemView.findViewById(R.id.linearPriceType);
            imgType = itemView.findViewById(R.id.imgType);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvType = itemView.findViewById(R.id.tvType);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(travels.get(position));
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            Travel travel = travels.get(position);

            tvName.setText(travel.getName());
            tvLocationName.setText(travel.getRegion_name());
            Glide.with(context).load(travel.getLogo_url()).into(imgAvatar);
            Glide.with(context).load(travel.getUrl_icon()).into(imgType);
            tvRate.setText(travel.getStandard_rate());
            tvCommentCount.setText(travel.getComment_count());
            tvAddress.setText(travel.getAddress());
            tvType.setText(travel.getCollection().getName());


            if (travel.getDistance() != null && !"".equals(travel.getDistance()) && Double.parseDouble(travel.getDistance()) < 1000) {
                tvDistance.setText("Cách bạn " + travel.getDistance() + " m");
            } else if (travel.getDistance() != null && !"".equals(travel.getDistance())) {
                double finalValue = Math.round(Double.parseDouble(travel.getDistance()) / 1000 * 10.0) / 10.0;
                tvDistance.setText("Cách bạn " + finalValue + " km");
            }

            if (Constants.TypeDestination.RESTAURANTS.equals(travel.getCollection().getContent_type()) || Constants.TypeDestination.HOTELS.equals(travel.getCollection().getContent_type())) {
                linearPriceType.setVisibility(View.VISIBLE);
                layoutForRestaurant.setVisibility(View.GONE);
                tvStatus.setVisibility(View.GONE);
                tvOpenDate.setVisibility(View.GONE);
                tvOpenTime.setVisibility(View.GONE);
                tvPriceRange.setVisibility(View.VISIBLE);
                tvPriceRange.setText(travel.getPrice_from() + " - " + travel.getPrice_to());
            } else {
                tvPriceRange.setVisibility(View.GONE);
                linearPriceType.setVisibility(View.GONE);
                layoutForRestaurant.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.VISIBLE);
                tvOpenDate.setVisibility(View.VISIBLE);
                tvOpenTime.setVisibility(View.VISIBLE);
                tvOpenTime.setText(travel.getRange_time());
                tvOpenDate.setText(travel.getOpen_week());
                tvStatus.setText(travel.getType_open());
            }
        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);
    }


}
