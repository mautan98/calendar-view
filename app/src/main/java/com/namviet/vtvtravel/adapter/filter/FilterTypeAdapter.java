package com.namviet.vtvtravel.adapter.filter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;

import java.util.List;

public class FilterTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<FilterByPageResponse.Data.Input> inputs;

    public FilterTypeAdapter(List<FilterByPageResponse.Data.Input> inputs, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.inputs = inputs;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_type_filter, parent, false);
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
            return inputs.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private int position;
        private TextView tvFilter;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            tvFilter = itemView.findViewById(R.id.tvFilter);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(inputs.get(position).isSelected()){
                        inputs.get(position).setSelected(false);
                    }else {
                        inputs.get(position).setSelected(true);
                    }
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            tvFilter.setText(inputs.get(position).getLabel());
            if(inputs.get(position).isSelected()){
                checkBox.setChecked(true);
            }else {
                checkBox.setChecked(false);
            }
        }
    }


    public interface ClickItem {
        void onClickItem(CommentResponse.Data.Comment comment);
    }


}
