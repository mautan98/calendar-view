package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.newhome.AppPromotionPartnerResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;

import java.util.ArrayList;
import java.util.List;

public class SubPromotionPartnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> idsClicked = new ArrayList<>();

    private static final int TYPE_ITEM = 0;

    private Context context;
    private List<AppPromotionPartnerResponse.Item> itemList;

    public SubPromotionPartnerAdapter(Context context, List<AppPromotionPartnerResponse.Item> itemList) {
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
            v = LayoutInflater.from(context).inflate(R.layout.f2_item_home_partner_link, parent, false);
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
        private ImageView imvBanner;
        private int position = 0;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imvBanner = itemView.findViewById(R.id.imvBanner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(!checkID(itemList.get(position).getId())) {
                            try {
                                TrackingAnalytic.postEvent(TrackingAnalytic.CLICK_PARTNER_BANNER_AD, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.HOME, TrackingAnalytic.ScreenTitle.HOME).setPartner_banner_ad_id(itemList.get(position).getId()).setScreen_class(this.getClass().getName()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            idsClicked.add(itemList.get(position).getId());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            setImage(itemList.get(position).getAvatarUri(), imvBanner);

            if(position == 0){
                try {
                    try {
                        TrackingAnalytic.postEvent(TrackingAnalytic.VIEW_PARTNER_BANNER_AD, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.HOME, TrackingAnalytic.ScreenTitle.HOME).setPartner_banner_ad_id(itemList.get(position).getId()).setScreen_class(this.getClass().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void setImage(String url, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).thumbnail(0.2f).into(image);

        }

        private boolean checkID(String id){
            for (int i = 0; i < idsClicked.size(); i++) {
                if(id.equals(idsClicked.get(i))){
                    return true;
                }
            }
            return false;
        }
    }


}
