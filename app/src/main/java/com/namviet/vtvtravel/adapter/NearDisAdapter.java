package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.daimajia.slider.library.NearDistance;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ItemNearDisBinding;
import com.namviet.vtvtravel.listener.ItemChangeListener;
import com.namviet.vtvtravel.viewmodel.ItemNearDisViewModel;

import java.util.List;

public class NearDisAdapter extends RecyclerView.Adapter<NearDisAdapter.NearDisHolder> {
    private Context mContext;
    private List<NearDistance> nearDistanceList;
    private ItemChangeListener listener;
    private int index = 0;

    public void setListener(ItemChangeListener listener) {
        this.listener = listener;
    }

    public NearDisAdapter(Context mContext, List<NearDistance> nearDistanceList) {
        this.mContext = mContext;
        this.nearDistanceList = nearDistanceList;
    }

    @NonNull
    @Override
    public NearDisHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNearDisBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_near_dis,
                parent, false);
        return new NearDisHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NearDisHolder holder, int position) {
        holder.bindNear(position);
    }

    @Override
    public int getItemCount() {
        return nearDistanceList == null ? 0 : nearDistanceList.size();
    }

    public class NearDisHolder extends RecyclerView.ViewHolder {
        private ItemNearDisBinding binding;

        public NearDisHolder(ItemNearDisBinding binding) {
            super(binding.itemView);
            this.binding = binding;

        }

        public void bindNear(final int position) {
            NearDistance nearDistance = nearDistanceList.get(position);
            if (index == position) {
                nearDistance.setChecked(true);
            } else {
                nearDistance.setChecked(false);
            }
            binding.ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(index!=position){
                        index = position;
                        notifyDataSetChanged();
                        if(listener!=null){
                            listener.onChange();
                        }
                    }

                }
            });

            if (binding.getItemNearDis() == null) {
                binding.setItemNearDis(new ItemNearDisViewModel(itemView.getContext(), nearDistance));
            } else {
                binding.getItemNearDis().setNearDistance(nearDistance);
            }
            this.binding.setVariable(BR.itemNearDis, binding.getItemNearDis());

        }
    }

    public int getIndexDis() {
        return index;
    }
}


