package com.namviet.vtvtravel.adapter.f2travelvoucher;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse;

import java.util.List;

public class CategoryFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private int oldSelected = 1000;
    private List<CategoryVoucherResponse.Category> categories;

    public CategoryFilterAdapter(List<CategoryVoucherResponse.Category> categories, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.categories = categories;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_travel_voucher_category, parent, false);
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
        try {
            return categories.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private int position;
        private TextView tvName;
        private ImageView imgCheck;
        private LinearLayout linearSort;

        private ImageView imgAvatar;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            linearSort = itemView.findViewById(R.id.linearSort);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }

        public void bindItem(int position) {
            this.position = position;
            tvName.setText(categories.get(position).getName());
            if (categories.get(position).isChecked()) {
                imgCheck.setVisibility(View.VISIBLE);
            } else {
                imgCheck.setVisibility(View.GONE);
            }

            linearSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < categories.size(); i++) {
                        categories.get(i).setChecked(false);
                    }
                    categories.get(position).setChecked(true);
                    notifyDataSetChanged();
                    clickItem.onClickItem(categories.get(position));
                }
            });


            switch (categories.get(position).getId()) {
                case "2":
                    Glide.with(context).load(R.drawable.f2_ic_filter_di_dau).into(imgAvatar);
                    break;
                case "4":
                    Glide.with(context).load(R.drawable.f2_ic_filter_choi_gi).into(imgAvatar);
                    break;
                case "6":
                    Glide.with(context).load(R.drawable.f2_ic_filter_an_gi).into(imgAvatar);
                    break;
                case "8":
                    Glide.with(context).load(R.drawable.f2_ic_filter_o_dau).into(imgAvatar);
                    break;
                default:
                    Glide.with(context).load(R.drawable.f2_ic_filter_all).into(imgAvatar);
            }
        }
    }


    public interface ClickItem {
        void onClickItem(CategoryVoucherResponse.Category category);
    }


}
