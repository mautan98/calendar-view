package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

public class CategoryThreeSlideHomeAdapter extends RecyclerView.Adapter<CategoryThreeSlideHomeAdapter.ItemThreeHolder> {

    private Context mContext;
    private List<ItemGroup> groupList;
    private TravelSelectListener travelSelectListener;

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }

    public CategoryThreeSlideHomeAdapter(Context mContext, List<ItemGroup> groupList) {
        this.mContext = mContext;
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public ItemThreeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.vp_type_three_item, parent, false);
        return new ItemThreeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemThreeHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return groupList == null ? 0 : groupList.size();
    }

    public class ItemThreeHolder extends BaseHolder {
        private CardView cvItem1;
        private LinearLayout llItem2;
        private LinearLayout llItem3;
        private ImageView ivItem1;
        private TextView tvItem1;

        private ImageView ivItem2;
        private TextView tvItem2;

        private ImageView ivItem3;
        private TextView tvItem3;

        public ItemThreeHolder(View itemView) {
            super(itemView);
            cvItem1 = itemView.findViewById(R.id.cvItem1);
            llItem2 = itemView.findViewById(R.id.llItem2);
            llItem3 = itemView.findViewById(R.id.llItem3);

            ivItem1 = itemView.findViewById(R.id.ivItem1);
            tvItem1 = itemView.findViewById(R.id.tvDescription1);

            ivItem2 = itemView.findViewById(R.id.ivItem2);
            tvItem2 = itemView.findViewById(R.id.tvDescription2);

            ivItem3 = itemView.findViewById(R.id.ivItem3);
            tvItem3 = itemView.findViewById(R.id.tvDescription3);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final ItemGroup itemGroup = groupList.get(position);
            if (itemGroup.getGroup().size() > 0) {
                tvItem1.setText(itemGroup.getGroup().get(0).getName());
                setImageUrl(itemGroup.getGroup().get(0).getLogo_url(), ivItem1);
                cvItem1.setOnClickListener(new View.OnClickListener() {
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

            if (itemGroup.getGroup().size() > 2) {
                tvItem3.setText(itemGroup.getGroup().get(2).getName());
                setImageUrl(itemGroup.getGroup().get(2).getLogo_url(), ivItem3);
                llItem3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != travelSelectListener) {
                            travelSelectListener.onSelectTravel(itemGroup.getGroup().get(2));
                        }
                    }
                });
            }
        }
    }
}
