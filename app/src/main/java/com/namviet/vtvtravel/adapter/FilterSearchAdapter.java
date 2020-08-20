package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.listener.FilterSelectListener;
import com.namviet.vtvtravel.model.Filter;

import java.util.ArrayList;

public class FilterSearchAdapter extends RecyclerView.Adapter<FilterSearchAdapter.FilterSearchHolder> {

    private Context mContext;
    private ArrayList<Filter> listFilter;
    private String title;

    private FilterSelectListener filterSelectListener;

    public void setFilterSelectListener(FilterSelectListener filterSelectListener) {
        this.filterSelectListener = filterSelectListener;
    }

    public FilterSearchAdapter(Context mContext, ArrayList<Filter> listFilter, String title) {
        this.mContext = mContext;
        this.listFilter = listFilter;
        this.title = title;
    }


    @Override
    public FilterSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_filter, parent, false);
        return new FilterSearchHolder(v);
    }

    @Override
    public void onBindViewHolder(FilterSearchHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return listFilter == null ? 0 : listFilter.size();
    }

    public class FilterSearchHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivSelect;

        public FilterSearchHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivSelect = itemView.findViewById(R.id.ivSelect);
            ivSelect.setVisibility(View.INVISIBLE);
        }

        public void bindItem(final int position) {
            final Filter filter = listFilter.get(position);
            tvName.setText(filter.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != filterSelectListener) {
                        filterSelectListener.onSelectItem(filter, title);
                    }
                }
            });
        }
    }


}

