package com.namviet.vtvtravel.view.f3.smalllocation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;

import java.util.List;

public class SubFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE = 0;
    private static final int TYPE_VIEW_MORE = 1;
    private Context context;
    private List<FilterTest> data;
    private boolean isExpanded = false;
    private int selectedPosition = -1;
    private IOnFilterClick iOnFilterClick;
    public SubFilterAdapter(List<FilterTest> data, Context context, IOnFilterClick iOnFilterClick) {
        this.context = context;
        this.data = data;
        this.iOnFilterClick = iOnFilterClick;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size() > 7) {
            if (isExpanded) {
                if (position == data.size() - 1) {
                    return TYPE_VIEW_MORE;
                } else return TYPE;
            } else {
                if (position == 7) {
                    return TYPE_VIEW_MORE;
                } else return TYPE;
            }
        } else return TYPE;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_filter_text, parent, false);
            return new ItemViewHolder(v);
        } else if (viewType == TYPE_VIEW_MORE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_filter_text_view_more, parent, false);
            return new ItemViewMore(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE) {
                ((ItemViewHolder) holder).bindItem(position);
            } else {
                ((ItemViewMore) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(isExpanded){
            return data.size();
        }else if (data.size() >7){
            return 8;
        }else return data.size();
    }

    public class ItemViewMore extends RecyclerView.ViewHolder {
        private TextView tvViewMore;
        public ItemViewMore(View itemView) {
            super(itemView);
            tvViewMore = itemView.findViewById(R.id.tv_view_more);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isExpanded){
                        isExpanded = false;
                        tvViewMore.setText("Hiển thị thêm");
                    }
                    else {
                        isExpanded = true;
                        tvViewMore.setText("Thu gọn");
                    }
                    notifyDataSetChanged();
                }
            });

        }

        public void bindItem(int position) {

        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_filter;
        private LinearLayout lnlFilter;
        private TextView tv_filter_count;
        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_filter = itemView.findViewById(R.id.tv_filter);
            tv_filter_count = itemView.findViewById(R.id.tv_filter_count);
            lnlFilter = itemView.findViewById(R.id.lnl_filter);
        }

        public void bindItem(int position) {
            FilterTest filterTest = data.get(position);
            if(filterTest.isSelected()){
                lnlFilter.setBackground(context.getResources().getDrawable(R.drawable.f3_bg_filter_text_selected));
                tv_filter.setTextColor(context.getResources().getColor(R.color.white));
                tv_filter_count.setTextColor(context.getResources().getColor(R.color.white));
            }else {
                lnlFilter.setBackground(context.getResources().getDrawable(R.drawable.f3_bg_filter_text));
                tv_filter.setTextColor(Color.parseColor("#00918D"));
                tv_filter_count.setTextColor(Color.parseColor("#808080"));
            }
            tv_filter.setText(data.get(position).getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(filterTest.isSelected()){
                        filterTest.setSelected(false);
                    }
                    else filterTest.setSelected(true);
                    if(iOnFilterClick != null){
                        iOnFilterClick.onItemFilterClick();
                    }
                    selectedPosition = position;
                    notifyDataSetChanged();
                }
            });
        }
    }

}
