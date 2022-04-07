package com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe;

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
import com.namviet.vtvtravel.view.f3.deal.model.mygift.MyGift;

import java.util.ArrayList;

public class F3MyGiftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ArrayList<MyGift> myGifts;


    public F3MyGiftAdapter(Context context, ArrayList<MyGift> myGifts) {
        this.context = context;
        this.myGifts = myGifts;

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
            return myGifts.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvType;
        private ImageView imgAvatar;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvType = itemView.findViewById(R.id.tvType);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);

        }

        public void bindItem(int position) {
            tvName.setText(myGifts.get(position).getName());
            tvType.setText(myGifts.get(position).getType());
            Glide.with(context).load(myGifts.get(position).getAvatarUri()).error(R.drawable.img_placeholder).into(imgAvatar);
        }
    }

}
