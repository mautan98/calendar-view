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

import java.util.List;

public class TagVideoAdapter extends RecyclerView.Adapter<TagVideoAdapter.TagHolder> {
    private Context mContext;
    private List<String> tagList;

    public TagVideoAdapter(Context mContext, List<String> tagList) {
        this.mContext = mContext;
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag_video, parent, false);
        return new TagHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
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
            String tag = tagList.get(position);
            tvTag.setText("#" + tag);
        }
    }
}
