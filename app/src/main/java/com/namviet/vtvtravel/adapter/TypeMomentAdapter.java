package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.Filter;

import java.util.List;

public class TypeMomentAdapter extends RecyclerView.Adapter<TypeMomentAdapter.TypeMomentHolder> {
    private Context mContext;
    private List<Filter> filterList;
    private int index = 0;

    public TypeMomentAdapter(Context mContext, List<Filter> filterList) {
        this.mContext = mContext;
        this.filterList = filterList;
    }

    @NonNull
    @Override
    public TypeMomentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_moment, parent, false);
        return new TypeMomentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeMomentHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return filterList == null ? 0 : filterList.size();
    }

    public class TypeMomentHolder extends BaseHolder {
        private ImageView ivSelect;
        private TextView tvType;

        public TypeMomentHolder(View itemView) {
            super(itemView);
            ivSelect = itemView.findViewById(R.id.ivSelect);
            tvType = itemView.findViewById(R.id.tvType);
        }

        @Override
        public void bindItem(final int position) {
            super.bindItem(position);
            Filter filter = filterList.get(position);
            if (index == position) {
                ivSelect.setImageResource(R.drawable.vt_checked);
            } else {
                ivSelect.setImageResource(R.drawable.vt_check);
            }
            tvType.setText(filter.getLabel());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = position;
                    notifyDataSetChanged();
                }
            });

        }
    }

    public Filter getItemSelect() {
        return filterList.get(index);
    }

}
