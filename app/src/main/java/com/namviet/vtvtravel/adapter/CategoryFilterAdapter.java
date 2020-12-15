package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.FilterData;

import java.util.ArrayList;

public class CategoryFilterAdapter extends RecyclerView.Adapter<CategoryFilterAdapter.CategoryFilterHolder> {
    private Context mContext;
    private ArrayList<FilterData> listFilterData;



    public CategoryFilterAdapter(Context mContext, ArrayList<FilterData> filterData) {
        this.mContext = mContext;
        this.listFilterData = filterData;
    }

    @Override
    public CategoryFilterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_filter, parent, false);
        return new CategoryFilterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryFilterHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return listFilterData == null ? 0 : listFilterData.size();
    }

    public class CategoryFilterHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private RecyclerView rvListChild;

        public CategoryFilterHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rvListChild = itemView.findViewById(R.id.rvListChild);
            rvListChild.setLayoutManager(new LinearLayoutManager(mContext));
            rvListChild.setNestedScrollingEnabled(false);
        }

        public void bindItem(int position) {
            FilterData filterData = listFilterData.get(position);
            tvTitle.setText(filterData.getLabel());
            FilterAdapter filterAdapter = new FilterAdapter(mContext, filterData.getInputs());
            rvListChild.setAdapter(filterAdapter);
        }

    }

    public void cancelFilter() {
        if (null != listFilterData) {
            for (int i = 0; i < listFilterData.size(); i++) {
                FilterData filterData = listFilterData.get(i);
                for (int j = 0; j < filterData.getInputs().size(); j++) {
                    listFilterData.get(i).getInputs().get(j).setSelected(false);
                }
            }
            notifyDataSetChanged();
        }
    }

    public ArrayList<FilterData> getListFilterData() {
        return listFilterData;
    }
}
