package com.namviet.vtvtravel.adapter.f2search;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private List<Travel> travelList;

    public TrendingAdapter(List<Travel> travelList, Context context) {
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_search_trending, parent, false);
            return new HeaderViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                Travel travel = travelList.get(position);
                ((HeaderViewHolder) holder).bindItem(position, travel);
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
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvDistance;
        private ImageView imgAvatar;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvName = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        SmallLocationActivity.startScreenDetail(context, SmallLocationActivity.OpenType.DETAIL, travelList.get(position).getDetail_link());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        public void bindItem(int position, Travel travel) {
            this.position = position;

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
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            tvName.setText(travel.getName());
            tvAddress.setText(travel.getAddress());
            Glide.with(context).load(travel.getUrl_icon()).into(imgAvatar);
        }
    }


}
