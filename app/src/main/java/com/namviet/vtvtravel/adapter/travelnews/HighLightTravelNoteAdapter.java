package com.namviet.vtvtravel.adapter.travelnews;

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
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.travelnews.Travel;

import java.util.List;

public class HighLightTravelNoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<Travel> travels;

    public HighLightTravelNoteAdapter(Context context, List<Travel> travels, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.travels = travels;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_high_light_travel_note, parent, false);
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
            return travels.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private ImageView imvBanner;
        private TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            imvBanner = itemView.findViewById(R.id.imvBanner);
            tvTitle = itemView.findViewById(R.id.tvTitle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(travels.get(position));
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            Glide.with(context).load(travels.get(position).getLogo_url()).into(imvBanner);
            tvTitle.setText(travels.get(position).getName());

        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);
    }


}
