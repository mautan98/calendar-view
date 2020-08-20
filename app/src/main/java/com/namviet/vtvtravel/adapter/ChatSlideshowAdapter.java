package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.chat.ChatAnswer;

import java.util.List;

public class ChatSlideshowAdapter extends RecyclerView.Adapter<ChatSlideshowAdapter.SlideShowHolder> {

    private Context mContext;
    private List<ChatAnswer> mSlideshowChats;
    private TravelSelectListener travelSelectListener;

    public ChatSlideshowAdapter(Context mContext, List<ChatAnswer> mSlideshowChats) {
        this.mContext = mContext;
        this.mSlideshowChats = mSlideshowChats;
    }

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }

    @NonNull
    @Override
    public SlideShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_chat_slideshow_detail, parent, false);
        return new SlideShowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideShowHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return mSlideshowChats.size();
    }

    public class SlideShowHolder extends RecyclerView.ViewHolder {
        private LinearLayout mItemViewLl;
        private ImageView mLogoUrlImv;
        private TextView mNameTv;
        private TextView mAddressTv;

        public SlideShowHolder(View itemView) {
            super(itemView);
            mItemViewLl = itemView.findViewById(R.id.ll_item_view);
            mLogoUrlImv = itemView.findViewById(R.id.imv_logo_url);
            mNameTv = itemView.findViewById(R.id.tv_name);
            mAddressTv = itemView.findViewById(R.id.tv_address);
        }

        public void bindItem(int position) {
            setImageUrl(mSlideshowChats.get(position).getLogo_url(), mLogoUrlImv);
            mNameTv.setText(mSlideshowChats.get(position).getName());
            mAddressTv.setText(mSlideshowChats.get(position).getAddress());
            mItemViewLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Travel travel = new Travel();
//                    travel.setDetail_link(mSlideshowChats.get(position).getDetail_link());
                    String url = WSConfig.HOST + mSlideshowChats.get(position).getContent_type() + "/" + mSlideshowChats.get(position).getId();
                    travel.setDetail_link(url);
                    travel.setContent_type(mSlideshowChats.get(position).getContent_type());
                    travelSelectListener.onSelectTravel(travel);
                }
            });
        }

        private void setImageUrl(String ulrCs, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(mContext)
                    .setDefaultRequestOptions(requestOptions)
                    .load(ulrCs)
                    .thumbnail(0.2f)
                    .into(image);

        }
    }
}
