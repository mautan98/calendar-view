package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.Travel;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.SearchSelectListener;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.SearchHistory;
import com.namviet.vtvtravel.model.SearchNearSuggest;
import com.namviet.vtvtravel.model.SearchResult;
import com.namviet.vtvtravel.model.SearchTrend;
import com.namviet.vtvtravel.model.SearchVoucher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GroupSearchAdapter extends RecyclerView.Adapter<GroupSearchAdapter.GroupSearchHolder> {
    private Context mContext;
    private List<Object> suggestList;
    private SearchSelectListener searchSelectListener;
    private TravelSelectListener travelSelectListener;

    public void setTravelSelectListener(TravelSelectListener travelSelectListener) {
        this.travelSelectListener = travelSelectListener;
    }

    public void setSearchSelectListener(SearchSelectListener searchSelectListener) {
        this.searchSelectListener = searchSelectListener;
    }

    public GroupSearchAdapter(Context mContext, List<Object> suggestList) {
        this.mContext = mContext;
        this.suggestList = suggestList;
    }

    @NonNull
    @Override
    public GroupSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_search, parent, false);
        return new GroupSearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupSearchHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return suggestList == null ? 0 : suggestList.size();
    }

    public class GroupSearchHolder extends BaseHolder implements TravelSelectListener, SearchSelectListener {
        private TextView tvTitle;
        private RecyclerView rvItem;

        public GroupSearchHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rvItem = itemView.findViewById(R.id.rvItem);
            rvItem.setLayoutManager(new LinearLayoutManager(mContext));
            rvItem.setNestedScrollingEnabled(false);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            Object item = suggestList.get(position);
            String json = new Gson().toJson(item);
            try {
                JSONObject itemSuggest = new JSONObject(json);
                if (itemSuggest.has(Constants.KeyJSON.CODE) && itemSuggest.getString(Constants.KeyJSON.CODE).equals(Constants.TypeSearch.RECENT_HISTORY)) {
                    SearchHistory history = new Gson().fromJson(json, SearchHistory.class);
                    tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_bg_tv_notify));
                    tvTitle.setText(history.getTitle());
                    if (null != history.getItems() && history.getItems().size() > 0 && null != history.getTitle()) {
                        tvTitle.setVisibility(View.VISIBLE);
                    } else {
                        tvTitle.setVisibility(View.GONE);
                    }
                    rvItem.setVisibility(View.VISIBLE);
                    SearchNearAdapter searchNearAdapter = new SearchNearAdapter(mContext, history.getItems(), history.getTitle());
                    rvItem.setAdapter(searchNearAdapter);
                    searchNearAdapter.setSearchSelectListener(this);
                } else if (itemSuggest.has(Constants.KeyJSON.CODE) && itemSuggest.getString(Constants.KeyJSON.CODE).equals(Constants.TypeSearch.YOUR_VOUCHER)) {
                    final SearchVoucher voucher = new Gson().fromJson(json, SearchVoucher.class);
                    tvTitle.setText(voucher.getTitle());
                    if (null != voucher.getTitle()){
                        tvTitle.setVisibility(View.VISIBLE);
                    } else{
                        tvTitle.setVisibility(View.GONE);

                    }
                    rvItem.setVisibility(View.GONE);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != searchSelectListener) {
                                searchSelectListener.onSearchSelect(voucher);
                            }
                        }
                    });
                } else if (itemSuggest.has(Constants.KeyJSON.CODE) && itemSuggest.getString(Constants.KeyJSON.CODE).equals(Constants.TypeSearch.HIGHLIGHT_TREND)) {
                    SearchTrend trend = new Gson().fromJson(json, SearchTrend.class);
                    tvTitle.setText(trend.getTitle());
                    if (null != trend.getItems() && trend.getItems().size() > 0 && null != trend.getTitle()) {
                        tvTitle.setVisibility(View.VISIBLE);
                    } else {
                        tvTitle.setVisibility(View.GONE);
                    }
                    rvItem.setVisibility(View.VISIBLE);
                    SearchTrendAdapter searchTrendAdapter = new SearchTrendAdapter(mContext, trend.getItems());
                    searchTrendAdapter.setTravelSelectListener(this);
                    rvItem.setAdapter(searchTrendAdapter);
                } else if (itemSuggest.has(Constants.KeyJSON.CODE) && itemSuggest.getString(Constants.KeyJSON.CODE).equals(Constants.TypeSearch.NEARBY_SUGGESTION)) {
                    SearchNearSuggest suggest = new Gson().fromJson(json, SearchNearSuggest.class);
                    tvTitle.setText(suggest.getTitle());
                    if (null != suggest.getItems() && suggest.getItems().size() > 0 && null != suggest.getTitle()) {
                        tvTitle.setVisibility(View.VISIBLE);
                    } else {
                        tvTitle.setVisibility(View.GONE);
                    }
                    rvItem.setVisibility(View.VISIBLE);
                    SearchNearAdapter searchNearAdapter = new SearchNearAdapter(mContext, suggest.getItems(), suggest.getWelcome());
                    rvItem.setAdapter(searchNearAdapter);
                    searchNearAdapter.setSearchSelectListener(this);
                } else {
                    SearchResult result = new Gson().fromJson(json, SearchResult.class);
                    tvTitle.setText(result.getName());
                    if (null != result.getItems() && result.getItems().size() > 0 && null != result.getName()) {
                        tvTitle.setVisibility(View.VISIBLE);
                    } else {
                        tvTitle.setVisibility(View.GONE);
                    }
                    tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.md_grey_900));
                    rvItem.setVisibility(View.VISIBLE);
                    SearchTrendAdapter searchTrendAdapter = new SearchTrendAdapter(mContext, result.getItems());
                    rvItem.setAdapter(searchTrendAdapter);
                    searchTrendAdapter.setTravelSelectListener(this);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSelectTravel(Travel travel) {
            if (null != travelSelectListener) {
                travelSelectListener.onSelectTravel(travel);
            }
        }

        @Override
        public void onSearchSelect(Object object) {
            if (null != searchSelectListener) {
                searchSelectListener.onSearchSelect(object);
            }
        }
    }

    public void setSuggestList(List<Object> suggestList) {
        this.suggestList = suggestList;
        notifyDataSetChanged();
    }
}
