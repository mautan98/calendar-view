package com.namviet.vtvtravel.adapter.f2offline;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.listener.F2ClickActionListener;
import com.namviet.vtvtravel.model.offline.Package;

import java.util.List;

public class TopPackageAdapter extends RecyclerView.Adapter<TopPackageAdapter.BaseViewHolder> {
    private Context context;
    private List<Package> list;
    private String parentLink;
    private F2ClickActionListener f2ClickActionListener;

    public TopPackageAdapter(Context context, List<Package> list, String parentLink, F2ClickActionListener f2ClickActionListener) {
        this.context = context;
        this.list = list;
        this.parentLink = parentLink;
        this.f2ClickActionListener = f2ClickActionListener;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.f2_item_top_package;
    }

    @NonNull
    @Override
    public TopPackageAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new TopPackageAdapter.FriendPackageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPackageAdapter.BaseViewHolder holder, int position) {
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

        public void onBind(Package o) {

        }
    }

    public class FriendPackageViewHolder extends TopPackageAdapter.BaseViewHolder {
        private ImageView imgIcon;
        private Package aPackage;
        public FriendPackageViewHolder(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        f2ClickActionListener.onClickF2ClickActionListener(aPackage.getAction());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void onBind(Package o) {
            try {
                this.aPackage = o;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Glide.with(context).load(parentLink+o.getBanner()).into(imgIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
