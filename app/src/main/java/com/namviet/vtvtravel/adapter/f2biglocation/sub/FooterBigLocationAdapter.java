package com.namviet.vtvtravel.adapter.f2biglocation.sub;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.DetailBigLocationAdapter;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

import java.util.List;

public class FooterBigLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private String code;
    private List<Travel> items;

    public FooterBigLocationAdapter(List<Travel> items, Context context, String code, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.items = items;
        this.code = code;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_big_location_main_footer, parent, false);
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
        private int position;
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvRate;
        private TextView tvCommentCount;
        private TextView tvPrice;
        private TextView tvAddress;
        private TextView tvDistance;
        private TextView tvOpenDate;
        private TextView tvOpenState;
        private TextView tvEvaluateText;
        private LinearLayout layoutOpen;
        private LinearLayout layoutPrice;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvEvaluateText = itemView.findViewById(R.id.tvEvaluateText);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            tvOpenState = itemView.findViewById(R.id.tvOpenState);

            layoutOpen = itemView.findViewById(R.id.layoutOpen);
            layoutPrice = itemView.findViewById(R.id.layoutPrice);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SmallLocationActivity.startScreenDetail((Activity) context, SmallLocationActivity.OpenType.DETAIL, items.get(position).getDetail_link());
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;

            Travel travel = items.get(position);

            Glide.with(context).load(travel.getLogo_url()).into(imgAvatar);
            tvName.setText(travel.getName());
            tvRate.setText(travel.getEvaluate());
            tvEvaluateText.setText(travel.getEvaluate_text());
            tvCommentCount.setText(travel.getComment_count());
            tvAddress.setText(travel.getAddress());

            if (code.equals(DetailBigLocationAdapter.TypeString.APP_TOP_RESTAURANT) || code.equals(DetailBigLocationAdapter.TypeString.APP_TOP_HOTEL)) {
                layoutPrice.setVisibility(View.VISIBLE);
                layoutOpen.setVisibility(View.GONE);
                tvPrice.setText(travel.getPrice_from() + " đ" + " - " + travel.getPrice_to() + " đ");
            } else {
                layoutPrice.setVisibility(View.GONE);
                layoutOpen.setVisibility(View.VISIBLE);
                tvOpenState.setText(travel.getType_open());
                tvOpenDate.setText(travel.getOpen_week());

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
