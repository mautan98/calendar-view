package com.namviet.vtvtravel.adapter.f2biglocation.sub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.travelnews.Location;

import java.util.List;

public class SearchAllLocationAdapter2 extends RecyclerView.Adapter<SearchAllLocationAdapter2.MyViewHolder> {
    private Context context;
    private List<Location> locationList;
    private ClickItem clickItem;

    public SearchAllLocationAdapter2(Context context, List<Location> locationList, ClickItem clickItem) {
        this.context = context;
        this.locationList = locationList;
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public SearchAllLocationAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_search_all_location_2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAllLocationAdapter2.MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return locationList != null ? locationList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView imgTick;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgTick = itemView.findViewById(R.id.imgTick);
        }

        public void onBind(int position) {
            Location location = locationList.get(position);
            tvName.setText(location.getName());
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < locationList.size(); i++) {
                        locationList.get(i).setSelected(false);
                    }
                    locationList.get(position).setSelected(true);
                    notifyDataSetChanged();
                    clickItem.onClick(location);
                }
            });

            if(location.isSelected()){
                imgTick.setVisibility(View.VISIBLE);
            }else {
                imgTick.setVisibility(View.GONE);
            }

        }
    }

    public interface ClickItem {
        void onClick(Location location);
    }
}
