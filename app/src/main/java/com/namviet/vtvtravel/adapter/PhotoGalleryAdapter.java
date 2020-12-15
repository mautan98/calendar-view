package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.PhotoSelectListener;

import java.util.ArrayList;

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryAdapter.PhotoGalleryHolder> {
    private Context mContext;
    private boolean isDetails;
    private ArrayList<String> listPhoto;
    private PhotoSelectListener photoSelectListener;

    public void setPhotoSelectListener(PhotoSelectListener photoSelectListener) {
        this.photoSelectListener = photoSelectListener;
    }

    public PhotoGalleryAdapter(Context mContext, boolean isDetails) {
        this.mContext = mContext;
        this.isDetails = isDetails;
    }

    public PhotoGalleryAdapter(Context mContext, boolean isDetails, ArrayList<String> listPhoto) {
        this.mContext = mContext;
        this.isDetails = isDetails;
        this.listPhoto = listPhoto;
    }

    @NonNull
    @Override
    public PhotoGalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_photo_gallery, parent, false);
        return new PhotoGalleryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoGalleryHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        if (isDetails) {
            return listPhoto == null ? 0 : listPhoto.size();
        } else {
            return 20;
        }

    }

    public class PhotoGalleryHolder extends BaseHolder {
        private ImageView ivItem;

        public PhotoGalleryHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            if (null != listPhoto) {
                String photo = listPhoto.get(position);
                setImageUrl(photo, ivItem);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != photoSelectListener) {
                        photoSelectListener.onSelect();
                    }
                }
            });
        }
    }

    public ArrayList<String> getListPhoto() {
        return listPhoto;
    }
}
