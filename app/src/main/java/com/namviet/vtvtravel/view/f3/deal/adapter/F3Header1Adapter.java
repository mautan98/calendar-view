package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
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

public class F3Header1Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private ArrayList<Block> items = new ArrayList<>();
    private Context context;
    public int selectedItem = 0;
    private ClickTab clickTab;
    private boolean isHeader1;


    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    public F3Header1Adapter(int position, ArrayList<Block> items, Context context, ClickTab clickTab, boolean isHeader1) {
        this.items = items;
        this.context = context;
        this.clickTab = clickTab;
        this.selectedItem = position;
        this.isHeader1 = isHeader1;
    }

    public ClickTab getClickTab() {
        return clickTab;
    }


    public void setClickTab(ClickTab clickTab) {
        this.clickTab = clickTab;
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



    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private View viewIndicator;
        private TextView tvTitle;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
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
            } else {
                Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_medium);
                tvTitle.setTextColor(Color.parseColor("#7A7A7A"));
                tvTitle.setTypeface(typeface);
                viewIndicator.setVisibility(View.INVISIBLE);

            }
            tvTitle.setText(items.get(position).getName());
        }
    }


    public interface ClickTab {
        void onClickTab(int position);
    }


}
