package com.namviet.vtvtravel.view.f3.smalllocation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;

import java.util.List;

public class SubFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE = 0;
    private static final int TYPE_VIEW_MORE = 1;
    private Context context;
    private FilterByPageResponse.Data data;
    private boolean isExpanded = false;
    private int selectedPosition = -1;
    private IOnFilterClick iOnFilterClick;

    public SubFilterAdapter(FilterByPageResponse.Data data, Context context, IOnFilterClick iOnFilterClick) {
        this.context = context;
        this.data = data;
        this.iOnFilterClick = iOnFilterClick;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.getInputs().size() > 7) {
            if (isExpanded) {
                if (position == data.getInputs().size() - 1) {
                    return TYPE_VIEW_MORE;
                } else return TYPE;
            } else {
                if (position == 7) {
                    return TYPE_VIEW_MORE;
                } else return TYPE;
            }
        } else return TYPE;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_filter_text, parent, false);
            return new ItemViewHolder(v);
        } else if (viewType == TYPE_VIEW_MORE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f3_item_filter_text_view_more, parent, false);
            return new ItemViewMore(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE) {
                ((ItemViewHolder) holder).bindItem(position);
            } else {
                ((ItemViewMore) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (isExpanded) {
            return data.getInputs().size();
        } else if (data.getInputs().size() > 7) {
            return 8;
        } else return data.getInputs().size();
    }

    public class ItemViewMore extends RecyclerView.ViewHolder {
        private TextView tvViewMore;

        public ItemViewMore(View itemView) {
            super(itemView);
            tvViewMore = itemView.findViewById(R.id.tv_view_more);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isExpanded) {
                        isExpanded = false;
                        tvViewMore.setText("Hiển thị thêm");
                    } else {
                        isExpanded = true;
                        tvViewMore.setText("Thu gọn");
                    }
                    notifyDataSetChanged();
                }
            });

        }

        public void bindItem(int position) {

        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_filter;
        private LinearLayout lnlFilter;
        private TextView tv_filter_count;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_filter = itemView.findViewById(R.id.tv_filter);
            tv_filter_count = itemView.findViewById(R.id.tv_filter_count);
            lnlFilter = itemView.findViewById(R.id.lnl_filter);
        }

        public void bindItem(int position) {
            FilterByPageResponse.Data.Input input = data.getInputs().get(position);
            if (input.isSelected()) {
                lnlFilter.setBackground(context.getResources().getDrawable(R.drawable.f3_bg_filter_text_selected));
                tv_filter.setTextColor(context.getResources().getColor(R.color.white));
                tv_filter_count.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                lnlFilter.setBackground(context.getResources().getDrawable(R.drawable.f3_bg_filter_text));
                tv_filter.setTextColor(Color.parseColor("#00918D"));
                tv_filter_count.setTextColor(Color.parseColor("#808080"));
            }
            if (data.getCode().equals("HOTEL_STANDARD_RATE")) {
                if (data.getInputs().get(position).getLabel().equals("1")) {
                    tv_filter.setText(String.format("Kém: Dưới %s điểm", data.getInputs().get(position).getLabel()));
                } else if (data.getInputs().get(position).getLabel().equals("2")) {
                    tv_filter.setText(String.format("Trung bình: Dưới %s điểm", data.getInputs().get(position).getLabel()));
                } else if (data.getInputs().get(position).getLabel().equals("3")) {
                    tv_filter.setText(String.format("Tốt: %s điểm trở lên", data.getInputs().get(position).getLabel()));
                } else
                    tv_filter.setText(String.format("Trung bình: %s điểm trở lên", data.getInputs().get(position).getLabel()));
            } else tv_filter.setText(data.getInputs().get(position).getLabel());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (input.isSelected()) {
                        input.setSelected(false);
                    } else input.setSelected(true);
                    if (iOnFilterClick != null) {
                        iOnFilterClick.onItemFilterClick();
                    }
                    selectedPosition = position;
                    notifyDataSetChanged();
                }
            });
        }
    }

}
