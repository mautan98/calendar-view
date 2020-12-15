package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.TravelSelectListener;

import java.text.DecimalFormat;
import java.util.List;

public class SearchNearResultAdapter extends RecyclerView.Adapter<SearchNearResultAdapter.NearResultHolder> {
    private Context mContext;
    private List<Travel> travelList;

    private TravelSelectListener travelSelectListener;

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }

    public SearchNearResultAdapter(Context mContext, List<Travel> travelList) {
        this.mContext = mContext;
        this.travelList = travelList;
    }

    @NonNull
    @Override
    public NearResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_near_result, parent, false);
        return new NearResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearResultHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return travelList == null ? 0 : travelList.size();
    }

    public class NearResultHolder extends BaseHolder {
        private ImageView ivPhoto;
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvDistance;
        private TextView tvDirection;

        public NearResultHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvDirection = itemView.findViewById(R.id.tvDirection);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Travel travel = travelList.get(position);
            setImageUrl(travel.getLogo_url(), ivPhoto);
            tvTitle.setText(travel.getName());
            tvAddress.setText(travel.getAddress());
            if (travel.getDistance() > 0) {
                if (travel.getDistance() < 1000) {
                    tvDistance.setText(travel.getDistance() + " m");
                } else {
                    DecimalFormat df = new DecimalFormat("#.##");
                    tvDistance.setText(df.format(travel.getDistance() * 0.001) + " km");
                }
            } else {
                tvDistance.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != travelSelectListener) {
                        travelSelectListener.onSelectTravel(travel);
                    }
                }
            });
        }
    }
}
