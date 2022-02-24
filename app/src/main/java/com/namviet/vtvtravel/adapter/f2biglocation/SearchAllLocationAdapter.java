package com.namviet.vtvtravel.adapter.f2biglocation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.travelnews.Location;

import java.util.List;

public class SearchAllLocationAdapter extends RecyclerView.Adapter<SearchAllLocationAdapter.MyViewHolder> {
    private Context context;
    private List<Location> locationList;
    private ClickItem clickItem;
    private int selected = -1;

    public SearchAllLocationAdapter(Context context, List<Location> locationList, ClickItem clickItem) {
        this.context = context;
        this.locationList = locationList;
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public SearchAllLocationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_search_all_location, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAllLocationAdapter.MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return locationList != null ? locationList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView imgChecked;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgChecked = itemView.findViewById(R.id.img_checked);
        }

        public void onBind(int position) {
            if(selected == position){
                imgChecked.setVisibility(View.VISIBLE);
            }
            else imgChecked.setVisibility(View.INVISIBLE);
            Location location = locationList.get(position);
            tvName.setText(location.getName());
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected = position;
                    clickItem.onClick(location);
                    notifyDataSetChanged();
                }
            });

        }
    }

    public interface ClickItem {
        void onClick(Location location);
    }
}
