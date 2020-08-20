package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.TravelSelectListener;

import java.util.List;

public class SearchTrendAdapter extends RecyclerView.Adapter<SearchTrendAdapter.SearchSuggestHolder> {
    private Context mContext;
    private List<Travel> trendList;

    private TravelSelectListener travelSelectListener;

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }

    public SearchTrendAdapter(Context mContext, List<Travel> trendList) {
        this.mContext = mContext;
        this.trendList = trendList;
    }

    @NonNull
    @Override
    public SearchSuggestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new SearchSuggestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return trendList == null ? 0 : trendList.size();
    }

    public class SearchSuggestHolder extends BaseHolder {
        private TextView tvTitle;
        private ImageView mSearchIconImv;
        private TextView mSearchAddressTv;
        private TextView tvDistance;

        public SearchSuggestHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            mSearchIconImv = itemView.findViewById(R.id.imv_search_icon);
            mSearchAddressTv = itemView.findViewById(R.id.tv_search_address);
            tvDistance = itemView.findViewById(R.id.tvDistance);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Travel travel = trendList.get(position);
            tvTitle.setText(travel.getName());
            setImageUrl(travel.getUrl_icon(), mSearchIconImv);
            mSearchAddressTv.setText(travel.getAddress());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != travelSelectListener) {
                        travelSelectListener.onSelectTravel(travel);
                    }
                }
            });
            tvDistance.setVisibility(View.VISIBLE);
            if (travel.getDistance() > 0) {
                if (travel.getDistance() > 1000) {
                    tvDistance.setText("Cách " + String.format("%.2f", travel.getDistance() * 0.001) + " km");
                } else {
                    int distance = (int) travel.getDistance();
                    tvDistance.setText("Cách " + distance + " m");
                }
            } else {
                tvDistance.setVisibility(View.GONE);
            }
        }

        public void setImageUrl(String ulrCs, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(getImage());
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
        }

        private int getImage() {
            return mContext.getResources().getIdentifier("vt_arrow_schedule_black", "drawable", mContext.getPackageName());
        }
    }
}
