package com.namviet.vtvtravel.adapter.f2travelvoucher;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2travelvoucher.SortClass;

import java.util.List;

public class SortAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private int oldSelected = 1000;
    private List<SortClass.Sort> categories;

    public SortAdapter(List<SortClass.Sort> categories, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.categories = categories;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_sort, parent, false);
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
            return categories.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private int position;
        private TextView tvName;
        private ImageView imgCheck;
        private LinearLayout linearSort;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            linearSort = itemView.findViewById(R.id.linearSort);
        }

        public void bindItem(int position) {
            this.position = position;
            tvName.setText(categories.get(position).getLabel());
            if (categories.get(position).isSelected()) {
                imgCheck.setVisibility(View.VISIBLE);
            } else {
                imgCheck.setVisibility(View.GONE);
            }

            linearSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < categories.size(); i++) {
                        categories.get(i).setSelected(false);
                    }
                    categories.get(position).setSelected(true);
                    notifyDataSetChanged();
                    clickItem.onClickItem(categories.get(position));
                }
            });
        }
    }


    public interface ClickItem {
        void onClickItem(SortClass.Sort sort);
    }


}
