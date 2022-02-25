package com.namviet.vtvtravel.adapter.smalllocation;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;

import com.namviet.vtvtravel.model.f2smalllocation.Travel;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

import java.util.List;

public class SubNearbyExperienceInSmallLocationDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;

    private List<Travel> items;

    public SubNearbyExperienceInSmallLocationDetailAdapter(List<Travel> items, Context context, ClickItem clickItem) {
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.aaa, parent, false);
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
        private ImageView imgType;
        private ImageView imgOpenState;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvDistance;
        private TextView tvAddress;
        private TextView tvOpenDate;
        private TextView tvOpenTime;
        private TextView tvOpenState;
        private TextView tvType;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
            tvOpenState = itemView.findViewById(R.id.tvOpenState);
            tvType = itemView.findViewById(R.id.tv_type);
            imgType = itemView.findViewById(R.id.img_type);
            imgOpenState = itemView.findViewById(R.id.img_open_state);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        SmallLocationActivity.startScreenDetail(context, SmallLocationActivity.OpenType.DETAIL, items.get(position).getDetail_link());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;

            Travel travel = items.get(position);

            Glide.with(context).load(travel.getLogo_url()).into(imgAvatar);
            Glide.with(context).load(travel.getUrl_icon()).into(imgType);
            tvName.setText(travel.getName());
            tvDescription.setText(travel.getShort_description());
            tvAddress.setText(travel.getAddress());
            tvOpenDate.setText(travel.getOpen_week());
            tvType.setText(travel.getCollection().getName());

            try {
                if(travel.isHas_location()) {
                    if (travel.getDistance() != null && !"".equals(travel.getDistance()) && Double.parseDouble(travel.getDistance()) < 1000) {
                        tvDistance.setText(travel.getDistance_text()+" " + travel.getDistance() + " m");
                    } else if (travel.getDistance() != null && !"".equals(travel.getDistance())) {
                        double finalValue = Math.round(Double.parseDouble(travel.getDistance()) / 1000 * 10.0) / 10.0;
                        tvDistance.setText(travel.getDistance_text()+" " + finalValue + " km");
                    }
                }else {
                    tvDistance.setText("Không xác định");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


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
                if(travel.getRange_time().isEmpty()){

                    tvOpenTime.setVisibility(View.GONE);
                }else {

                    tvOpenTime.setText(travel.getRange_time());
                    tvOpenTime.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {

                tvOpenTime.setVisibility(View.GONE);
                e.printStackTrace();
            }

        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);
    }


}
