package com.namviet.vtvtravel.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ItemTravelBinding;
import com.namviet.vtvtravel.model.ItemTravel;
import com.namviet.vtvtravel.viewmodel.ItemTravelViewModel;

import java.util.Collections;
import java.util.List;

public class ItemTravelAdapter extends RecyclerView.Adapter<ItemTravelAdapter.ItemTravelHolder> {
    private List<ItemTravel> travelList;

    public ItemTravelAdapter() {
        this.travelList = Collections.emptyList();
    }

    @Override
    public ItemTravelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTravelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_travel,
                parent, false);
        return new ItemTravelHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemTravelHolder holder, int position) {
        if (travelList.get(position).getItems() != null) {
            holder.bindSearch(travelList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return travelList == null ? 0 : travelList.size();
    }

    public class ItemTravelHolder extends RecyclerView.ViewHolder {
        private ItemTravelBinding binding;

        public ItemTravelHolder(ItemTravelBinding binding) {
            super(binding.itemViews);
            this.binding = binding;
        }

        public void bindSearch(ItemTravel itemTravel) {
            if (binding.getItemTravel() == null) {
                binding.setItemTravel(new ItemTravelViewModel(itemView.getContext(), itemTravel));
            } else {
                binding.getItemTravel().setItemTravel(itemTravel);
            }
            if (null != itemTravel.getItems() && itemTravel.getItems().size() > 0) {
                ItemChildTravelAdapter travelAdapter = new ItemChildTravelAdapter(itemView.getContext(), itemTravel.getName());
                binding.rvItem.setAdapter(travelAdapter);
                travelAdapter.setTravelList(itemTravel.getItems());
                binding.rvItem.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            }

        }
    }

    public void setTravelList(List<ItemTravel> travelList) {
        this.travelList = travelList;
        notifyDataSetChanged();
    }
}
