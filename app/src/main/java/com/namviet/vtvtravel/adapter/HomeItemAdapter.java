package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeItemHolder> {
    private Context mContext;

    public HomeItemAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_horizontal, parent, false);
        return new HomeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HomeItemHolder extends RecyclerView.ViewHolder {
        public HomeItemHolder(View itemView) {
            super(itemView);
        }
    }
}
