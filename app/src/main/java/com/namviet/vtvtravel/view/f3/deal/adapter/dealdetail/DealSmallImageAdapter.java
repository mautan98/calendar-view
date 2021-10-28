package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;

public class DealSmallImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;


    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    public DealSmallImageAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(context).inflate(R.layout.item_small_image_deal, parent, false);
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
//        try {
//            return items.size();
//        } catch (Exception e) {
//            return 0;
//        }
        return 10;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    notifyDataSetChanged();
                }
            });

        }

        public void bindItem(int position) {
        }
    }


    public interface ClickTab {
        void onClickTab(int position);
    }


}
