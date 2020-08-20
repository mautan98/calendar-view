package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.PlaceSelectLisntener;

import java.util.List;

public class SuggestPlaceAdapter extends RecyclerView.Adapter<SuggestPlaceAdapter.SuggestPlaceHolder> {
    private Context mContext;
    private List<Travel> travelList;
    private PlaceSelectLisntener placeSelectLisntener;

    public void setPlaceSelectLisntener(PlaceSelectLisntener placeSelectLisntener) {
        this.placeSelectLisntener = placeSelectLisntener;
    }

    public SuggestPlaceAdapter(Context mContext, List<Travel> travelList) {
        this.mContext = mContext;
        this.travelList = travelList;
    }

    @NonNull
    @Override
    public SuggestPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_suggest_tab_intro_where_go, parent, false);
        return new SuggestPlaceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestPlaceHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return travelList == null ? 0 : travelList.size();
    }

    public class SuggestPlaceHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvTitle;
        private TextView tvAddress;

        public SuggestPlaceHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Travel travel = travelList.get(position);
            setImageUrl(travel.getLogo_url(), ivItem);
            tvAddress.setText(travel.getAddress());
            tvTitle.setText(travel.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != placeSelectLisntener) {
                        placeSelectLisntener.onSelect(travel);
                    }
                }
            });
        }
    }
}
