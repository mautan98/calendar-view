package com.namviet.vtvtravel.adapter.filter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;

import java.util.List;

public class BaseFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private int oldSelected = 1000;
    private List<FilterByPageResponse.Data> itemTabs;

    public BaseFilterAdapter(List<FilterByPageResponse.Data> itemTabs, Context context, ClickItem clickItem, int position) {
        this.context = context;
        this.clickItem = clickItem;
        this.itemTabs = itemTabs;
        this.oldSelected = position;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_base_filter, parent, false);
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
            return itemTabs.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private int position;
        private TextView tvFilter;
        private RadioButton radioBtn;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvFilter = itemView.findViewById(R.id.tvFilter);
            radioBtn = itemView.findViewById(R.id.radioBtn);
            radioBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(itemTabs.get(position).isSelected()){
                        itemTabs.get(position).setSelected(false);
                        oldSelected = 1000;
                    }else {
                        itemTabs.get(position).setSelected(true);
                        if(oldSelected != 1000) {
                            itemTabs.get(oldSelected).setSelected(false);
                        }
                        oldSelected = position;
                    }

                    notifyDataSetChanged();
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            tvFilter.setText(itemTabs.get(position).getLabel());
            if(itemTabs.get(position).isSelected()){
                radioBtn.setChecked(true);
            }else {
                radioBtn.setChecked(false);
            }
        }
    }


    public interface ClickItem {
        void onClickItem(CommentResponse.Data.Comment comment);
    }


}
