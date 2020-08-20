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
import com.namviet.vtvtravel.listener.ScheduleSelectListener;
import com.namviet.vtvtravel.model.Schedule;

import java.util.ArrayList;

public class ListSuggestAdapter extends RecyclerView.Adapter<ListSuggestAdapter.ItemSuggestViewHolder> {
    private Context mContext;
    private ScheduleSelectListener scheduleSelectListener;
    private ArrayList<Schedule> schedulesList;

    public void setScheduleSelectListener(ScheduleSelectListener scheduleSelectListener) {
        this.scheduleSelectListener = scheduleSelectListener;
    }

    public ListSuggestAdapter(Context mContext, ArrayList<Schedule> schedulesList) {
        this.mContext = mContext;
        this.schedulesList = schedulesList;
    }

    @NonNull
    @Override
    public ItemSuggestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_suggest, parent, false);
        return new ItemSuggestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSuggestViewHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return schedulesList == null ? 0 : schedulesList.size();
    }

    public class ItemSuggestViewHolder extends BaseHolder {
        private ImageView ivPhoto;
        private TextView tvTitle;
        private TextView tvDuration;
        private TextView tvDate;

        public ItemSuggestViewHolder(View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvDate = itemView.findViewById(R.id.tvDate);


        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Schedule schedule = schedulesList.get(position);
            setImageUrl(schedule.getLogo_url(), ivPhoto);
            tvTitle.setText(schedule.getName());
            tvDuration.setText(schedule.getDuration_label());
            tvDate.setText(dateDuration(schedule.getDeparture_dates()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != scheduleSelectListener) {
                        scheduleSelectListener.onScheduleSelect(schedule);
                    }
                }
            });
        }
    }

    private String dateDuration(ArrayList<String> arr) {
        String date = null;
        if (null != arr) {
            if (arr.size() > 0 && arr.get(0).length() > 5) {
                date = arr.get(0).substring(0, 5);
            }
            for (int i = 1; i < arr.size(); i++) {
                if (arr.get(i).length() > 5) {
                    date = date + "; " + arr.get(i).substring(0, 5);
                }
            }
            return date;
        } else {
            return null;
        }

    }
}
