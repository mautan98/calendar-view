package com.namviet.vtvtravel.adapter.imagepart;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.f2imagepart.ImagePart;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.imagepart.ImagePartResponse;
import com.namviet.vtvtravel.response.imagepart.ItemImagePartResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.ViewMoreUtils;
import com.namviet.vtvtravel.view.f2.CommentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HighLightestImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;

    private List<ItemImagePartResponse.Data.Item> images;

    public HighLightestImagesAdapter(List<ItemImagePartResponse.Data.Item> images, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.images = images;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_gallery, parent, false);
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
            return images.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvScrollPosition;
        private TextView tvDate;
        private TextView tvAuthor;
        private TextView tvView;
        private ViewPager vpGallery;
        private TextView tvCommentCount;
        private ImageView btnShare;
        private ImageView imgComment;
        private ReadMoreTextView tvDescription;
        private TextView tvReadMore;
        private int position;
        private SlideImageInHighLightestImageAdapter slideImageInHighLightestImageAdapter;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvScrollPosition = itemView.findViewById(R.id.tvScrollPosition);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvView = itemView.findViewById(R.id.tvView);
            vpGallery = itemView.findViewById(R.id.vpGallery);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            btnShare = itemView.findViewById(R.id.btnShare);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            imgComment = itemView.findViewById(R.id.imgComment);
        }

        public void bindItem(int position) {
            this.position = position;
            ItemImagePartResponse.Data.Item item = images.get(position);
            tvName.setText(item.getName());
            tvAuthor.setText(item.getAuthor());

            tvDescription.setText(item.getContent());
//            if (!item.isView_more()){
//                ViewMoreUtils.makeTextViewResizable(tvDescription, 2, "Xem thêm", true, "#000000");
//            }
            tvCommentCount.setText(item.getCount_comment());
            tvView.setText(item.getView_count());
            tvDate.setText("" + DateUtltils.timeToString(Long.valueOf(item.getCreated())));


            slideImageInHighLightestImageAdapter = new SlideImageInHighLightestImageAdapter(context, item.getThumb_url(), SlideImageInHighLightestImageAdapter.LIST_TYPE);
            vpGallery.setAdapter(slideImageInHighLightestImageAdapter);
            try {
                tvScrollPosition.setText("1/" + item.getThumb_url().size());
            } catch (Exception e) {
                tvScrollPosition.setText("0/0");
            }

            imgComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailTravelNewsResponse detailTravelNewsResponse = new DetailTravelNewsResponse();
                    DetailTravelNewsResponse.Data data = new DetailTravelNewsResponse().new Data();
                    data.setId(item.getId());
                    data.setContent_type(item.getContent_type());
                    detailTravelNewsResponse.setData(data);
                    CommentActivity.startScreen((Activity) context, detailTravelNewsResponse, null);
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    F2Util.startSenDataText((Activity) context, item.getLink_share());
                }
            });

            vpGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    try {
                        tvScrollPosition.setText((String.valueOf(position + 1) + "/" + item.getThumb_url().size()));
                    } catch (Exception e) {
                        tvScrollPosition.setText("0/0");
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }


    public interface ClickItem {
        void onClickItem(int position);
    }


}
