package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.TravelSelectListener;

import java.util.List;

public class WhereStayAdapter extends RecyclerView.Adapter<WhereStayAdapter.WhereStayHolder> {


    private Context mContext;
    private List<Travel> travelList;
    private TravelSelectListener travelSelectListener;

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }
    public WhereStayAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public WhereStayAdapter(Context mContext, List<Travel> travelList) {
        this.mContext = mContext;
        this.travelList = travelList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 5 == 0) {
            return R.layout.item_where_stay_big;
        } else {
            return R.layout.item_where_stay;
        }
    }

    @NonNull
    @Override
    public WhereStayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        return new WhereStayHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WhereStayHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return travelList == null ? 0 : travelList.size();
    }

    public class WhereStayHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvTitle;
        private TextView tvAddress;
        private RatingBar whereStayRatingBar;

        public WhereStayHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            whereStayRatingBar = itemView.findViewById(R.id.rating_bar_where_stay);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Travel travel = travelList.get(position);
            setImageUrl(travel.getLogo_url(), ivItem);
            tvTitle.setText(travel.getName());
            tvAddress.setText(travel.getAddress());
            whereStayRatingBar.setRating(travel.getStandard_rate());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(null!= travelSelectListener){
                        travelSelectListener.onSelectTravel(travel);
                    }

                }
            });
        }
    }
}
