package com.namviet.vtvtravel.adapter.smalllocation;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.travelnews.Travel;

import java.io.File;
import java.net.URI;
import java.util.List;

public class AddImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_ADD = 1;
    private Context context;
    private ClickItem clickItem;

    private List<Bitmap> items;

    public AddImageAdapter(List<Bitmap> items, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (position == items.size()) {
                return TYPE_ITEM_ADD;
            } else {
                return TYPE_ITEM;
            }
        } catch (Exception e) {
            return TYPE_ITEM_ADD;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_write_review_image, parent, false);
            return new HeaderViewHolder(v);
        }else if (viewType == TYPE_ITEM_ADD) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_write_review_add_image, parent, false);
            return new AddImageViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
            if (getItemViewType(position) == TYPE_ITEM_ADD) {
                ((AddImageViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return items.size() + 1;
        } catch (Exception e) {
            return 1;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private ImageView btnDelete;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }

        public void bindItem(int position) {
            this.position = position;
            try {
                Glide.with(context).load(items.get(position)).into(imgAvatar);
            } catch (Exception e) {
                e.printStackTrace();
            }
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onDeleteImage(position);
                }
            });
        }
    }


    public class AddImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private int position;

        public AddImageViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickAddImage();
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
        }
    }


    public interface ClickItem {
        void onClickAddImage();
        void onDeleteImage(int position);
    }


}
