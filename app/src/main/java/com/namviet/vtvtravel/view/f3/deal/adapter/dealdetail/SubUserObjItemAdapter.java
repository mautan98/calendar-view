package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.model.UserObj;

import java.util.List;

public class SubUserObjItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    private Context context;
    private List<UserObj> itemList;


    public SubUserObjItemAdapter(Context context, List<UserObj> itemList) {
        this.context = context;
        this.itemList = itemList;
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
            v = LayoutInflater.from(context).inflate(R.layout.item_user_obj, parent, false);
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
        return itemList != null ? itemList.size() : 0;
    }




    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgRank;
        private TextView mTvRank;
        private ImageView mImgAccept;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }
        private void initView(View itemView) {
            mImgRank = (ImageView) itemView.findViewById(R.id.img_rank);
            mTvRank = (TextView) itemView.findViewById(R.id.tv_rank);
            mImgAccept = (ImageView) itemView.findViewById(R.id.img_accept);
        }
        public void bindItem(int position) {
            UserObj obj = itemList.get(position);
            mImgRank.setImageResource(obj.getIcon());
            mTvRank.setText(obj.getName());
            if(obj.isAccept()){
                mImgAccept.setImageResource(R.drawable.ic_baseline_done_24);
            }
            else mImgAccept.setVisibility(View.GONE);
        }


    }


}
