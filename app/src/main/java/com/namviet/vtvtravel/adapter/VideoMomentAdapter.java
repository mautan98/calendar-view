package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.TravelSelectListener;

import java.util.List;

public class VideoMomentAdapter extends RecyclerView.Adapter<VideoMomentAdapter.VideoMomentHolder> {
    private Context mContext;
    private List<Travel> travelList;

    private TravelSelectListener travelSelectListener;

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }

    public VideoMomentAdapter(Context mContext, List<Travel> travelList) {
        this.mContext = mContext;
        this.travelList = travelList;
    }

    @NonNull
    @Override
    public VideoMomentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_moment, parent, false);
        return new VideoMomentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoMomentHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return travelList == null ? 0 : travelList.size();
    }


    public class VideoMomentHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvTitle;

        public VideoMomentHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Travel travel = travelList.get(position);
            setImageUrl(travel.getLogo_url(), ivItem);
            tvTitle.setText(travel.getName());
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
}
