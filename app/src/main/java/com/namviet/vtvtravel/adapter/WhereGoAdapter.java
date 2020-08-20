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

public class WhereGoAdapter extends RecyclerView.Adapter<WhereGoAdapter.WhereGoHolder> {
    private Context mContext;
    private List<Travel> items;

    private TravelSelectListener travelSelectListener;

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }

    public WhereGoAdapter(Context mContext, List<Travel> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public WhereGoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_where_go, parent, false);
        return new WhereGoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WhereGoHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class WhereGoHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvTitle;
        private TextView tvAddress;

        public WhereGoHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Travel travel = items.get(position);
            if (null != travel.getLogo_url()) {
                setImageUrl(travel.getLogo_url(), ivItem);
            } else {
                setImageUrl(travel.getBanner_url(), ivItem);
            }
            tvTitle.setText(travel.getName());
            tvAddress.setText(travel.getAddress());
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

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);

        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);

    }
}
