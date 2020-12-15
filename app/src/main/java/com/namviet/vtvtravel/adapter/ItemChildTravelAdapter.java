package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ItemChildTravelBinding;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.viewmodel.ItemChildTravelViewModel;

import java.util.Collections;
import java.util.List;

public class ItemChildTravelAdapter extends RecyclerView.Adapter<ItemChildTravelAdapter.TravelHolder> {

    private Context mContext;
    private List<Travel> travelList;
    private String category;

    public ItemChildTravelAdapter(Context mContext, String category) {
        this.mContext = mContext;
        this.category = category;
        travelList = Collections.emptyList();
    }

    @Override
    public TravelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemChildTravelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_child_travel,
                parent, false);
        return new TravelHolder(binding);
    }

    @Override
    public void onBindViewHolder(TravelHolder holder, int position) {
        holder.bindTravel(travelList.get(position));
    }

    @Override
    public int getItemCount() {
        return travelList.size();
    }

    public class TravelHolder extends RecyclerView.ViewHolder {

        private ItemChildTravelBinding binding;

        public TravelHolder(ItemChildTravelBinding binding) {
            super(binding.itemViews);
            this.binding = binding;
        }

        public void bindTravel(Travel travel) {
            if (binding.getTravelModel() == null) {
                binding.setTravelModel(new ItemChildTravelViewModel(itemView.getContext(), travel));
            } else {
                binding.getTravelModel().setTravel(travel);
            }
            binding.getTravelModel().setCategory(category);
        }
    }

    public void setTravelList(List<Travel> travelList) {
        this.travelList = travelList;
        notifyDataSetChanged();
    }
}
