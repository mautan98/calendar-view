package com.namviet.vtvtravel.adapter.filter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.filter.ItemTab;
import com.namviet.vtvtravel.response.f2filter.DistanceClass;

import java.util.List;

public class DistanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<DistanceClass.Distance> distances;

    public DistanceAdapter(List<DistanceClass.Distance> distances, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.distances = distances;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_distance, parent, false);
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
            return distances.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layoutFilter;
        private TextView tvDistance;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            layoutFilter = itemView.findViewById(R.id.layoutFilter);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < distances.size(); i++) {
                        distances.get(i).setSelected(false);
                    }
                    distances.get(position).setSelected(true);
                    notifyDataSetChanged();
                    clickItem.onClickItemDistance(distances.get(position));
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            if (distances.get(position).isSelected()) {
                layoutFilter.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_filter_selected));
                tvDistance.setTextColor(ContextCompat.getColor(context, R.color.white));
            } else {
                layoutFilter.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_filter));
                tvDistance.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
            tvDistance.setText(distances.get(position).getLabel());
        }
    }


    public interface ClickItem {
        void onClickItemDistance(DistanceClass.Distance distance);
    }
}
