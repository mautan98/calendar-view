package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.Filter;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder> {

    private Context mContext;
    private ArrayList<Filter> listFilter;
    private ArrayList<Filter> listSelected;

    public FilterAdapter(Context mContext, ArrayList<Filter> listFilter) {
        this.mContext = mContext;
        this.listFilter = listFilter;
        listSelected = new ArrayList<>();
    }



    @Override
    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_filter, parent, false);
        return new FilterHolder(v);
    }

    @Override
    public void onBindViewHolder(FilterHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return listFilter == null ? 0 : listFilter.size();
    }

    public class FilterHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivSelect;

        public FilterHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivSelect = itemView.findViewById(R.id.ivSelect);
        }

        public void bindItem(final int position) {
            final Filter filter = listFilter.get(position);
            if (filter.isSelected()) {
                ivSelect.setImageResource(R.drawable.vt_square_checked);
                listSelected.add(listFilter.get(position));
            } else {
                ivSelect.setImageResource(R.drawable.vt_square_check);
                listSelected.remove(listFilter.get(position));
            }
            tvName.setText(filter.getLabel());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (filter.isSelected()) {
                        listFilter.get(position).setSelected(false);
                    } else {
                        listFilter.get(position).setSelected(true);
                    }
                    notifyDataSetChanged();
                }
            });


        }
    }

    public ArrayList<Filter> getListSelected() {
        return listFilter;
    }
}
