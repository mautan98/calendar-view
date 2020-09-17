package com.namviet.vtvtravel.adapter.f2biglocation.sub;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.DetailBigLocationAdapter;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

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
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvRate;
        private TextView tvRateText;
        private TextView tvCommentCount;
        private TextView tvAddress;
        private TextView tvDistance;
        private TextView tvType;
        private TextView tvLocationName;
        private TextView tvOpenDate;
        private TextView tvStatus;
        private TextView tvOpenTime;
        private View viewTime;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
            viewTime = itemView.findViewById(R.id.viewTime);
            tvName = itemView.findViewById(R.id.tvName);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvType = itemView.findViewById(R.id.tvType);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            tvRateText = itemView.findViewById(R.id.tvRateText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SmallLocationActivity.startScreenDetail((Activity)context, SmallLocationActivity.OpenType.DETAIL, items.get(position).getDetail_link());
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;

            Travel travel = items.get(position);

            Glide.with(context).load(travel.getLogo_url()).into(imgAvatar);
            tvName.setText(travel.getName());
            tvRate.setText(travel.getEvaluate());
            tvRateText.setText(travel.getEvaluate_text());
            tvCommentCount.setText(travel.getComment_count());
            tvAddress.setText(travel.getAddress());
            tvLocationName.setText(travel.getRegion_name());

            tvType.setText(travel.getType());

            try {
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
            } catch (Exception e) {
                e.printStackTrace();
            }


            tvOpenDate.setText(travel.getOpen_week());
            tvStatus.setText(travel.getType_open());


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
    }


    public interface ClickItem {
        void onClickItem(Travel travel);
    }


}
