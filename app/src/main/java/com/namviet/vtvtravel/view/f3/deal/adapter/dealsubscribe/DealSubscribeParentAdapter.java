package com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.DealFollow;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.Item;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class DealSubscribeParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ArrayList<DealFollow> dealFollows;

    public DealSubscribeParentAdapter(Context context, ArrayList<DealFollow> dealFollows) {
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
            return dealFollows.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnlPrice;
        private RecyclerView rclContent;
        private ImageView btnShowHide;



        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvTotalHoldTime;
        private TextView tvRank;
        private TextView tvStatus;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            btnShowHide = itemView.findViewById(R.id.btnHideShow);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvTotalHoldTime = itemView.findViewById(R.id.tvTotalHoldTime);
            tvRank = itemView.findViewById(R.id.tvRank);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }

        public void bindItem(int position) {
            rclContent.setAdapter(new DealSubscribeChildAdapter(context, dealFollows.get(position).getListDealChild()));
            btnShowHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            tvName.setText(dealFollows.get(position).getName());
            tvRank.setText(dealFollows.get(position).getRanking());
            tvStatus.setText(dealFollows.get(position).getIsProcessing().equals("1")?"Đang diễn ra": (dealFollows.get(position).getIsProcessing().equals("2")?"Sắp diễn ra": "Hết thời gian"));
            tvTotalHoldTime.setText(dealFollows.get(position).getTotalHoldTime());

        }
    }
}
