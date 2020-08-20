package com.namviet.vtvtravel.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.namviet.vtvtravel.listener.ItemVideoClickListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ItemViewVideoBinding;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.viewmodel.ItemChildTravelViewModel;

import java.util.Collections;
import java.util.List;

public class ViewVideoApdater extends RecyclerView.Adapter<ViewVideoApdater.MyViewHolder> {


    private LayoutInflater layoutInflater;
    private List<Travel> travelList;
    private ItemVideoClickListener listener;

    public ViewVideoApdater(ItemVideoClickListener listener) {
        travelList = Collections.emptyList();
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemViewVideoBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_view_video, parent, false);


        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindVideo(travelList.get(position));
    }

    @Override
    public int getItemCount() {
        return travelList == null ? 0 : travelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemViewVideoBinding binding;

        public MyViewHolder(ItemViewVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindVideo(final Travel travel) {
            if (binding.getTravelModel() == null) {
                binding.setTravelModel(new ItemChildTravelViewModel(itemView.getContext(), travel));
            } else {
                binding.getTravelModel().setTravel(travel);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != listener) {
                        listener.onItemClick(travel);
                    }
                }
            });
            this.binding.setVariable(BR.itemTravel, binding.getTravelModel());

        }
    }

    public void setTravelList(List<Travel> travelList) {
        this.travelList = travelList;
        notifyDataSetChanged();
    }
}
