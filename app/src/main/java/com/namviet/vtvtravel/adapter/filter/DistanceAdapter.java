package com.namviet.vtvtravel.adapter.filter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_filter_text, parent, false);
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
        private TextView tv_filter_count;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            layoutFilter = itemView.findViewById(R.id.lnl_filter);
            tvDistance = itemView.findViewById(R.id.tv_filter);
            tv_filter_count = itemView.findViewById(R.id.tv_filter_count);
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
                layoutFilter.setBackground(context.getResources().getDrawable(R.drawable.f3_bg_filter_text_selected));
                tvDistance.setTextColor(context.getResources().getColor(R.color.white));
                tv_filter_count.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                layoutFilter.setBackground(context.getResources().getDrawable(R.drawable.f3_bg_filter_text));
                tvDistance.setTextColor(Color.parseColor("#00918D"));
                tv_filter_count.setTextColor(Color.parseColor("#808080"));
            }
            tvDistance.setText("Dưới "+distances.get(position).getLabel());
        }
    }


    public interface ClickItem {
        void onClickItemDistance(DistanceClass.Distance distance);
    }
}
