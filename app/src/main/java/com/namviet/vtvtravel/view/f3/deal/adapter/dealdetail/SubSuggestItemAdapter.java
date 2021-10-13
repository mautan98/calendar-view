package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;

import java.util.List;

public class SubSuggestItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    private Context context;
    private List<?> itemList;



    public SubSuggestItemAdapter(Context context, List<?> itemList) {
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
            v = LayoutInflater.from(context).inflate(R.layout.item_deal, parent, false);
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
        return itemList != null ? itemList.size() : 0;
    }




    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvatar;
        private TextView mTvDiscount;
        private TextView mTvName;
        private TextView mTvUserTotal;
        private TextView mTvOriginPrice;
        private TextView mTvDisplayPrice;
        private ProgressBar mProgress1;
        private TextView mTvDayLeft;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }
        private void initView(View itemView) {
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mTvDiscount = (TextView) itemView.findViewById(R.id.tvDiscount);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvUserTotal = (TextView) itemView.findViewById(R.id.tvUserTotal);
            mTvOriginPrice = (TextView) itemView.findViewById(R.id.tvOriginPrice);
            mTvDisplayPrice = (TextView) itemView.findViewById(R.id.tvDisplayPrice);
            mProgress1 = (ProgressBar) itemView.findViewById(R.id.progress1);
            mTvDayLeft = (TextView) itemView.findViewById(R.id.tvDayLeft);
        }
        public void bindItem(int position) {
            setImage(R.drawable.bg_register_service_success,mImgAvatar );
        }

        public void setImage(int resId, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(resId).thumbnail(0.2f).into(image);
        }
    }


}
