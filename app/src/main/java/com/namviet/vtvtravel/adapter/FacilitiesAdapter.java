package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.Facility;

import java.util.List;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilityHolder> {
    private Context mContext;
    private List<Facility> facilityList;

    public FacilitiesAdapter(Context mContext, List<Facility> facilityList) {
        this.mContext = mContext;
        this.facilityList = facilityList;
    }

    @NonNull
    @Override
    public FacilityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_facility, parent, false);
        return new FacilityHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return facilityList == null ? 0 : facilityList.size();
    }

    public class FacilityHolder extends BaseHolder {
        private ImageView ivIcon;
        private TextView tvTitle;

        public FacilityHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            Facility facility = facilityList.get(position);
            setImageUrl(facility.getIcon_url(), ivIcon);
            tvTitle.setText(facility.getName());
        }
    }
}
