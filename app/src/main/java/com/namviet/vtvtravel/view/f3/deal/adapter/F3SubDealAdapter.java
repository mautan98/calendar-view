package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.f3.deal.constant.IsProcessingType;
import com.namviet.vtvtravel.view.f3.deal.model.deal.Content;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.view.f3.deal.view.dealdetail.DetailDealActivity;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

public class F3SubDealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private DealResponse dealResponse;
    private BaseViewModel viewModel;


    public interface ILoadDataBlock {
        void onLoaDataAtPosition(int positionChild);
    }

    public F3SubDealAdapter(Context context, DealResponse dealResponse, BaseViewModel viewModel) {
        this.context = context;
        this.dealResponse = dealResponse;
        this.viewModel = viewModel;

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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_deal, parent, false);
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
            return dealResponse.getData().getContent().size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvatar;
        private TextView tvUserTotal;
        private TextView tvName;
        private TextView tvDisplayPrice;
        private TextView tvOriginPrice;
        private TextView tvDiscount;
        private TextView tvDayLeft;
        private TextView tvExpiryDate;
        private TextView tvYouHunting;
        private LinearLayout layoutIsHuntingUser;
        private RelativeLayout layoutTotalMustHaveChild;
        private LinearLayout layoutTotalHuntingUser;
        private TextView tvTotalMustHaveChild;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvUserTotal = itemView.findViewById(R.id.tvUserTotal);
            tvName = itemView.findViewById(R.id.tvName);
            tvDisplayPrice = itemView.findViewById(R.id.tvDisplayPrice);
            tvOriginPrice = itemView.findViewById(R.id.tvOriginPrice);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvDayLeft = itemView.findViewById(R.id.tvDayLeft);
            tvExpiryDate = itemView.findViewById(R.id.expiry_date);
            tvYouHunting = itemView.findViewById(R.id.tv_you_hunting);
            layoutIsHuntingUser = itemView.findViewById(R.id.layoutIsHuntingUser);
            layoutTotalMustHaveChild = itemView.findViewById(R.id.layoutTotalMustHaveChild);
            tvTotalMustHaveChild = itemView.findViewById(R.id.tvTotalMustHaveChild);
            layoutTotalHuntingUser = itemView.findViewById(R.id.layoutTotalHuntingUser);
        }

        public void bindItem(int position) {
            Content content = dealResponse.getData().getContent().get(position);
            tvName.setText(content.getName());
            tvUserTotal.setText(content.getUserHuntingCount() + "+");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailDealActivity.startScreen(context, content.getId().toString(),content.isCampaign());
                }
            });
            if (content.getIsProcessing() != null) {

                if (content.getIsProcessing().equals(IsProcessingType.DANG_DIEN_RA_TYPE)) {
                    long timeStamp = content.getEndAt();
                    long myCurrentTimeMillis = System.currentTimeMillis();
                    if (myCurrentTimeMillis > timeStamp) {
                        tvDayLeft.setText("Còn lại 0 ngày");
                    } else {
                        long distance = (timeStamp - myCurrentTimeMillis) / 1000;

                        String days = (int) (distance / 86400) + " ngày ";
                        String hours = String.valueOf((int) ((distance % 86400) / 3600));
                        String minutes = String.valueOf((int) ((distance % 3600) / 60));
                        String seconds = String.valueOf((int) ((distance % 3600) % 60));

                        tvDayLeft.setText("Còn lại " + days + hours + ":" + minutes + ":" + seconds);

                    }
                }else if(content.getIsProcessing().equals(IsProcessingType.SAP_DIEN_RA_TYPE)){
                    long timeStamp = content.getBeginAt();
                    long myCurrentTimeMillis = System.currentTimeMillis();
                    if (myCurrentTimeMillis > timeStamp) {
                        tvDayLeft.setText("Bắt đầu sau 0 ngày");
                    } else {
                        long distance = (timeStamp - myCurrentTimeMillis) / 1000;

                        String days = (int) (distance / 86400) + " ngày ";
                        String hours = String.valueOf((int) ((distance % 86400) / 3600));
                        String minutes = String.valueOf((int) ((distance % 3600) / 60));
                        String seconds = String.valueOf((int) ((distance % 3600) % 60));

                        tvDayLeft.setText("Bắt đầu sau " + days + hours + ":" + minutes + ":" + seconds);

                    }
                }else {
                    tvDayLeft.setText("Đã hết hạn");
                }
            }else {
                tvDayLeft.setText("Đã hết hạn");
            }

            try {
                Glide.with(context).load(content.getAvatarUri()).placeholder(R.drawable.img_placeholder).into(imgAvatar);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tvExpiryDate.setText("HSD: " + DateUtltils.timeToString18(content.getExpireDate()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tvTotalMustHaveChild.setText("Tích luỹ từ " + content.getTotalDeal() + "\nCTKM");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (content.isCampaign()) {
                layoutTotalMustHaveChild.setVisibility(View.VISIBLE);
            } else {
                layoutTotalMustHaveChild.setVisibility(View.GONE);
            }

            if (content.getIsUserHunting()) {
                layoutIsHuntingUser.setVisibility(View.VISIBLE);
            } else {
                layoutIsHuntingUser.setVisibility(View.INVISIBLE);
            }
        }
    }
}
