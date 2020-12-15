package com.namviet.vtvtravel.adapter.newhome;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;

import java.util.List;

public class FavorPlaceAdapter extends RecyclerView.Adapter<FavorPlaceAdapter.ViewHodel> {

    private Context context;
    private List<Object> list;

    public FavorPlaceAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_favor_place, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
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
