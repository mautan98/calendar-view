package com.namviet.vtvtravel.adapter.travelvoucher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;

public class TravelVoucherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private final Context context;
    private final ClickItem clickItem;
    private final List<ListVoucherResponse.Data.Voucher> vouchers;

    public TravelVoucherAdapter(List<ListVoucherResponse.Data.Voucher> vouchers, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.vouchers = vouchers;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_travel_voucher, parent, false);
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
            return vouchers.size();
        } catch (Exception e) {
            return 10;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private final ImageView imgAvatar;
        private final TextView tvName;
        private final TextView tvTimeLeft;
        private final View imgTime;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvTimeLeft = itemView.findViewById(R.id.tvTimeLeft);
            imgTime = itemView.findViewById(R.id.imgTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(vouchers.get(position));
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;

            ListVoucherResponse.Data.Voucher travel = vouchers.get(position);
            Glide.with(context).load(travel.getAvatarUri()).into(imgAvatar);
            tvName.setText(travel.getName());

            try {
                tvTimeLeft.setText("Hạn đến " + DateUtltils.timeToString(travel.getExpireDate() / 1000));
                imgTime.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                imgTime.setVisibility(View.INVISIBLE);
                e.printStackTrace();
            }


        }
    }


    public interface ClickItem {
        void onClickItem(ListVoucherResponse.Data.Voucher voucher);
    }


}
