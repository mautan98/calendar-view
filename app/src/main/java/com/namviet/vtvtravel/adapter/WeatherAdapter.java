package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.ItemWeather;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {
    private Context mContext;
    private List<ItemWeather> itemWeatherList;

    public WeatherAdapter(Context mContext, List<ItemWeather> itemWeatherList) {
        this.mContext = mContext;
        this.itemWeatherList = itemWeatherList;
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_weather, parent, false);
        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return itemWeatherList == null ? 0 : itemWeatherList.size();
    }

    public class WeatherHolder extends BaseHolder {
        private TextView tvDate;
        private ImageView ivIcon;
        private TextView tvAmountTemp;

        public WeatherHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmountTemp = itemView.findViewById(R.id.tvAmountTemp);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            ItemWeather itemWeather = itemWeatherList.get(position);
            if (Math.round(itemWeather.getTemp_min()) == Math.round(itemWeather.getTemp_max())) {
                tvAmountTemp.setText(Math.round(itemWeather.getTemp_min()) + "°C");
            } else {
                tvAmountTemp.setText(Math.round(itemWeather.getTemp_min()) + " - " + Math.round(itemWeather.getTemp_max()) + "°C");
            }

            setImageUrl(itemWeather.getWeather().getIcon_url(), ivIcon);
            tvDate.setText(DateUtltils.timeToStringWeather(itemWeather.getDate()));
        }
    }
}
