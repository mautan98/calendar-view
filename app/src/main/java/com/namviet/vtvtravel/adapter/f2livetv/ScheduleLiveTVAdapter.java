package com.namviet.vtvtravel.adapter.f2livetv;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;

public class ScheduleLiveTVAdapter extends RecyclerView.Adapter<ScheduleLiveTVAdapter.MyViewHolder> {
    private Context context;
    private List<LiveTvResponse.Channel.Schedule> scheduleList;
    private int positionRunning = 1000;

    public ScheduleLiveTVAdapter(Context context, List<LiveTvResponse.Channel.Schedule> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    public void highLight(int position){
        positionRunning = position;
    }

    @NonNull
    @Override
    public ScheduleLiveTVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_program_livetv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleLiveTVAdapter.MyViewHolder holder, int position) {
        try {
            holder.onBind(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return scheduleList == null ? 0 : scheduleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime, txtTitle, txtContent;
        private View layoutRoot;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            layoutRoot = itemView.findViewById(R.id.layoutRoot);
        }

        public void onBind(int position){
            txtTitle.setText(scheduleList.get(position).getName());
            if(scheduleList.get(position).getTopic() == null || "".equals(scheduleList.get(position).getTopic())){
                txtContent.setVisibility(View.GONE);
            }else{
                txtContent.setText(scheduleList.get(position).getTopic());
            }

            txtTime.setText(DateUtltils.timeToString2(scheduleList.get(position).getStart_time()));

            if(position == positionRunning){
                layoutRoot.setBackgroundColor(Color.parseColor("#CCE9E8"));
            }else {
                layoutRoot.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
    }

}
