package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.GroupSchedule;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.ArrayList;

public class GroupScheduleAdapter extends RecyclerView.Adapter<GroupScheduleAdapter.GroupScheduleHolder> {


    private MainActivity mContext;
    private ArrayList<GroupSchedule> groupSchedules;
    private boolean detailSchedule;

    public GroupScheduleAdapter(MainActivity mContext, ArrayList<GroupSchedule> groupSchedules) {
        this.mContext = mContext;
        this.groupSchedules = groupSchedules;
    }

    public GroupScheduleAdapter(MainActivity mContext, ArrayList<GroupSchedule> groupSchedules, boolean detailSchedule) {
        this.mContext = mContext;
        this.groupSchedules = groupSchedules;
        this.detailSchedule = detailSchedule;
    }

    @NonNull
    @Override
    public GroupScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_schedule, parent, false);
        return new GroupScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupScheduleHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return groupSchedules.size();
    }

    public class GroupScheduleHolder extends BaseHolder {
        private TextView tvTitle;
        private RecyclerView rvItem;

        public GroupScheduleHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rvItem = itemView.findViewById(R.id.rvItem);
            rvItem.setLayoutManager(new LinearLayoutManager(mContext));
            rvItem.setNestedScrollingEnabled(false);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            GroupSchedule groupSchedule = groupSchedules.get(position);
            tvTitle.setText(groupSchedule.getTitle());
            SchedulePreviewAdapter schedulePreviewAdapter = new SchedulePreviewAdapter(mContext, groupSchedule.getContent(), detailSchedule);
            rvItem.setAdapter(schedulePreviewAdapter);

        }
    }

    public ArrayList<GroupSchedule> getGroupSchedules() {
        return groupSchedules;
    }
}
