package com.namviet.vtvtravel.adapter;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ItemMenuHomeBinding;
import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.viewmodel.ItemMenuViewModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.HomeMenuHolder> {
    private List<ItemMenu> menuList;

    public HomeMenuAdapter() {
        this.menuList = Collections.emptyList();
    }

    @Override
    public HomeMenuAdapter.HomeMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMenuHomeBinding itemMenu =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_menu_home,
                        parent, false);
        return new HomeMenuHolder(itemMenu);
    }

    @Override
    public void onBindViewHolder(HomeMenuAdapter.HomeMenuHolder holder, int position) {
        holder.bindMenu(menuList.get(position));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class HomeMenuHolder extends RecyclerView.ViewHolder {

        ItemMenuHomeBinding itemMenuBinding;

        public HomeMenuHolder(ItemMenuHomeBinding itemMenuBinding) {
            super(itemMenuBinding.itemMenu);
            this.itemMenuBinding = itemMenuBinding;
        }

        void bindMenu(ItemMenu itemMenu) {
            if (itemMenuBinding.getMenuViewModel() == null) {
                itemMenuBinding.setMenuViewModel(new ItemMenuViewModel(itemView.getContext(), itemMenu));
            } else {
                itemMenuBinding.getMenuViewModel().setItemMenu(itemMenu);
            }
        }
    }

    public void setMenuList(List<ItemMenu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }
}
