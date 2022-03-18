package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;

import java.util.List;

public class DealSmallImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private List<String> urls;
    public int position_selected = 0;
    private ClickSubItem clickSubItem;

    public void setClickSubItem(ClickSubItem clickSubItem) {
        this.clickSubItem = clickSubItem;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    public DealSmallImageAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(context).inflate(R.layout.item_small_image_deal, parent, false);
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
//        try {
//            return items.size();
//        } catch (Exception e) {
//            return 0;
//        }
        return urls != null ? urls.size() : 0;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private ConstraintLayout constraintLayoutAvatar;
        public HeaderViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgAvatar);
            constraintLayoutAvatar = itemView.findViewById(R.id.constraint_avatar);



        }

        public void bindItem(int position) {
            setImage(urls.get(position),img);
            if(position_selected == position){
                constraintLayoutAvatar.setBackgroundColor(Color.parseColor("#FF9E2F"));
            }
            else {
                constraintLayoutAvatar.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickSubItem.onClickSubItem(position);
                    position_selected = position;
                    notifyDataSetChanged();
                }
            });
        }
        public void setImage(String url, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).thumbnail(0.2f).into(image);
        }
    }


    public interface ClickSubItem {
        void onClickSubItem(int position);
    }


}
