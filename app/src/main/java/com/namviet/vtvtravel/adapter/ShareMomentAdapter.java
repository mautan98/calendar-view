package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.CustomGallery;

import java.io.File;
import java.util.ArrayList;

public class ShareMomentAdapter extends RecyclerView.Adapter<ShareMomentAdapter.ShareMomentHolder> {
    private Context mContext;
    private ArrayList<CustomGallery> photoList;

    public ShareMomentAdapter(Context mContext, ArrayList<CustomGallery> photoList) {
        this.mContext = mContext;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public ShareMomentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_share_moment, parent, false);
        return new ShareMomentHolder(view, new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull ShareMomentHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return photoList == null ? 0 : photoList.size();
    }

    public class ShareMomentHolder extends BaseHolder {
        private ImageView ivPhoto;
        private EditText edAbout;
        private ImageView ivRemove;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ShareMomentHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            this.myCustomEditTextListener = myCustomEditTextListener;
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            edAbout = itemView.findViewById(R.id.edAbout);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            edAbout.addTextChangedListener(myCustomEditTextListener);
        }

        @Override
        public void bindItem(final int position) {
            super.bindItem(position);
            CustomGallery customGallery = photoList.get(position);
            setImage(customGallery.getSdcardPath(), ivPhoto);
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photoList.remove(position);
                    notifyDataSetChanged();
                }
            });
            myCustomEditTextListener.updatePosition(position);

        }

        public void setImage(String url, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(new File(url)).thumbnail(0.2f).into(image);
        }
    }

    public ArrayList<CustomGallery> getPhotoList() {
        return photoList;
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            photoList.get(position).setAboutPhoto(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}
