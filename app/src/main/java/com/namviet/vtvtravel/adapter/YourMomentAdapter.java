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
import java.util.Random;

public class YourMomentAdapter extends RecyclerView.Adapter<YourMomentAdapter.YourMomentHolder> {
    private Context mContext;
    private Random mRandom = new Random();
    private List<Travel> travelList;
    private TravelSelectListener travelSelectListener;

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }

    public YourMomentAdapter(Context mContext, List<Travel> travelList) {
        this.mContext = mContext;
        this.travelList = travelList;
    }

    @NonNull
    @Override
    public YourMomentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_your_moment, parent, false);
        return new YourMomentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YourMomentHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return travelList == null ? 0 : travelList.size();
    }

    public class YourMomentHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvTitle;
        private TextView tvUser;

        public YourMomentHolder(View itemView) {
            super(itemView);
            // itemView.getLayoutParams().height = getRandomIntInRange(mContext.getResources().getDimensionPixelSize(R.dimen._230sdp), mContext.getResources().getDimensionPixelSize(R.dimen._180sdp));
            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvUser = itemView.findViewById(R.id.tvUser);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final Travel travel = travelList.get(position);
            setImageUrl(travel.getLogo_url(), ivItem);
            tvTitle.setText(travel.getName());
            tvUser.setText("By: " + travel.getAuthor());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(null!=travelSelectListener){
                       travelSelectListener.onSelectTravel(travel);
                   }

                }
            });
        }
    }

    protected int getRandomIntInRange(int max, int min) {
        return mRandom.nextInt(max - min) + min;
    }
}
