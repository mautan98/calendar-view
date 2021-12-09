package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f3.deal.Utils;
import com.namviet.vtvtravel.view.f3.deal.model.Rank;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.DealCampaignDetail;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.DealCampaignScore;

import java.util.List;

public class SubRankingItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    private Context context;
    private DealCampaignDetail itemList;


    public SubRankingItemAdapter(Context context, DealCampaignDetail itemList) {
        this.context = context;
        this.itemList = itemList;
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
            v = LayoutInflater.from(context).inflate(R.layout.item_sub_ranking, parent, false);
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
            return itemList.getData().getDealCampaignScores() != null ? itemList.getData().getDealCampaignScores().size() : 0;
        } catch (Exception e) {
            return 0;
        }
    }




    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLnlRankContent;
        private TextView mTvNo;
        private TextView mTvPhoneNumber;
        private TextView mTvTimeKeep;
        private ImageView imgNo1;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }
        private void initView(View itemView) {
            mLnlRankContent = (LinearLayout) itemView.findViewById(R.id.lnl_rank_content);
            mTvNo = (TextView) itemView.findViewById(R.id.tv_no);
            mTvPhoneNumber = (TextView) itemView.findViewById(R.id.tv_phone_number);
            mTvTimeKeep = (TextView) itemView.findViewById(R.id.tv_time_keep);
            imgNo1 = (ImageView) itemView.findViewById(R.id.img_no1);
        }
        public void bindItem(int position) {
            try {
                DealCampaignScore rank = itemList.getData().getDealCampaignScores().get(position);
                if(position <9){
                    if(position == 0){
                        imgNo1.setVisibility(View.VISIBLE);
                        mTvNo.setVisibility(View.GONE);
                        mTvPhoneNumber.setTextColor(Color.parseColor("#0CAF00"));
                        mTvTimeKeep.setTextColor(Color.parseColor("#0CAF00"));
                    }
                    else {
                        imgNo1.setVisibility(View.GONE);
                        mTvNo.setVisibility(View.VISIBLE);
                        mTvNo.setText("0"+(position+1));
                    }

                }else {
                    mTvNo.setText(String.valueOf(position+1));
                }
                String phoneNumSercurity = rank.getMobile().substring(0,rank.getMobile().length()-3);
                mTvPhoneNumber.setText(phoneNumSercurity+"xxx");
                String timeHold = Utils.CalendarUtils.ConvertMilliSecondsToHH(rank.getHoldTime());
                mTvTimeKeep.setText(timeHold);
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    if(account.getMobile().equals(rank.getMobile())){
                        mTvPhoneNumber.setTextColor(Color.parseColor("#FF0000"));
                        mTvTimeKeep.setTextColor(Color.parseColor("#FF0000"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


}
