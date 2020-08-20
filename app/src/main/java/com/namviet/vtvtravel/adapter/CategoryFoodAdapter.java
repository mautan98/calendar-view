package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.FoodCate;

import java.util.List;

public class CategoryFoodAdapter extends RecyclerView.Adapter<CategoryFoodAdapter.CategoryFoodHolder> {
    private Context mContext;
    private List<FoodCate> foodCateList;

    public CategoryFoodAdapter(Context mContext, List<FoodCate> foodCateList) {
        this.mContext = mContext;
        this.foodCateList = foodCateList;
    }

    public CategoryFoodAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategoryFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_category_food, parent, false);
        return new CategoryFoodHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryFoodHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return foodCateList == null ? 0 : foodCateList.size();
    }

    public class CategoryFoodHolder extends BaseHolder {
        private ImageView ivItem;
        private TextView tvItem;

        public CategoryFoodHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvItem = itemView.findViewById(R.id.tvItem);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            FoodCate foodCate = foodCateList.get(position);
            setImageUrl(foodCate.getLogo_url(), ivItem);
            tvItem.setText(foodCate.getName());
        }
    }
}
