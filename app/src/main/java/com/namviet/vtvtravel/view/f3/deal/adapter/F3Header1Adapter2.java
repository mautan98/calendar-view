package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.Item;

import java.util.ArrayList;
import java.util.List;

public class F3Header1Adapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<Item> items = new ArrayList<>();
    private Context context;
    public int selectedItem = 0;
    private F3Header1Adapter.ClickTab clickTab;
    private boolean isHeader1;
    private boolean isFake;

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    public F3Header1Adapter2(int position, List<Item> items, Context context, F3Header1Adapter.ClickTab clickTab, boolean isHeader1) {
        this.items = items;
        this.context = context;
        this.clickTab = clickTab;
        this.selectedItem = position;
        this.isHeader1 = isHeader1;
    }


    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_header1, parent, false);
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
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void setFake(boolean fake) {
        isFake = fake;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private View imgSelected;
        private View viewIndicator;
        private TextView tvTitle;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            imgSelected = itemView.findViewById(R.id.v_selected);
            viewIndicator = itemView.findViewById(R.id.v_indicator);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = position;
                    clickTab.onClickTab(selectedItem);
                    notifyDataSetChanged();
                }
            });

        }

        public void bindItem(int position) {
            if (isHeader1) {
                if (position == 1 || position == 4) {
                    imgSelected.setVisibility(View.VISIBLE);
                } else imgSelected.setVisibility(View.INVISIBLE);
            } else {
                imgSelected.setVisibility(View.INVISIBLE);
            }


            this.position = position;
            if (position == selectedItem) {
                tvTitle.setTextColor(Color.parseColor("#FF2929"));
                viewIndicator.setVisibility(View.VISIBLE);
            } else {
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
                viewIndicator.setVisibility(View.INVISIBLE);

            }
            tvTitle.setText(items.get(position).getTitle());
        }
    }



}
