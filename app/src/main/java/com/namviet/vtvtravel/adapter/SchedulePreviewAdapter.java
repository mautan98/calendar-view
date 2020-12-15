package com.namviet.vtvtravel.adapter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.Schedule;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.List;

public class SchedulePreviewAdapter extends RecyclerView.Adapter<SchedulePreviewAdapter.SchedulePreviewHolder> {
    private MainActivity mContext;
    private List<Schedule> scheduleList;
    private boolean detailSchedule;

    public SchedulePreviewAdapter(MainActivity mContext, List<Schedule> scheduleList) {
        this.mContext = mContext;
        this.scheduleList = scheduleList;
    }

    public SchedulePreviewAdapter(MainActivity mContext, List<Schedule> scheduleList, boolean detailSchedule) {
        this.mContext = mContext;
        this.scheduleList = scheduleList;
        this.detailSchedule = detailSchedule;
    }

    @NonNull
    @Override
    public SchedulePreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_schedule_preview_travel, parent, false);
        return new SchedulePreviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulePreviewHolder holder, int position) {
        holder.bindItem(position);
    }


    @Override
    public int getItemCount() {
        return scheduleList == null ? 0 : scheduleList.size();
    }

    public class SchedulePreviewHolder extends BaseHolder {
        private ImageView ivRemove;
        private ImageView ivPhoto;
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvDistance;

        public SchedulePreviewHolder(View itemView) {
            super(itemView);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            if (detailSchedule) {
                ivRemove.setVisibility(View.INVISIBLE);
            }
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    String url = WSConfig.HOST + schedule.getContent_type() + "/" + schedule.getId();
                    Travel travel = new Travel();
                    travel.setDetail_link(url);
                    travel.setId(schedule.getId());
                    travel.setContent_type(schedule.getContent_type());
                    bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
                    mContext.setBundle(bundle);
                    mContext.switchFragment(SlideMenu.MenuType.DETAIL_EAT_SCREEN);

//                    Travel travel = new Travel();
////                    travel.setDetail_link(mSlideshowChats.get(position).getDetail_link());
//                    String url = WSConfig.HOST + WSConfig.Api.GET_PLACE + "/" + schedule.getId();
//                    travel.setDetail_link(url);
//                    travelSelectListener.onSelectTravel(travel);
                }
            });

            if (!detailSchedule) {
                ivRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        scheduleList.remove(schedule);
                        notifyDataSetChanged();
                    }
                });
            }

        }
    }


}
