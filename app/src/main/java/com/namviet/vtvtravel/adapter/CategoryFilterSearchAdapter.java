package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.listener.FilterSelectListener;
import com.namviet.vtvtravel.model.Filter;
import com.namviet.vtvtravel.response.FilterData;
import com.namviet.vtvtravel.response.FilterSearchData;

import java.util.ArrayList;

public class CategoryFilterSearchAdapter extends RecyclerView.Adapter<CategoryFilterSearchAdapter.FilterSearchHolder> {
    private Context mContext;
    private ArrayList<FilterSearchData> listFilterData;
    private FilterSelectListener filterSelectListener;

    public void setFilterSelectListener(FilterSelectListener filterSelectListener) {
        this.filterSelectListener = filterSelectListener;
    }

    public CategoryFilterSearchAdapter(Context mContext, ArrayList<FilterSearchData> filterData) {
        this.mContext = mContext;
        this.listFilterData = filterData;
    }

    @Override
    public FilterSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_filter, parent, false);
        return new FilterSearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterSearchHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return listFilterData == null ? 0 : listFilterData.size();
    }

    public class FilterSearchHolder extends RecyclerView.ViewHolder implements FilterSelectListener {
        private TextView tvTitle;
        private RecyclerView rvListChild;

        public FilterSearchHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rvListChild = itemView.findViewById(R.id.rvListChild);
            rvListChild.setLayoutManager(new LinearLayoutManager(mContext));
            rvListChild.setNestedScrollingEnabled(false);
        }

        public void bindItem(int position) {
            FilterSearchData filterData = listFilterData.get(position);
            tvTitle.setText(filterData.getName());
            FilterSearchAdapter filterAdapter = new FilterSearchAdapter(mContext, filterData.getItems(), filterData.getName());
            rvListChild.setAdapter(filterAdapter);
            filterAdapter.setFilterSelectListener(this);
        }

        @Override
        public void onSelect(ArrayList<FilterData> list) {

        }

        @Override
        public void onSelectItem(Filter filter, String title) {
            if (null != filterSelectListener) {
                filterSelectListener.onSelectItem(filter, title);
            }
        }
    }

}
