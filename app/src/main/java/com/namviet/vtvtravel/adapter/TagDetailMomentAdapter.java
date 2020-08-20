package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.response.DetailNewsData;

import java.util.List;

public class TagDetailMomentAdapter extends RecyclerView.Adapter<TagDetailMomentAdapter.TagHolder> {
    private Context mContext;
    private List<DetailNewsData.Tag> tagList;

    public TagDetailMomentAdapter(Context mContext, List<DetailNewsData.Tag> tagList) {
        this.mContext = mContext;
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public TagDetailMomentAdapter.TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag_moment, parent, false);
        return new TagDetailMomentAdapter.TagHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagDetailMomentAdapter.TagHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return tagList == null ? 0 : tagList.size();
    }

    public class TagHolder extends BaseHolder {
        private TextView tvTag;

        public TagHolder(View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.tvTag);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            String tag = tagList.get(position).getName();
            tvTag.setText("#" + tag);
        }
    }
}
