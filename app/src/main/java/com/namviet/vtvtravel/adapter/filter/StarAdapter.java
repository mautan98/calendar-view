package com.namviet.vtvtravel.adapter.filter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2filter.DistanceClass;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;
import com.namviet.vtvtravel.view.fragment.f2filter.FilterHomeFragment;

import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<FilterByPageResponse.Data.Input> starList;

    public StarAdapter(List<FilterByPageResponse.Data.Input> starList, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.starList = starList;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_filter_star, parent, false);
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
            return starList.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearStar;
        private TextView tvNumber;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            linearStar = itemView.findViewById(R.id.linearStar);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < starList.size(); i++) {
                        starList.get(i).setSelected(false);
                    }
                    starList.get(position).setSelected(true);
                    notifyDataSetChanged();
//                    clickItem.onClickItemStar(starList.get(position));
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            if (starList.get(position).isSelected()) {
                linearStar.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_star_selected));
                tvNumber.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                linearStar.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_star));
                tvNumber.setTextColor(Color.parseColor("#C7C7C7"));
            }
            tvNumber.setText(starList.get(position).getLabel());
        }
    }


    public interface ClickItem {
        void onClickItemStar(FilterHomeFragment.Star distance);
    }
}
