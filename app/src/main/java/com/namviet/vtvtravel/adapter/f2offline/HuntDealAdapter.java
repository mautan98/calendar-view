package com.namviet.vtvtravel.adapter.f2offline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.activity.BaseActivity;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.listener.F2ClickActionListener;
import com.namviet.vtvtravel.model.offline.Action;
import com.namviet.vtvtravel.model.offline.Items;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.fragment.f2offline.RuleDialog;

import org.w3c.dom.Text;

import java.util.List;

public class HuntDealAdapter extends RecyclerView.Adapter<HuntDealAdapter.BaseViewHolder> {
    private Context context;
    private List<Items> list;
    private String parentLink;
    private F2ClickActionListener f2ClickActionListener;

    public HuntDealAdapter(Context context, List<Items> list, String parentLink, F2ClickActionListener f2ClickActionListener) {
        this.context = context;
        this.list = list;
        this.parentLink = parentLink;
        this.f2ClickActionListener = f2ClickActionListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType().equals("0")) {
            return R.layout.f2_item_huntdeal_call1039;
        } else if (list.get(position).getType().equals("1")) {
            return R.layout.f2_item_huntdeal_gift;
        } else {
            return R.layout.f2_item_huntdeal_link;
        }
    }

    @NonNull
    @Override
    public HuntDealAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.f2_item_huntdeal_call1039:
                return new HuntDealAdapter.Call1039ViewHolder(v);
            case R.layout.f2_item_huntdeal_gift:
                return new HuntDealAdapter.GiftViewHolder(v);
            case R.layout.f2_item_huntdeal_link:
                return new HuntDealAdapter.LinkViewHolder(v);
            default:
                return new HuntDealAdapter.BaseViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HuntDealAdapter.BaseViewHolder holder, int position) {
        try {
            holder.onBind(list.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public void onBind(Items o) {

        }
    }

    public class Call1039ViewHolder extends HuntDealAdapter.BaseViewHolder {
        private TextView tvTitle;
        private TextView tvSubTitle;
        private ImageView imgIcon;
        private ImageView btnCallNow;
        private Items items;

        public Call1039ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            btnCallNow = itemView.findViewById(R.id.btnCallNow);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    f2ClickActionListener.onClickF2ClickActionListener(items.getAction());
                }
            });
        }

        public void onBind(Items o) {
            try {
                items = o;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tvTitle.setText(o.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tvSubTitle.setText(o.getSubTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Glide.with(context).load(parentLink + o.getIcon()).into(imgIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Glide.with(context).load(parentLink + o.getButton()).into(btnCallNow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class GiftViewHolder extends HuntDealAdapter.BaseViewHolder {
        private TextView tvTitle;
        private TextView tvSubTitle;
        private ImageView imgIcon;

        public GiftViewHolder(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
        }

        public void onBind(Items o) {
            try {
                tvTitle.setText(o.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tvSubTitle.setText(o.getSubTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Glide.with(context).load(parentLink + o.getIcon()).into(imgIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class LinkViewHolder extends HuntDealAdapter.BaseViewHolder {
        private TextView tvTitle;
        private TextView tvSubTitle;
        private ImageView imgIcon;
        private Items items;

        public LinkViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    f2ClickActionListener.onClickF2ClickActionListener(items.getAction());
                }
            });
        }

        public void onBind(Items o) {
            try {
                this.items = o;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tvTitle.setText(o.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tvSubTitle.setText(o.getSubTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Glide.with(context).load(parentLink + o.getIcon()).into(imgIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
