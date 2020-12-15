package com.namviet.vtvtravel.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.listener.CitySelectListener;
import com.namviet.vtvtravel.model.City;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder> {
    private Context mContext;
    private ArrayList<City> cityList;
    private ArrayList<City> arrFilter;
    private int index = 0;
    private CitySelectListener citySelectListener;
    private String idCity;

    public void setCitySelectListener(CitySelectListener citySelectListener) {
        this.citySelectListener = citySelectListener;
    }

    public CityAdapter(Context mContext, ArrayList<City> cityList) {
        this.mContext = mContext;
        this.cityList = cityList;
        arrFilter = new ArrayList<>();
        arrFilter.addAll(this.cityList);
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_city, parent, false);
        return new CityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return cityList == null ? 0 : cityList.size();
    }

    public class CityHolder extends RecyclerView.ViewHolder {
        private TextView tvCity;
        private ImageView ivSelect;

        public CityHolder(View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
            ivSelect = itemView.findViewById(R.id.ivSelect);
        }

        public void bindItem(final int position) {
            final City city = cityList.get(position);
            tvCity.setText(city.getName());
            if (city.isSelected()) {
                index = position;
                ivSelect.setImageResource(R.drawable.vt_checked);
            } else {
                ivSelect.setImageResource(R.drawable.vt_check);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    City city = cityList.get(position);
                    cityList.get(position).setSelected(true);
                    idCity = city.getId();
                    notifyDataSetChanged();
                    cityList.clear();
                    cityList.addAll(arrFilter);
                    for (int i = 0; i < cityList.size(); i++) {
                        if (idCity.equals(cityList.get(i).getId())) {
                            cityList.get(i).setSelected(true);
                        } else {
                            cityList.get(i).setSelected(false);
                        }
                    }
                    if (null != citySelectListener) {
                        citySelectListener.onSelect(null, city);
                    }


                }
            });
        }
    }

    public ArrayList<City> getCityList() {
        return cityList;
    }

    public void filter(String charText) {
        charText = deAccent(charText.toLowerCase(Locale.getDefault()));
        cityList.clear();
        if (charText.length() == 0) {
            cityList.addAll(arrFilter);
        } else {
            for (City wp : arrFilter) {
                String name = deAccent(wp.getName().toLowerCase(Locale.getDefault()));
                if (name.contains(charText)) {
                    cityList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public String deAccent(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }
}
