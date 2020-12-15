package com.namviet.vtvtravel.adapter.imagepart;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.f2imagepart.ImagePart;
import com.namviet.vtvtravel.model.travelnews.Travel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ImagePartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_1 = 1;
    private Context context;
    private ClickItem clickItem;

    private List<ImagePart> images;

    public ImagePartAdapter(List<ImagePart> images, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.images = images;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position % 2) {
            case 1:
                return TYPE_ITEM_1;
            case 0:
                return TYPE_ITEM;
        }
        return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_image_flix_right, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM_1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_image_flix_left, parent, false);
            return new HeaderViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_ITEM_1) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return images.size();
        } catch (Exception e) {
            return 10;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        List<ImageView> imageViews = new ArrayList<>();
        private ImageView img1;
        private ImageView img3;
        private ImageView img4;
        private ImageView img2;
        private ImageView img5;
        private ImageView img6;
        private ImageView img7;
        private ImageView img8;
        private ImageView img9;
        private LinearLayout layoutThree1;
        private LinearLayout layoutThree2;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            img1 = itemView.findViewById(R.id.img1);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            img2 = itemView.findViewById(R.id.img2);
            img5 = itemView.findViewById(R.id.img5);
            img6 = itemView.findViewById(R.id.img6);
            img7 = itemView.findViewById(R.id.img7);
            img8 = itemView.findViewById(R.id.img8);
            img9 = itemView.findViewById(R.id.img9);
            layoutThree1 = itemView.findViewById(R.id.layoutThree1);
            layoutThree2 = itemView.findViewById(R.id.layoutThree2);
            imageViews.add(img1);
            imageViews.add(img2);
            imageViews.add(img3);
            imageViews.add(img4);
            imageViews.add(img5);
            imageViews.add(img6);
            imageViews.add(img7);
            imageViews.add(img8);
            imageViews.add(img9);

            for (int i = 0; i < imageViews.size(); i++) {
                int finalI = i;
                imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            clickItem.onClickItem(position * 9 + finalI);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        public void bindItem(int position) {
            this.position = position;


            List<Travel> items = images.get(position).getItems();
            for (int i = 0; i < items.size(); i++) {
                Glide.with(context).load(items.get(i).getLogo_url()).into(imageViews.get(i));
            }

            int size = items.size();
            if(size >6){
                layoutThree1.setVisibility(View.VISIBLE);
                layoutThree2.setVisibility(View.VISIBLE);
            }else if(size > 3) {
                layoutThree1.setVisibility(View.VISIBLE);
                layoutThree2.setVisibility(View.GONE);
            }else {
                layoutThree1.setVisibility(View.GONE);
                layoutThree2.setVisibility(View.GONE);
            }
        }
    }


    public interface ClickItem {
        void onClickItem(int position);
    }


}
