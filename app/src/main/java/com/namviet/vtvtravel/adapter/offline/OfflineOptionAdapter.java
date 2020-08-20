package com.namviet.vtvtravel.adapter.offline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.offline.ItemsPopup;
import com.namviet.vtvtravel.model.offline.OfflineOption;

import java.util.List;

public class OfflineOptionAdapter extends RecyclerView.Adapter<OfflineOptionAdapter.ViewHolder> {
    private Context context;
    private List<ItemsPopup> offlineOptionList;
    private ItemClick itemClick;
    private String parentLink;

    public OfflineOptionAdapter(Context context, List<ItemsPopup> offlineOptionList,ItemClick itemClick, String parentLink) {
        this.context = context;
        this.offlineOptionList = offlineOptionList;
        this.itemClick  = itemClick;
        this.parentLink = parentLink;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_offline_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return offlineOptionList != null ? offlineOptionList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageImv;
        private TextView titleTxt;
        private TextView descriptionTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            imageImv = itemView.findViewById(R.id.imvImage);
            titleTxt = itemView.findViewById(R.id.txtTitle);
            descriptionTxt = itemView.findViewById(R.id.txtDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        itemClick.onItemClick(getAdapterPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void onBind(int position){
            ItemsPopup itemsPopup = offlineOptionList.get(position);
            Glide.with(context).load(parentLink+itemsPopup.getIcon()).into(imageImv);
            titleTxt.setText(itemsPopup.getTitle());
            descriptionTxt.setText(itemsPopup.getSubTitle());
        }
    }

    public interface ItemClick{
        void onItemClick(int position);
    }
}
