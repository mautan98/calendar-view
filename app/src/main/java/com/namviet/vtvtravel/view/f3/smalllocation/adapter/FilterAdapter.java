package com.namviet.vtvtravel.view.f3.smalllocation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2filter.DistanceClass;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SWIPE = 1;
    private Context context;
    private FilterByPageResponse data;

    public FilterAdapter(FilterByPageResponse data, Context context) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 4) {
            return TYPE_SWIPE;
        } else return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_filter, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_SWIPE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_filter_switch, parent, false);
            return new SwipeFilterViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_SWIPE) {
                ((SwipeFilterViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return data.getData().size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rcvItemFilter;
        SubFilterAdapter subFilterAdapter;
        TextView tvHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            rcvItemFilter = itemView.findViewById(R.id.rcv_item_text);
            tvHeader = itemView.findViewById(R.id.tv_header);
        }

        public void bindItem(int position) {
            subFilterAdapter = new SubFilterAdapter(data.getData().get(position), context, new IOnFilterClick() {
                @Override
                public void onItemFilterClick() {
                    Log.e("xxx", "onItemFilterClick: ");
                }
            });
            rcvItemFilter.setAdapter(subFilterAdapter);
            tvHeader.setText(data.getData().get(position).getLabel());
            switch (data.getData().get(position).getCode()){
                case "HOTEL_STANDARD_RATE":
                    rcvItemFilter.setLayoutManager(new LinearLayoutManager(context));
                    break;
                default:
                    rcvItemFilter.setLayoutManager(new FlexboxLayoutManager(context));
                    break;
            }
            subFilterAdapter.notifyDataSetChanged();

        }
    }
    public class SwipeFilterViewHolder extends RecyclerView.ViewHolder {

        public SwipeFilterViewHolder(View itemView) {
            super(itemView);

        }

        public void bindItem(int position) {

        }
    }

}
