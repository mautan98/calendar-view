package com.namviet.vtvtravel.adapter.f2offline;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.offline.Items;

import java.util.List;

public class CallNowAdapter extends RecyclerView.Adapter<CallNowAdapter.BaseViewHolder> {
    private Context context;
    private List<Items> list;
    private String parentLink;


    public CallNowAdapter(Context context, List<Items> list, String parentLink) {
        this.context = context;
        this.list = list;
        this.parentLink = parentLink;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getType().equals("0")){
            return R.layout.f2_item_callnow_type1039;
        }else {
            return R.layout.f2_item_callnow_link;
        }

    }

    @NonNull
    @Override
    public CallNowAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.f2_item_callnow_type1039:
                return new Type1039ViewHolder(v);
            case R.layout.f2_item_callnow_link:
                return new LinkToContactViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CallNowAdapter.BaseViewHolder holder, int position) {
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

    public class Type1039ViewHolder extends BaseViewHolder {
        private ImageView imgIcon;
        private TextView tv1, tv2, tv3;
        public Type1039ViewHolder(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
        }

        public void onBind(Items o) {
            super.onBind(o);
            try {
                Glide.with(context).load(parentLink+o.getIcon()).into(imgIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tv1.setText(o.getSpecialText().get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tv2.setText(o.getSpecialText().get(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tv3.setText(o.getSpecialText().get(2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class LinkToContactViewHolder extends BaseViewHolder {
        private TextView tvTextItem;
        private ImageView imgIcon;
        public LinkToContactViewHolder(View itemView) {
            super(itemView);
            tvTextItem  = itemView.findViewById(R.id.tvTextItem);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }

        public void onBind(Items o) {
            super.onBind(o);
            try {
                tvTextItem.setText(o.getSubTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Glide.with(context).load(parentLink+o.getIcon()).into(imgIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
