package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f3.commingsoon.view.ComingSoonActivity;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog;

import java.util.ArrayList;
import java.util.List;

public class SubHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<ItemHomeService.Item> items = new ArrayList<>();
    private Context context;

    public SubHeaderAdapter(List<ItemHomeService.Item> items, Context context) {
        this.items = items;
        this.context = context;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_home_header, parent, false);
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
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    try {
//                        OneButtonTitleImageDialog oneButtonTitleImageDialog = new OneButtonTitleImageDialog();
//                        oneButtonTitleImageDialog.show(((MainActivity) context).getSupportFragmentManager(), Constants.TAG_DIALOG);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    try {
                        ComingSoonActivity.Companion.openActivity(context, items.get(getAdapterPosition()).getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void bindItem(int position) {
            try {
                tvTitle.setText(items.get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
