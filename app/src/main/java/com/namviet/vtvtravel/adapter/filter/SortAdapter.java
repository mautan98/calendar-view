package com.namviet.vtvtravel.adapter.filter;

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
import com.namviet.vtvtravel.response.f2smalllocation.SortSmallLocationResponse;

import java.util.List;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {
    private Context context;
    private List<SortSmallLocationResponse.Data.Item> sortList;
    private ClickButton clickButton;

    public void setClickButton(ClickButton clickButton) {
        this.clickButton = clickButton;
    }

    public SortAdapter(Context context, List<SortSmallLocationResponse.Data.Item> sortList) {
        this.context = context;
        this.sortList = sortList;
    }

    @NonNull
    @Override
    public SortAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_sort, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return sortList != null ? sortList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView imgCheck;
        private LinearLayout linearSort;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            linearSort = itemView.findViewById(R.id.linearSort);
        }

        public void onBind(int position) {
            tvName.setText(sortList.get(position).getText());
            if (sortList.get(position).isChecked()) {
                imgCheck.setVisibility(View.VISIBLE);
            } else {
                imgCheck.setVisibility(View.GONE);
            }

            linearSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < sortList.size(); i++) {
                        sortList.get(i).setChecked(false);
                    }
                    sortList.get(position).setChecked(true);
                    notifyDataSetChanged();
                    clickButton.onClickItem(position);
                }
            });
        }
    }

    public interface ClickButton{
        public void onClickItem(int position);
    }
}
