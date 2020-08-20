package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.ScheduleSelectListener;
import com.namviet.vtvtravel.model.Schedule;

import java.util.List;

public class ScheduleCreatedAdapter extends RecyclerView.Adapter<ScheduleCreatedAdapter.ItemMyListHolder> {
    private Context mContext;
    private List<Schedule> tourList;
    private ScheduleSelectListener scheduleSelectListener;

    public void setScheduleSelectListener(ScheduleSelectListener scheduleSelectListener) {
        this.scheduleSelectListener = scheduleSelectListener;
    }

    public ScheduleCreatedAdapter(Context mContext, List<Schedule> tourList) {
        this.mContext = mContext;
        this.tourList = tourList;
    }

    @NonNull
    @Override
    public ItemMyListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_schedule_created, parent, false);
        return new ItemMyListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMyListHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return tourList == null ? 0 : tourList.size();
    }

    public class ItemMyListHolder extends BaseHolder {
        private TextView tvTitle;

        public ItemMyListHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Schedule tour = tourList.get(position);
            tvTitle.setText("" + tour.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != scheduleSelectListener) {
                        scheduleSelectListener.onScheduleSelect(tour);
                    }
                }
            });
        }
    }
}
