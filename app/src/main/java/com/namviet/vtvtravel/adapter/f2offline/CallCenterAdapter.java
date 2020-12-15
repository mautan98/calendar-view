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
import com.namviet.vtvtravel.model.offline.Items;

import java.util.List;

public class CallCenterAdapter extends RecyclerView.Adapter<CallCenterAdapter.BaseViewHolder> {
    private Context context;
    private List<Items> list;
    private String parentLink;
    private F2ClickActionListener f2ClickActionListener;

    public CallCenterAdapter(Context context, List<Items> list, String parentLink, F2ClickActionListener f2ClickActionListener) {
        this.context = context;
        this.list = list;
        this.parentLink = parentLink;
        this.f2ClickActionListener = f2ClickActionListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getType().equals("0")){
            return R.layout.f2_item_call_center_button;
        }else {
            return R.layout.f2_item_call_center_image;
        }

    }

    @NonNull
    @Override
    public CallCenterAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.f2_item_call_center_button:
                return new ButtonViewHolder(v);
            case R.layout.f2_item_call_center_image:
                return new ImageViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CallCenterAdapter.BaseViewHolder holder, int position) {
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

    public class ButtonViewHolder extends CallCenterAdapter.BaseViewHolder {
        private ImageView imgIcon;
        private Items items;
        public ButtonViewHolder(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        f2ClickActionListener.onClickF2ClickActionListener(items.getAction());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void onBind(Items o) {
            this.items = o;
            try {
                Glide.with(context).load(parentLink+o.getIcon()).into(imgIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public class ImageViewHolder extends CallCenterAdapter.BaseViewHolder {
        private ImageView imgIcon;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }

        public void onBind(Items o) {
            try {
                Glide.with(context).load(parentLink+o.getIcon()).into(imgIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
