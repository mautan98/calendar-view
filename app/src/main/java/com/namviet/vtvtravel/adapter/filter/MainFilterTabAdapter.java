package com.namviet.vtvtravel.adapter.filter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.filter.ItemTab;
import com.namviet.vtvtravel.model.model.MainFilterType;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;

import java.util.List;

public class MainFilterTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<ItemTab> mainFilterTypes;

    public MainFilterTabAdapter(List<ItemTab> mainFilterTypes, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.mainFilterTypes = mainFilterTypes;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_tab_filter, parent, false);
            return new HeaderViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return mainFilterTypes.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layoutFilter;
        private ImageView imgFilter;
        private TextView tvFilterName;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            layoutFilter = itemView.findViewById(R.id.layoutFilter);
            imgFilter = itemView.findViewById(R.id.imgFilter);
            tvFilterName = itemView.findViewById(R.id.tvFilterName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < mainFilterTypes.size(); i++) {
                        mainFilterTypes.get(i).setSelected(false);
                    }
                    mainFilterTypes.get(position).setSelected(true);
                    notifyDataSetChanged();
                    clickItem.onClickItem(mainFilterTypes.get(position));
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            if (mainFilterTypes.get(position).isSelected()) {
                layoutFilter.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_filter_selected));
                tvFilterName.setTextColor(ContextCompat.getColor(context, R.color.white));
                Glide.with(context).load(mainFilterTypes.get(position).getIcon_enable_url()).into(imgFilter);
            } else {
                layoutFilter.setBackground(ContextCompat.getDrawable(context, R.drawable.f2_bg_tab_filter));
                tvFilterName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                Glide.with(context).load(mainFilterTypes.get(position).getIcon_url()).into(imgFilter);
            }
            tvFilterName.setText(mainFilterTypes.get(position).getName());
        }
    }


    public interface ClickItem {
        void onClickItem(ItemTab itemTab);
    }
}
