package com.namviet.vtvtravel.adapter.f2travelvoucher;

import android.content.Context;
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
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.RankVoucherResponse;

import java.util.List;

public class RankFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private int oldSelected = 1000;
    private List<RankVoucherResponse.Rank> ranks;

    public RankFilterAdapter(List<RankVoucherResponse.Rank> ranks, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.ranks = ranks;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_travel_voucher_category, parent, false);
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
            return ranks.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private int position;
        private TextView tvName;
        private ImageView imgCheck;
        private LinearLayout linearSort;
        private ImageView imgAvatar;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgCheck = itemView.findViewById(R.id.imgCheck);
            linearSort = itemView.findViewById(R.id.linearSort);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }

        public void bindItem(int position) {
            this.position = position;
            tvName.setText(ranks.get(position).getName());
            if (ranks.get(position).isSelected()) {
                imgCheck.setVisibility(View.VISIBLE);
            } else {
                imgCheck.setVisibility(View.GONE);
            }

            linearSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < ranks.size(); i++) {
                        ranks.get(i).setSelected(false);
                    }
                    ranks.get(position).setSelected(true);
                    notifyDataSetChanged();
                    clickItem.onClickItem(ranks.get(position));
                }
            });

            if(position == 0) {
                Glide.with(context).load(R.drawable.f2_ic_filter_rank_all).into(imgAvatar);
            }else {
                Glide.with(context).load(ranks.get(position).getIconRank()).into(imgAvatar);
            }
//            switch (ranks.get(position).getId()) {
//                case "2":
//                    Glide.with(context).load(R.drawable.f2_ic_filter_di_dau).into(imgAvatar);
//                    break;
//                case "4":
//                    Glide.with(context).load(R.drawable.f2_ic_filter_choi_gi).into(imgAvatar);
//                    break;
//                case "6":
//                    Glide.with(context).load(R.drawable.f2_ic_filter_an_gi).into(imgAvatar);
//                    break;
//                case "8":
//                    Glide.with(context).load(R.drawable.f2_ic_filter_o_dau).into(imgAvatar);
//                    break;
//                default:
//                    Glide.with(context).load(R.drawable.f2_ic_filter_all).into(imgAvatar);
//            }
        }
    }


    public interface ClickItem {
        void onClickItem(RankVoucherResponse.Rank rank);
    }


}
