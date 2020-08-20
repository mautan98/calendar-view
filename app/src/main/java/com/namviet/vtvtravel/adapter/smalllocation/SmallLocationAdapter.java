package com.namviet.vtvtravel.adapter.smalllocation;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.comment.SubCommentAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;

public class SmallLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;

    private List<Travel> items;

    public SmallLocationAdapter(List<Travel> items, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.items = items;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_eating, parent, false);
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
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvRating;
        private TextView tvRatingText;
        private TextView tvCommentCount;
        private TextView tvDistance;
        private TextView tvAddress;
        private TextView tvOpenDate;
        private TextView tvOpenTime;
        private TextView tvOpenState;
        private TextView tvPrice;

        private LinearLayout layoutOpen;
        private LinearLayout layoutPrice;

        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvRatingText = itemView.findViewById(R.id.tvRatingText);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
            tvOpenState = itemView.findViewById(R.id.tvOpenState);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            layoutOpen = itemView.findViewById(R.id.layoutOpen);
            layoutPrice = itemView.findViewById(R.id.layoutPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(items.get(position));
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;

            Travel travel = items.get(position);

            tvName.setText(travel.getName());
            tvDescription.setText(travel.getShort_description());
            Glide.with(context).load(travel.getLogo_url()).into(imgAvatar);
            tvCommentCount.setText(travel.getComment_count());
            tvAddress.setText(travel.getAddress());
            tvRating.setText(travel.getEvaluate());
            tvRatingText.setText(travel.getEvaluate_text());

            if (Constants.TypeDestination.RESTAURANTS.equals(travel.getContent_type()) || Constants.TypeDestination.HOTELS.equals(travel.getContent_type())) {
                layoutPrice.setVisibility(View.VISIBLE);
                layoutOpen.setVisibility(View.GONE);
                tvPrice.setText(travel.getPrice_from() + " đ" + " - " + travel.getPrice_to() + " đ");
            } else {
                layoutPrice.setVisibility(View.GONE);
                layoutOpen.setVisibility(View.VISIBLE);
                tvOpenDate.setText(travel.getOpen_week());
                tvOpenTime.setText(travel.getRange_time());
                tvOpenState.setText(travel.getType_open());

                if ("Đang đóng".equals(travel.getType_open())) {
                    tvOpenState.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    tvOpenState.setTextColor(Color.parseColor("#0FB403"));
                }
            }

            if (travel.getDistance() != null && !"".equals(travel.getDistance()) && Double.parseDouble(travel.getDistance()) < 1000) {
                tvDistance.setText("Cách bạn " + travel.getDistance() + " m");
            } else if (travel.getDistance() != null && !"".equals(travel.getDistance())) {
                double finalValue = Math.round(Double.parseDouble(travel.getDistance()) / 1000 * 10.0) / 10.0;
                tvDistance.setText("Cách bạn " + finalValue + " km");
            }
        }
    }

    public interface ClickItem {
        void onClickItem(Travel travel);
    }

}
