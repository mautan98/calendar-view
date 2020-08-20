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
import com.namviet.vtvtravel.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleTravelAdapter extends RecyclerView.Adapter<ScheduleTravelAdapter.ItemScheduleHolder> {
    private Context mContext;
    private List<Schedule> scheduleList;
    private ArrayList<Schedule> listSelected;

    public ScheduleTravelAdapter(Context mContext, List<Schedule> scheduleList) {
        this.mContext = mContext;
        this.scheduleList = scheduleList;
        listSelected = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_schedule_travel, parent, false);
        return new ItemScheduleHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemScheduleHolder holder, int position) {
        holder.bindItem(position);
    }


    @Override
    public int getItemCount() {
        return scheduleList == null ? 0 : scheduleList.size();
    }

    public class ItemScheduleHolder extends BaseHolder {
        private ImageView ivSelect;
        private ImageView ivPhoto;
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvDistance;

        public ItemScheduleHolder(View itemView) {
            super(itemView);
            ivSelect = itemView.findViewById(R.id.ivSelect);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
        }

        @Override
        public void bindItem(final int position) {
            super.bindItem(position);
            final Schedule schedule = scheduleList.get(position);
            setImageUrl(schedule.getLogo_url(), ivPhoto);
            tvTitle.setText(schedule.getName());
            tvAddress.setText(schedule.getAddress());
            if (schedule.getDistance() > 0) {
                if (schedule.getDistance() > 1000) {
                    tvDistance.setText("" + String.format("%.2f", schedule.getDistance() * 0.001) + " km");
                } else {
                    int distance = (int) schedule.getDistance();
                    tvDistance.setText(distance + " m");
                }
            }else {
                tvDistance.setVisibility(View.GONE);
            }
            if (schedule.isSelected()) {
                ivSelect.setBackgroundResource(R.drawable.bg_cirle_checked);
            } else {
                ivSelect.setBackgroundResource(R.drawable.bg_cirle_check);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (schedule.isSelected()) {
                        scheduleList.get(position).setSelected(false);
                        listSelected.remove(schedule);
                    } else {
                        scheduleList.get(position).setSelected(true);
                        listSelected.add(schedule);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    public ArrayList<Schedule> getListSelected() {
        return listSelected;
    }
}
