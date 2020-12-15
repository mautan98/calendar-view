package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.SearchSelectListener;
import com.namviet.vtvtravel.model.NearItem;

import java.util.List;

public class SearchNearAdapter extends RecyclerView.Adapter<SearchNearAdapter.SearchNearHolder> {
    private Context mContext;
    private List<NearItem> trendList;
    private SearchSelectListener searchSelectListener;
    private String title;

    public void setSearchSelectListener(SearchSelectListener searchSelectListener) {
        this.searchSelectListener = searchSelectListener;
    }

    public SearchNearAdapter(Context mContext, List<NearItem> trendList, String title) {
        this.mContext = mContext;
        this.title = title;
        this.trendList = trendList;
    }

    @NonNull
    @Override
    public SearchNearHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new SearchNearHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNearHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return trendList == null ? 0 : trendList.size();
    }

    public class SearchNearHolder extends BaseHolder {
        private TextView tvTitle;

        public SearchNearHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final NearItem nearItem = trendList.get(position);
            if (null != nearItem.getName()) {
                tvTitle.setText(nearItem.getName());
            } else if (null != nearItem.getKeyword()) {
                tvTitle.setText(nearItem.getKeyword());
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != searchSelectListener) {
                        nearItem.setTitleBar(title);
                        searchSelectListener.onSearchSelect(nearItem);
                    }
                }
            });
        }
    }
}
