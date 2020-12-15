package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.response.newhome.ItemAppVoucherNowResponse;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;

public class SubVoucherNowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private ItemAppVoucherNowResponse itemAppVoucherNowResponse;
    private Context context;

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    public SubVoucherNowAdapter(ItemAppVoucherNowResponse itemAppVoucherNowResponse, Context context) {
        this.itemAppVoucherNowResponse = itemAppVoucherNowResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_home_deal, parent, false);
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
            return itemAppVoucherNowResponse.getItems().size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvBanner;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imvBanner = itemView.findViewById(R.id.imvBanner);
        }

        public void bindItem(int position) {
            Glide.with(context).load(itemAppVoucherNowResponse.getItems().get(position).getAvatarUri()).into(imvBanner);
            imvBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ListVoucherResponse.Data.Voucher voucher = new Gson().fromJson(  new Gson().toJson(itemAppVoucherNowResponse.getItems().get(position)), ListVoucherResponse.Data.Voucher.class);
//                        DetailDealWebviewActivity.startScreen((Activity)context, itemAppVoucherNowResponse.getItems().get(position).getLinkVoucher());
                        TravelVoucherActivity.startScreenDetail(context, true, TravelVoucherActivity.OpenType.DETAIL, voucher);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
