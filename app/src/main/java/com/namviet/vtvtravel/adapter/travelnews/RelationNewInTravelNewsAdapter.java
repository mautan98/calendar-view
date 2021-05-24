package com.namviet.vtvtravel.adapter.travelnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;

public class RelationNewInTravelNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    private Context context;
    private List<Travel> travels;
    private ClickItem clickItem;

    public RelationNewInTravelNewsAdapter(Context context, List<Travel> travels, ClickItem clickItem) {
        this.travels = travels;
        this.context = context;
        this.clickItem = clickItem;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_relation_news_in_travel_news, parent, false);
            return new HeaderViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            ((HeaderViewHolder) holder).bindItem(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return travels.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        //        private ImageView imgVideoType;
        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvViewCount;
        private int position;
        private View view;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
//            imgVideoType = itemView.findViewById(R.id.imgVideoType);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
            try {
                view = itemView.findViewById(R.id.view);
            } catch (Exception e) {
                e.printStackTrace();
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        clickItem.onClickItem(travels.get(position));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            try {
                tvTitle.setText(travels.get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Glide.with(context).load(travels.get(position).getLogo_url()).into(imgAvatar);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tvTime.setText(DateUtltils.timeToString(Long.valueOf(travels.get(position).getCreated())));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tvViewCount.setText(travels.get(position).getView_count());

                double value = Double.parseDouble(travels.get(position).getView_count());
                if (value > 1000) {
                    double finalValue = Math.round(value / 1000 * 10.0) / 10.0;
                    tvViewCount.setText(finalValue + "k");
                } else {
                    tvViewCount.setText(travels.get(position).getView_count());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


//            try {
//                if (travels.get(position).getIs_video().equals("1")) {
//                    imgVideoType.setVisibility(View.VISIBLE);
//                } else {
//                    imgVideoType.setVisibility(View.GONE);
//                }
//            } catch (Exception e) {
//                imgVideoType.setVisibility(View.GONE);
//                e.printStackTrace();
//            }

            try {
                if(position == travels.size() - 1){
                    view.setVisibility(View.GONE);
                }else {
                    view.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);
    }


}
