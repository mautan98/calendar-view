package com.namviet.vtvtravel.view.f3.smalllocation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.filter.DistanceAdapter;
import com.namviet.vtvtravel.response.f2filter.DistanceClass;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SWIPE = 1;
    private static final int TYPE_DISTANCE = 2;
    private Context context;
    private FilterByPageResponse mData;
    private FilterByCodeResponse filterByCodeResponse;

    public FilterAdapter(FilterByPageResponse data, Context context, FilterByCodeResponse filterByCodeResponse) {
        this.context = context;
        this.filterByCodeResponse = filterByCodeResponse;
        this.mData = data;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == mData.getData().size() - 1) {
//            return TYPE_DISTANCE;
//        }
//        if (position == mData.getData().size() - 2) {
//            return TYPE_SWIPE;
//        } else return TYPE_ITEM;
        if (mData.getData().get(position).getTypeData() != null && mData.getData().get(position).getTypeData().equals(FilterByPageResponse.TYPE_DISTANCE)) {
            return TYPE_DISTANCE;
        } else if (mData.getData().get(position).getTypeData() != null && mData.getData().get(position).getTypeData().equals(FilterByPageResponse.TYPE_OPEN_STATE)) {
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
        } else if (viewType == TYPE_DISTANCE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_filter, parent, false);
            return new DistanceViewHolder(v);
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
            } else if (getItemViewType(position) == TYPE_DISTANCE) {
                ((DistanceViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return mData.getData().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public class DistanceViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rcvItemFilter;
        DistanceAdapter distanceAdapter;
        TextView tvHeader;

        public DistanceViewHolder(View itemView) {
            super(itemView);
            rcvItemFilter = itemView.findViewById(R.id.rcv_item_text);
            tvHeader = itemView.findViewById(R.id.tv_header);
        }

        public void bindItem(int position) {
            distanceAdapter = new DistanceAdapter(filterByCodeResponse.getDistanceClass().getDistances(), context, new DistanceAdapter.ClickItem() {
                @Override
                public void onClickItemDistance(DistanceClass.Distance distance) {

                }
            });
            rcvItemFilter.setAdapter(distanceAdapter);
            tvHeader.setText("Khoảng cách");
            rcvItemFilter.setLayoutManager(new FlexboxLayoutManager(context));
            distanceAdapter.notifyDataSetChanged();

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
            subFilterAdapter = new SubFilterAdapter(mData.getData().get(position), context, new IOnFilterClick() {
                @Override
                public void onItemFilterClick() {
                    Log.e("xxx", "onItemFilterClick: ");
                }
            });
            rcvItemFilter.setAdapter(subFilterAdapter);
            tvHeader.setText(mData.getData().get(position).getLabel());
            switch (mData.getData().get(position).getCode()) {
                case "HOTEL_STANDARD_RATE":
                    rcvItemFilter.setLayoutManager(new FlexboxLayoutManager(context));
                    break;
                default:
                    rcvItemFilter.setLayoutManager(new FlexboxLayoutManager(context));
                    break;
            }
            subFilterAdapter.notifyDataSetChanged();

        }
    }

    public class SwipeFilterViewHolder extends RecyclerView.ViewHolder {
        private SwitchCompat swOpenState;

        public SwipeFilterViewHolder(View itemView) {
            super(itemView);
            swOpenState = itemView.findViewById(R.id.sw_open_sate);
        }

        public void bindItem(int position) {
            swOpenState.setChecked(filterByCodeResponse.isTypeOpen());
            swOpenState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    filterByCodeResponse.setTypeOpen(b);
                }
            });
        }
    }

}
