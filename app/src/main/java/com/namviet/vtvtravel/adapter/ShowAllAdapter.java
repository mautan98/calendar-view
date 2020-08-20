package com.namviet.vtvtravel.adapter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.ItemShowAllBinding;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.Collections;
import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ShowAllHolder> {


    private List<Travel> travelList;

    public ShowAllAdapter() {
        this.travelList = Collections.emptyList();
    }

    @Override
    public ShowAllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemShowAllBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_show_all,
                parent, false);
        return new ShowAllHolder(binding);
    }

    @Override
    public void onBindViewHolder(ShowAllHolder holder, int position) {
        holder.bindTravel(travelList.get(position));
    }

    @Override
    public int getItemCount() {
        return travelList == null ? 0 : travelList.size();
    }

    public class ShowAllHolder extends RecyclerView.ViewHolder {
        private ItemShowAllBinding binding;

        public ShowAllHolder(ItemShowAllBinding binding) {
            super(binding.itemView);
            this.binding = binding;
        }

        public void bindTravel(final Travel travel) {
//            if (binding.getTravelModel() == null) {
//                binding.setTravelModel(new ItemChildTravelViewModel(itemView.getContext(), travel));
//            } else {
//                binding.getTravelModel().setTravel(travel);
//            }
//            this.binding.setVariable(BR.itemTravel, binding.getTravelModel());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity mainActivity = (MainActivity) itemView.getContext();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.IntentKey.KEY_ITEM, travel);
                    mainActivity.setBundle(bundle);
                    if (travel.getType().equals(Constants.TypeMenu.FOOD_TRAVEL) || travel.getType().equals(Constants.TypeMenu.PLACE_TRAVEL) || travel.getType().equals(Constants.TypeMenu.WEATHER_TRAVEL) || travel.getType().equals(Constants.TypeMenu.VIDEO)) {
//                        mainActivity.switchFragment(SlideMenu.MenuType.VIDEO_VIEW_SCREEN);
                    } else {
                        mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_SCREEN);
                    }
                }
            });
            Glide.with(itemView.getContext()).load(travel.getLogo_url()).into(binding.ivPhoto);
            binding.tvTitle.setText(travel.getName());
            if (travel.getAddress() == null) {
                binding.tvAddress.setVisibility(View.GONE);
            } else {
                binding.tvAddress.setText(travel.getAddress());
            }
            if (travel.getCreated() == null) {
                binding.tvTime.setVisibility(View.GONE);
            } else {
                binding.tvTime.setText(DateUtltils.timeToString(travel.getCreated()));
            }
            if (travel.getView_count() == null) {
                binding.tvViewed.setVisibility(View.GONE);
            } else {
                binding.tvViewed.setText("" + travel.getView_count());
            }

//            if (travel.getDistance() == null) {
//                binding.tvDistance.setVisibility(View.GONE);
//            } else {
//                binding.tvDistance.setText("" + travel.getDistance().getLabel());
//            }

        }
    }

    public void setTravelList(List<Travel> travelList) {
        this.travelList = travelList;
        notifyDataSetChanged();
    }
}
