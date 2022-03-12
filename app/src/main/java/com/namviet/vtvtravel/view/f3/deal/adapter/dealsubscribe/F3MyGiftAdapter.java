package com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe;

import android.content.Context;
import android.graphics.Paint;
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
import com.namviet.vtvtravel.view.f3.deal.constant.DiscountDisplayType;
import com.namviet.vtvtravel.view.f3.deal.constant.IsProcessingType;
import com.namviet.vtvtravel.view.f3.deal.model.deal.Content;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.view.f3.deal.view.dealdetail.DetailDealActivity;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.text.DecimalFormat;

public class F3MyGiftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private DealResponse dealResponse;
    private BaseViewModel viewModel;


    public F3MyGiftAdapter(Context context, DealResponse dealResponse, BaseViewModel viewModel) {
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_my_gift, parent, false);
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
            return 10;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {




        public HeaderViewHolder(View itemView) {
            super(itemView);

        }

        public void bindItem(int position) {

        }
    }

    public static String convertPrice(String string) {
        try {
            DecimalFormat df = new DecimalFormat("###,###,###");
            return df.format(Double.parseDouble(string));
        } catch (Exception e) {
            return string;
        }
    }

    public static String getHuntingUserCount(Integer input){
        if(input == null || input < 10){
            return "0";
        } else {
            if(input < 100){
                return "10";
            }else if(input < 1000){
                return "100";
            }else if(input < 10000){
                return "1000";
            }else if(input < 100000){
                return "10000";
            }else if(input < 1000000){
                return "100000";
            }else {
                return "1000000";
            }
        }
    }
}
