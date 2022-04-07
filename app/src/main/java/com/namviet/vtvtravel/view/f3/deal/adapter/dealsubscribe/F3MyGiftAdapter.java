package com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3SubDealAdapter;
import com.namviet.vtvtravel.view.f3.deal.model.mygift.MyGift;

import java.util.ArrayList;

public class F3MyGiftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ArrayList<MyGift> myGifts;


    public F3MyGiftAdapter(Context context, ArrayList<MyGift> myGifts) {
        this.context = context;
        this.myGifts = myGifts;

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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_my_gift, parent, false);
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
            return myGifts.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvType;
        private ImageView imgAvatar;
        private TextView tvExpiryDate;
        private View layoutPrice;
        private TextView tvOriginPrice;
        private TextView tvDisplayPrice;
        private TextView tvDisCount;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvType = itemView.findViewById(R.id.tvType);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            layoutPrice = itemView.findViewById(R.id.layoutPrice);
            tvOriginPrice = itemView.findViewById(R.id.tvOriginPrice);
            tvDisplayPrice = itemView.findViewById(R.id.tvDisplayPrice);
            tvDisCount = itemView.findViewById(R.id.tv_dis_count);

        }

        public void bindItem(int position) {
            tvName.setText(myGifts.get(position).getName());
            tvType.setText(myGifts.get(position).getType());
            Glide.with(context).load(myGifts.get(position).getAvatarUri()).error(R.drawable.img_placeholder).into(imgAvatar);


            try {
                if (myGifts.get(position).getExpireDate() != null) {
                    tvExpiryDate.setText("HSD: " + DateUtltils.timeToString18(Long.parseLong(myGifts.get(position).getExpireDate())));
                } else {
                    tvExpiryDate.setText("");
                }
            } catch (Exception e) {
                tvExpiryDate.setText("");
                e.printStackTrace();
            }


            try {
                tvOriginPrice.setPaintFlags(tvOriginPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } catch (Exception e) {
                e.printStackTrace();
            }


            switch (myGifts.get(position).getPromotionTypeId()){
                case "1":
                    layoutPrice.setVisibility(View.GONE);
                    break;
                case "2":
                    layoutPrice.setVisibility(View.GONE);
                    break;
                case "3":
                    layoutPrice.setVisibility(View.GONE);
                    break;
                case "4":
                    layoutPrice.setVisibility(View.VISIBLE);

                    try {
                        if (myGifts.get(position).getPriceBeforePromo() != null) {
                            tvOriginPrice.setVisibility(View.VISIBLE);
                            tvOriginPrice.setText(F3SubDealAdapter.convertPrice(String.valueOf(myGifts.get(position).getPriceBeforePromo())) + " đ");
                        } else {
                            tvOriginPrice.setText("");
                            tvOriginPrice.setVisibility(View.INVISIBLE);
                        }
                    } catch (Exception e) {
                        try {
                            tvOriginPrice.setVisibility(View.VISIBLE);
                            tvOriginPrice.setText(myGifts.get(position).getPriceBeforePromo() + " đ");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            tvOriginPrice.setVisibility(View.INVISIBLE);
                        }
                        e.printStackTrace();
                    }


                    try {
                        if (myGifts.get(position).getPriceAfterPromo() != null) {
                            tvDisplayPrice.setVisibility(View.VISIBLE);
                            tvDisplayPrice.setText(F3SubDealAdapter.convertPrice(String.valueOf(myGifts.get(position).getPriceAfterPromo())) + " đ");
                        } else {
                            tvDisplayPrice.setText("");
                            tvDisplayPrice.setVisibility(View.INVISIBLE);
                        }
                    } catch (Exception e) {
                        try {
                            tvDisplayPrice.setVisibility(View.VISIBLE);
                            tvDisplayPrice.setText(myGifts.get(position).getPriceAfterPromo() + " đ");
                        } catch (Exception ex) {
                            tvDisplayPrice.setVisibility(View.INVISIBLE);
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }

                    if(myGifts.get(position).getDisplayType().equals("2")){
                        tvDisCount.setVisibility(View.VISIBLE);
                    }else {
                        tvDisCount.setVisibility(View.GONE);
                    }

                    break;
                default:
                    layoutPrice.setVisibility(View.GONE);

            }
        }
    }

}
