package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.model.Block;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.Item;

import java.util.ArrayList;
import java.util.List;

public class F3Header2Adapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private ArrayList<Block> items = new ArrayList<>();
    private Context context;
    public int selectedItem = 0;
    private F3Header2Adapter.ClickTab clickTab;
    private boolean isHeader1;
    private int oldPosition = 0;

    private ArrayList<HeaderViewHolder> headerViewHolders = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    public F3Header2Adapter2(int position, ArrayList<Block> items, Context context, F3Header2Adapter.ClickTab clickTab, boolean isHeader1) {
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_header2, parent, false);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(v);
            headerViewHolders.add(headerViewHolder);
            return headerViewHolder;
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
                    //Fix m???t n???a nh??ng l???i ???????c c???
                    headerViewHolders.get(selectedItem).bindItem(selectedItem);
//                    notifyItemChanged(selectedItem);
                }
            });

        }

        public void bindItem(int position) {
//            if (isHeader1) {
//                if (position == 1 || position == 4) {
//                    imgSelected.setVisibility(View.VISIBLE);
//                } else imgSelected.setVisibility(View.INVISIBLE);
//            } else {
//                imgSelected.setVisibility(View.INVISIBLE);
//            }


            this.position = position;
            if (position == selectedItem) {
                Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_bold);
                tvTitle.setTextColor(Color.parseColor("#FF2929"));
                tvTitle.setTypeface(typeface);
                viewIndicator.setVisibility(View.VISIBLE);
                oldPosition = position;
            } else {
                Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_medium);
                tvTitle.setTextColor(Color.parseColor("#7A7A7A"));
                tvTitle.setTypeface(typeface);
                viewIndicator.setVisibility(View.INVISIBLE);

            }
            tvTitle.setText(items.get(position).getName());
        }
    }

}
