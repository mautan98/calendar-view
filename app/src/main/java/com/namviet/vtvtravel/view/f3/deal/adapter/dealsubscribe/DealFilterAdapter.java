package com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.RewardStatus;
import com.namviet.vtvtravel.viewmodel.newhome.ChangeRegionDialog;

import java.util.ArrayList;

public class DealFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ArrayList<RewardStatus> listFilter;
    private ClickItem clickItem;

    public DealFilterAdapter(Context context, ArrayList<RewardStatus> listFilter, ClickItem clickItem) {
        this.context = context;
        this.listFilter = listFilter;
        this.clickItem = clickItem;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_deal, parent, false);
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
            return listFilter.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMark;
        private TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgMark = itemView.findViewById(R.id.imgMark);
        }

        public void bindItem(int position) {
            tvTitle.setText(listFilter.get(position).getLabel());
            if (listFilter.get(position).isMarked()) {
                imgMark.setVisibility(View.VISIBLE);
            } else {
                imgMark.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < listFilter.size(); i++) {
                        listFilter.get(i).setMarked(false);
                    }
                    listFilter.get(position).setMarked(true);
                    clickItem.onClickItem(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface ClickItem {
        void onClickItem(int position);
    }
}
