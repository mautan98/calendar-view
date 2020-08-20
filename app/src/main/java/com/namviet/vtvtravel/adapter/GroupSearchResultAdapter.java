package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;

public class GroupSearchResultAdapter extends RecyclerView.Adapter<GroupSearchResultAdapter.GroupSearchHolder> {
    private Context mContext;

    @NonNull
    @Override
    public GroupSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_search, parent, false);
        return new GroupSearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupSearchHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GroupSearchHolder extends BaseHolder {
        private TextView tvTitle;
        private RecyclerView rvItem;

        public GroupSearchHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rvItem = itemView.findViewById(R.id.rvItem);
            rvItem.setLayoutManager(new GridLayoutManager(mContext, 2));
            rvItem.setNestedScrollingEnabled(false);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
        }
    }
}
