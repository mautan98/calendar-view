package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.newhome.AppFavoriteDestinationResponse;

import java.util.List;

public class SubFavoriteDestinationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_SEE_MORE = 1;
    private Context context;
    private List<AppFavoriteDestinationResponse.Item> itemList;
    private ClickItem clickItem;

    public SubFavoriteDestinationAdapter(Context context, List<AppFavoriteDestinationResponse.Item> itemList, ClickItem clickItem) {
        this.context = context;
        this.itemList = itemList;
        this.clickItem = clickItem;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if(position == itemList.size()){
                return TYPE_ITEM_SEE_MORE;
            }else {
                return TYPE_ITEM;
            }
        } catch (Exception e) {
            return TYPE_ITEM_SEE_MORE;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(context).inflate(R.layout.f2_item_favor_place, parent, false);
            return new HeaderViewHolder(v);
        }else if (viewType == TYPE_ITEM_SEE_MORE) {
            v = LayoutInflater.from(context).inflate(R.layout.f2_item_favor_place_see_more, parent, false);
            return new SeeMoreViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
            if (getItemViewType(position) == TYPE_ITEM_SEE_MORE) {
                ((SeeMoreViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() + 1 : 1;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView imvBanner;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imvBanner = itemView.findViewById(R.id.imvBanner);
        }

        public void bindItem(int position) {
            tvName.setText(itemList.get(position).getName());
            setImage(itemList.get(position).getBanner_url(), imvBanner);
            imvBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(itemList.get(position).getId());
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

    public class SeeMoreViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView imvBanner;

        public SeeMoreViewHolder(View itemView) {
            super(itemView);
        }

        public void bindItem(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickSeeMore();
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

    public interface ClickItem {
        void onClickItem(String regionId);
        void onClickSeeMore();
    }


}
