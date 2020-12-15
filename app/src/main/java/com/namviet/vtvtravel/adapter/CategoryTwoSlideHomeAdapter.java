package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.ItemGroup;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.TravelSelectListener;

import java.util.List;

public class CategoryTwoSlideHomeAdapter extends RecyclerView.Adapter<CategoryTwoSlideHomeAdapter.ItemTwoHolder> {

    private Context mContext;
    private List<ItemGroup> groupList;
    private TravelSelectListener travelSelectListener;

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }

    public CategoryTwoSlideHomeAdapter(Context mContext, List<ItemGroup> groupList) {
        this.mContext = mContext;
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public ItemTwoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vp_type_two_item, parent, false);
        return new ItemTwoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTwoHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return groupList == null ? 0 : groupList.size();
    }


    public class ItemTwoHolder extends BaseHolder {
        private LinearLayout llItem1;
        private LinearLayout llItem2;

        private ImageView ivItem1;
        private TextView tvItem1;

        private ImageView ivItem2;
        private TextView tvItem2;

        public ItemTwoHolder(View itemView) {
            super(itemView);
            llItem1 = itemView.findViewById(R.id.llItem2);
            llItem2 = itemView.findViewById(R.id.llItem3);

            ivItem1 = itemView.findViewById(R.id.ivItem2);
            tvItem1 = itemView.findViewById(R.id.tvDescription2);

            ivItem2 = itemView.findViewById(R.id.ivItem3);
            tvItem2 = itemView.findViewById(R.id.tvDescription3);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final ItemGroup itemGroup = groupList.get(position);
            if (itemGroup.getGroup().size() > 0) {
                tvItem1.setText(itemGroup.getGroup().get(0).getName());
                setImageUrl(itemGroup.getGroup().get(0).getLogo_url(), ivItem1);
                llItem1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != travelSelectListener) {
                            travelSelectListener.onSelectTravel(itemGroup.getGroup().get(0));
                        }
                    }
                });
            }

            if (itemGroup.getGroup().size() > 1) {
                tvItem2.setText(itemGroup.getGroup().get(1).getName());
                setImageUrl(itemGroup.getGroup().get(1).getLogo_url(), ivItem2);
                llItem2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != travelSelectListener) {
                            travelSelectListener.onSelectTravel(itemGroup.getGroup().get(1));
                        }
                    }
                });
            }
        }
    }
}
