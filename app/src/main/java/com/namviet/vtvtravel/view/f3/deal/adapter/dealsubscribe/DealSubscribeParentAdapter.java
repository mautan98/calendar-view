package com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.constant.IsProcessingType;
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.DealFollow;
import com.namviet.vtvtravel.view.f3.deal.view.dealdetail.DetailDealActivity;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.Item;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class DealSubscribeParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ArrayList<DealFollow> dealFollows;
    private DataListener dataListener;

    public DealSubscribeParentAdapter(Context context, ArrayList<DealFollow> dealFollows, DataListener dataListener) {
        this.context = context;
        this.dealFollows = dealFollows;
        this.dataListener = dataListener;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribe_deal_parent, parent, false);
            return new HeaderViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            if(dealFollows.size()>0){
                dataListener.onDataChange(false);
            }else {
                dataListener.onDataChange(true);
            }
            return dealFollows.size();
        } catch (Exception e) {
            dataListener.onDataChange(true);
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnlPrice;
        private RecyclerView rclContent;
        private ImageView btnShowHide;



        private ImageView imgAvatar;
        private ImageView imgClock;
        private TextView tvName;
        private TextView tvTotalHoldTime;
        private TextView tvRank;
        private TextView tvStatus;
        private View viewVertical;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            btnShowHide = itemView.findViewById(R.id.btnHideShow);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgClock = itemView.findViewById(R.id.imgClock);
            tvName = itemView.findViewById(R.id.tvName);
            tvTotalHoldTime = itemView.findViewById(R.id.tvTotalHoldTime);
            tvRank = itemView.findViewById(R.id.tvRank);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            viewVertical = itemView.findViewById(R.id.viewVertical);
        }

        public void bindItem(int position) {
            rclContent.setAdapter(new DealSubscribeChildAdapter(context, dealFollows.get(position).getListDealChild()));
            btnShowHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dealFollows.get(position).isShowChild()){
                        rclContent.setVisibility(View.GONE);
                        viewVertical.setVisibility(View.GONE);
                        dealFollows.get(position).setShowChild(false);
                        Glide.with(context).load(R.drawable.ic_show_child).into(btnShowHide);
                    }else {
                        rclContent.setVisibility(View.VISIBLE);
                        viewVertical.setVisibility(View.VISIBLE);
                        dealFollows.get(position).setShowChild(true);
                        Glide.with(context).load(R.drawable.ic_hide_child).into(btnShowHide);
                    }
                }
            });
            tvName.setText(dealFollows.get(position).getName());
            tvRank.setText(dealFollows.get(position).getRanking());

//            try {
//                tvStatus.setText(dealFollows.get(position).getIsProcessing().equals(IsProcessingType.DANG_DIEN_RA_TYPE)?"Đang diễn ra": (dealFollows.get(position).getIsProcessing().equals(IsProcessingType.SAP_DIEN_RA_TYPE)?"Sắp diễn ra": "Đã kết thúc"));
//                tvStatus.setTextColor(dealFollows.get(position).getIsProcessing().equals(IsProcessingType.DANG_DIEN_RA_TYPE)? Color.parseColor("#25C400") : Color.parseColor("#C2C2C2"));
//            } catch (Exception e) {
//                tvStatus.setText("Đã kết thúc");
//                tvStatus.setTextColor(Color.parseColor("#C2C2C2"));
//            }


            try {
                if(dealFollows.get(position).getRewardStatus().equals("2")){
                    tvStatus.setText("Đang diễn ra");
                    tvStatus.setTextColor(Color.parseColor("#25C400"));
                    Glide.with(context).load(R.drawable.ic_clock).into(imgClock);
                }else if (dealFollows.get(position).getRewardStatus().equals("4")){
                    tvStatus.setText("Đã chiến thắng");
                    tvStatus.setTextColor(Color.parseColor("#C2C2C2"));
                    Glide.with(context).load(R.drawable.ic_clock_2).into(imgClock);
                }else if(dealFollows.get(position).getRewardStatus().equals("5")){
                    tvStatus.setText("Săn không thành công");
                    tvStatus.setTextColor(Color.parseColor("#C2C2C2"));
                    Glide.with(context).load(R.drawable.ic_clock_2).into(imgClock);
                }else {
                    tvStatus.setText("Đang diễn ra");
                    tvStatus.setTextColor(Color.parseColor("#25C400"));
                    Glide.with(context).load(R.drawable.ic_clock).into(imgClock);
                }
            } catch (Exception e) {
                tvStatus.setText("Đang diễn ra");
                tvStatus.setTextColor(Color.parseColor("#25C400"));
                Glide.with(context).load(R.drawable.ic_clock).into(imgClock);
            }

//            try {
//                if(dealFollows.get(position).getIsProcessing().equals(IsProcessingType.DANG_DIEN_RA_TYPE)){
//                    Glide.with(context).load(R.drawable.ic_clock).into(imgClock);
//                }else {
//                    Glide.with(context).load(R.drawable.ic_clock_2).into(imgClock);
//                }
//            } catch (Exception e) {
//                Glide.with(context).load(R.drawable.ic_clock_2).into(imgAvatar);
//                e.printStackTrace();
//            }


            try {
                long time = Integer.parseInt(dealFollows.get(position).getTotalHoldTime())/1000;

                String days = (int)(time / 86400) + " NGÀY ";
                String hours = String.valueOf((int)((time % 86400) / 3600));
                String minutes = String.valueOf((int)((time % 3600) / 60));
                String seconds = String.valueOf((int)((time % 3600) % 60));

                if(days.length() == 1){
                    days = "0"+days;
                }

                if(hours.length() == 1){
                    hours = "0"+hours;
                }

                if(minutes.length() == 1){
                    minutes = "0"+minutes;
                }

                if(seconds.length() == 1){
                    seconds = "0"+seconds;
                }


                tvTotalHoldTime.setText(days + hours+" : "+minutes+" : "+seconds);


            } catch (Exception e) {
                tvTotalHoldTime.setText("0 NGÀY");

            }


            try {
                Glide.with(context).load(dealFollows.get(position).getAvatarUri()).into(imgAvatar);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(dealFollows.get(position).isShowChild()){
                rclContent.setVisibility(View.VISIBLE);
                viewVertical.setVisibility(View.VISIBLE);
                Glide.with(context).load(R.drawable.ic_hide_child).into(btnShowHide);
            }else {
                rclContent.setVisibility(View.GONE);
                viewVertical.setVisibility(View.GONE);
                Glide.with(context).load(R.drawable.ic_show_child).into(btnShowHide);
            }



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        DetailDealActivity.startScreen(context, dealFollows.get(position).getCampaignId(), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    }

    public interface DataListener {
        void onDataChange(boolean isShowNoDataView);
    }
}
