package com.namviet.vtvtravel.adapter.travelnews;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.TextJustification;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SubTravelNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_1 = 1;
    private static final int TYPE_ITEM_2 = 2;
    private static final int TYPE_ITEM_3 = 3;
    private Context context;
    int max = 3;
    int min = 0;
    private List<Travel> travels;
    private ClickItem clickItem;

    public SubTravelNewsAdapter(Context context, List<Travel> travels, ClickItem clickItem) {
        this.travels = travels;
        this.context = context;
        this.clickItem = clickItem;
    }

    @Override
    public int getItemViewType(int position) {
        if (travels.get(position).getTypeItem() == 1000) {
            int type = ThreadLocalRandom.current().nextInt(min, max + 1);
            travels.get(position).setTypeItem(type);
            return type;
        } else {
            return travels.get(position).getTypeItem();
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_travel_news, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM_1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_travel_news_1, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM_2) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_travel_news_2, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM_3) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_travel_news_3, parent, false);
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
        private ImageView imgVideoType;
        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvViewCount;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgVideoType = itemView.findViewById(R.id.imgVideoType);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);

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
                TextJustification.justify(tvTitle);
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


            try {
                if (travels.get(position).getIs_video().equals("1")) {
                    imgVideoType.setVisibility(View.VISIBLE);
                } else {
                    imgVideoType.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                imgVideoType.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }
    }


    public interface ClickItem {
        void onClickItem(Travel travel);
    }


}
