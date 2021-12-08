package com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe;

import android.content.Context;
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

import java.util.ArrayList;

public class DealSubscribeChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ArrayList<DealFollow> dealFollows;

    public DealSubscribeChildAdapter(Context context, ArrayList<DealFollow> dealFollows) {
        this.context = context;
        this.dealFollows = dealFollows;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribe_deal_child, parent, false);
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
            return dealFollows.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvTotalHoldTime;
        private TextView tvRank;
        private TextView tvStatus;
        private LinearLayout lnlPrice;
        private LinearLayout layoutChecked;
        private ImageView imgClock;
        private ImageView imgWin;
        private View viewCoverVertical;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgWin = itemView.findViewById(R.id.imgWin);
            tvName = itemView.findViewById(R.id.tvName);
            tvTotalHoldTime = itemView.findViewById(R.id.tvTotalHoldTime);
            tvRank = itemView.findViewById(R.id.tvRank);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgClock = itemView.findViewById(R.id.imgClock);
            layoutChecked = itemView.findViewById(R.id.layoutChecked);
            viewCoverVertical = itemView.findViewById(R.id.viewCoverVertical);

        }

        public void bindItem(int position) {
            tvName.setText(dealFollows.get(position).getName());
            tvRank.setText(dealFollows.get(position).getRanking());
            tvTotalHoldTime.setText(dealFollows.get(position).getTotalHoldTime());
            Glide.with(context).load(dealFollows.get(position).getAvatarUri()).into(imgAvatar);
            try {
                if(dealFollows.get(position).getIsProcessing().equals("1")){
                    Glide.with(context).load(R.drawable.ic_clock).into(imgClock);
                }else {
                    Glide.with(context).load(R.drawable.ic_clock_2).into(imgClock);
                }
            } catch (Exception e) {
                Glide.with(context).load(R.drawable.ic_clock_2).into(imgAvatar);
                e.printStackTrace();
            }

            try {
                tvStatus.setText(dealFollows.get(position).getIsProcessing().equals(IsProcessingType.DANG_DIEN_RA_TYPE)?"Đang diễn ra": (dealFollows.get(position).getIsProcessing().equals(IsProcessingType.SAP_DIEN_RA_TYPE)?"Sắp diễn ra": "Đã hết hạn"));
            } catch (Exception e) {
                tvStatus.setText("Đã hết hạn");
            }


            try {
                if(dealFollows.get(position).getIsWin().equals("1")){
                    imgWin.setVisibility(View.VISIBLE);
                }else {
                    imgWin.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                imgWin.setVisibility(View.GONE);
            }


            try {
                long time = Integer.parseInt(dealFollows.get(position).getTotalHoldTime())/1000;

                String days = (int)(time / 86400) + " NGÀY ";
                String hours = String.valueOf((int)((time % 86400) / 3600));
                String minutes = String.valueOf((int)((time % 3600) / 60));
                String seconds = String.valueOf((int)((time % 3600) % 60));

                tvTotalHoldTime.setText(days + hours+" : "+minutes+" : "+seconds);

                if(time > 0){
                    layoutChecked.setVisibility(View.VISIBLE);
                }else {
                    layoutChecked.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                tvTotalHoldTime.setText("0 NGÀY");
                layoutChecked.setVisibility(View.GONE);
            }

            if(position == dealFollows.size() - 1){
                viewCoverVertical.setVisibility(View.VISIBLE);
            }else {
                viewCoverVertical.setVisibility(View.GONE);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        DetailDealActivity.startScreen(context, dealFollows.get(position).getId(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
