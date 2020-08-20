package com.namviet.vtvtravel.adapter.f2offline;

import android.content.Context;
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

public class ServicePackageAdapter extends RecyclerView.Adapter<ServicePackageAdapter.BaseViewHolder> {
    private Context context;
    private List<Items> list;
    private String parentLink;

    public ServicePackageAdapter(Context context, List<Items> list, String parentLink) {
        this.context = context;
        this.list = list;
        this.parentLink = parentLink;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.f2_item_service_package_friend;
    }

    @NonNull
    @Override
    public ServicePackageAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new ServicePackageAdapter.FriendPackageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicePackageAdapter.BaseViewHolder holder, int position) {
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

    public class FriendPackageViewHolder extends ServicePackageAdapter.BaseViewHolder {
        private TextView tvTitle;
        private TextView tvSubTitle;
        private ImageView imgIcon;
        public FriendPackageViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            imgIcon = itemView.findViewById(R.id.imgIcon);
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
                Glide.with(context).load(parentLink+o.getIcon()).into(imgIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
