package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.view.f3.deal.model.Block;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.Item;
import com.ornach.richtext.RichText;
import com.ornach.richtext.RichView;

import java.util.ArrayList;
import java.util.List;

public class F3TabDealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private ArrayList<Block> items = new ArrayList<>();
    private Context context;
    public int selectedItem = 0;
    private ClickTab clickTab;
    private boolean isFromHome = true;

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    public F3TabDealAdapter(int position, ArrayList<Block> items, Context context, ClickTab clickTab, boolean isFromHome) {
        this.items = items;
        this.context = context;
        this.clickTab = clickTab;
        this.selectedItem = position;
        this.isFromHome = isFromHome;
    }
    public F3TabDealAdapter(int position, ArrayList<Block> items, Context context, ClickTab clickTab) {
        this.items = items;
        this.context = context;
        this.clickTab = clickTab;
        this.selectedItem = position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_tab_deal_home, parent, false);
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
            return items.get(0).getListChildBlock().size() - 1;
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private RichText imgSelected;
        private TextView tvTitle;
        private int position;
        public HeaderViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            imgSelected = itemView.findViewById(R.id.v_selected);

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
            this.position = position;
              if(!isFromHome){
                    imgSelected.setBackgroundColor(Color.parseColor("#F93134"));
                }
            if (position == selectedItem) {
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
                imgSelected.setVisibility(View.VISIBLE);
            } else {
                tvTitle.setTextColor(Color.parseColor("#707070"));
                imgSelected.setVisibility(View.GONE);
            }
            tvTitle.setText(items.get(0).getListChildBlock().get(position).getName());
        }
    }


    public interface ClickTab{
        void onClickTab(int position);
    }


}
