package com.namviet.vtvtravel.adapter.travelnews;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.ornach.richtext.RichText;

import java.util.List;

public class NearByInTravelDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isInTravelNews = false;
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<Travel> travels;

    public NearByInTravelDetailAdapter(Context context, List<Travel> travels, boolean isInTravelNews,  ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.travels = travels;
        this.isInTravelNews = isInTravelNews;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_experience, parent, false);
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
            if(isInTravelNews) {
                if (travels.size() >= 4) {
                    return 4;
                } else {
                    return travels.size();
                }
            }else {
                return travels.size();
            }
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutForRestaurant;
        private LinearLayout linearPriceType;

        private ImageView imgAvatar;
        private ImageView imgType;
        private TextView tvName;
        private TextView tvRate;
        private TextView tvDistance;
        private TextView tvLocationName;
        private TextView tvCommentCount;
        private TextView tvAddress;
        private TextView tvType;
        private TextView tvStatus;
        private TextView tvOpenTime;
        private TextView tvOpenDate;
        private TextView tvPriceRange;
        private TextView tvTime;
        private TextView tvViewCount;
        private TextView tvRateText;
        private int position;
        private View viewTime;
        private LikeButton imgHeart;
        private RichText viewStatus;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            viewTime = itemView.findViewById(R.id.viewTime);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            tvPriceRange = itemView.findViewById(R.id.tvPriceRange);
            tvOpenTime = itemView.findViewById(R.id.tvOpenTime);
            tvOpenDate = itemView.findViewById(R.id.tvOpenDate);
            layoutForRestaurant = itemView.findViewById(R.id.layoutForRestaurant);
            linearPriceType = itemView.findViewById(R.id.linearPriceType);
            imgType = itemView.findViewById(R.id.imgType);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvRateText = itemView.findViewById(R.id.tvRateText);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvType = itemView.findViewById(R.id.tvType);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
            imgHeart = itemView.findViewById(R.id.imgHeart);
            viewStatus = itemView.findViewById(R.id.viewStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(travels.get(position));
                }
            });

        }

        private void clickHeart(){
            try {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    clickItem.likeEvent(position);
                } else {
                    LoginAndRegisterActivityNew.startScreen(context, 0, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void bindItem(int position) {
            this.position = position;
            Travel travel = travels.get(position);

            tvName.setText(travel.getName());
            tvLocationName.setText(travel.getRegion_name());
            Glide.with(context).load(travel.getLogo_url()).into(imgAvatar);

            tvRate.setText(travel.getEvaluate());
            tvRateText.setText(travel.getEvaluate_text());
            tvCommentCount.setText(travel.getComment_count());
            tvAddress.setText(travel.getAddress());

            try {
                if (travel.isLiked()){
//                    imgHeart.setImageResource(R.drawable.f2_ic_heart);
                    imgHeart.setLiked(true);
                } else {
//                    imgHeart.setImageResource(R.drawable.f2_ic_transparent_heart);
                    imgHeart.setLiked(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            imgHeart.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickHeart();
                        }
                    }, 100);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickHeart();
                        }
                    }, 100);
                }
            });


//            imgHeart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        Account account = MyApplication.getInstance().getAccount();
//                        if (null != account && account.isLogin()) {
//                            clickItem.likeEvent(position);
//                        } else {
//                            LoginAndRegisterActivityNew.startScreen(context, 0, false);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

            try {
                tvType.setText(travel.getCollection().getName());
                Glide.with(context).load(travel.getUrl_icon()).into(imgType);
            } catch (Exception e) {
                tvType.setVisibility(View.GONE);
                imgType.setVisibility(View.GONE);
                e.printStackTrace();
            }


            try {
                if(travel.isHas_location()) {
                    if (travel.getDistance() != null && !"".equals(travel.getDistance()) && Double.parseDouble(travel.getDistance()) < 1000) {
                        tvDistance.setText(travel.getDistance_text()+" " + travel.getDistance() + " m");
                    } else if (travel.getDistance() != null && !"".equals(travel.getDistance())) {
                        double finalValue = Math.round(Double.parseDouble(travel.getDistance()) / 1000 * 10.0) / 10.0;
                        tvDistance.setText(travel.getDistance_text()+" " + finalValue + " km");
                    }
                }else {
                    tvDistance.setText("Không xác định");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            if (Constants.TypeDestination.RESTAURANTS.equals(travel.getContent_type()) || Constants.TypeDestination.HOTELS.equals(travel.getContent_type())) {
//                linearPriceType.setVisibility(View.VISIBLE);
//                layoutForRestaurant.setVisibility(View.GONE);
//                tvStatus.setVisibility(View.GONE);
//                tvOpenDate.setVisibility(View.GONE);
//                tvOpenTime.setVisibility(View.GONE);
//                tvPriceRange.setVisibility(View.VISIBLE);
//                tvPriceRange.setText(travel.getPrice_from() + " đ - " + travel.getPrice_to()+" đ");
//            } else {
                tvPriceRange.setVisibility(View.GONE);
                linearPriceType.setVisibility(View.GONE);
                layoutForRestaurant.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.VISIBLE);
                tvOpenDate.setVisibility(View.VISIBLE);
                tvOpenTime.setVisibility(View.VISIBLE);
                tvOpenDate.setText(travel.getOpen_week());
                tvStatus.setText(travel.getType_open());

                try {
                    tvStatus.setTextColor(Color.parseColor(travel.getTypeOpenColor()));
                    viewStatus.setBackgroundColor(Color.parseColor(travel.getTypeOpenColor()));
                } catch (Exception e) {
                    try {
                        tvStatus.setTextColor(Color.parseColor("#FF0000"));
                        viewStatus.setBackgroundColor(Color.parseColor("#FF0000"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
                }

                try {
                    if(travel.getRange_time().isEmpty()){
                        viewTime.setVisibility(View.GONE);
                        tvOpenTime.setVisibility(View.GONE);
                    }else {
                        viewTime.setVisibility(View.INVISIBLE);
                        tvOpenTime.setText(travel.getRange_time());
                        tvOpenTime.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    viewTime.setVisibility(View.GONE);
                    tvOpenTime.setVisibility(View.GONE);
                }
//            }
        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);
        void likeEvent(int position);
    }
}
