package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.ScheduleSelectListener;
import com.namviet.vtvtravel.model.Schedule;

import java.util.List;

public class TourCreatedAdapter extends RecyclerView.Adapter<TourCreatedAdapter.TourCreatedHolder> {
    private Context mContext;
    private List<Schedule> scheduleList;
    private ScheduleSelectListener scheduleSelectListener;

    public void setScheduleSelectListener(ScheduleSelectListener scheduleSelectListener) {
        this.scheduleSelectListener = scheduleSelectListener;
    }

    public TourCreatedAdapter(Context mContext, List<Schedule> scheduleList) {
        this.mContext = mContext;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public TourCreatedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_food_eat, parent, false);
        return new TourCreatedHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TourCreatedHolder holder, int position) {
        holder.bindItem(position);
    }


    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class TourCreatedHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvTitle;
        private TextView tvAddress;

        public TourCreatedHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Schedule travel = scheduleList.get(position);
            setImageUrl(travel.getLogo_url(), ivItem);
            tvTitle.setText(travel.getName());
            tvAddress.setVisibility(View.GONE);
            tvTitle.setMaxLines(2);
            tvTitle.setEllipsize(TextUtils.TruncateAt.END);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != scheduleSelectListener) {
                        scheduleSelectListener.onScheduleSelect(travel);
                    }

                }
            });
        }
    }
}
