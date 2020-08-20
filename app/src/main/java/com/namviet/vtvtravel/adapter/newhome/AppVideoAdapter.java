package com.namviet.vtvtravel.adapter.newhome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;

import java.util.List;

public class AppVideoAdapter extends RecyclerView.Adapter<AppVideoAdapter.ViewHodel> {

    private Context context;
    private List<Object> list;

    public AppVideoAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AppVideoAdapter.ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_app_video, parent, false);
        return new AppVideoAdapter.ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppVideoAdapter.ViewHodel holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHodel extends RecyclerView.ViewHolder {


        public ViewHodel(View itemView) {
            super(itemView);
        }

        public void onBind(Object o) {

        }
    }
}
