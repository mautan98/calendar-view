package com.namviet.vtvtravel.adapter.vqmm;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2wheel.WheelAreasResponse;

import java.util.ArrayList;

public class VQMMAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private ArrayList<WheelAreasResponse.Item> items;
    private int highLightPosition = 2000;

    public VQMMAdapter(Context context, ArrayList<WheelAreasResponse.Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    public void highLight(int position) {
        highLightPosition = position;
        notifyItemChanged(position);
    }

    public void resetHighLight() {
        highLightPosition = 2000;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_vqmm, parent, false);
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
            Log.e("lỗi", "okkkkkkk");
        }
    }

    @Override
    public int getItemCount() {
        try {
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvatar;
        private TextView tvTitle;
        private View viewHighLight;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            viewHighLight = itemView.findViewById(R.id.viewHighLight);

        }

        public void bindItem(int position) {

            Glide.with(context).load(items.get(position).getLogo()).into(imgAvatar);
            tvTitle.setText(items.get(position).getName());

            if (position == highLightPosition) {
                viewHighLight.setVisibility(View.VISIBLE);
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.md_black_1000));
            } else {
                viewHighLight.setVisibility(View.GONE);
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
            }


        }
    }


    public interface ClickItem {
        void onClickItem(CommentResponse.Data.Comment comment);

        void onLongClickItem(CommentResponse.Data.Comment comment, CommentResponse.Data.Comment commentParent);

        void onClickReply(CommentResponse.Data.Comment comment, CommentResponse.Data.Comment commentParent);
    }


}
