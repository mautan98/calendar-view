package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.SearchSelectListener;
import com.namviet.vtvtravel.model.ItemNotify;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;

public class ListNotifyAdapter extends RecyclerView.Adapter<ListNotifyAdapter.NotifyHolder> {
    private Context context;
    private List<ItemNotify> notifyList;
    private SearchSelectListener searchSelectListener;

    public void setSearchSelectListener(SearchSelectListener searchSelectListener) {
        this.searchSelectListener = searchSelectListener;
    }

    public ListNotifyAdapter(Context context, List<ItemNotify> notifyList) {
        this.context = context;
        this.notifyList = notifyList;
    }

    @NonNull
    @Override
    public NotifyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_notify, parent, false);
        return new NotifyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyHolder holder, int position) {
        holder.bindItem(position);

    }

    @Override
    public int getItemCount() {
        return notifyList == null ? 0 : notifyList.size();
    }

    public class NotifyHolder extends BaseHolder {
        private ImageView avatar;
        private TextView tvTitle;
        private TextView tvShortDes;
        private TextView tvTime;

        public NotifyHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvShortDes = itemView.findViewById(R.id.tvShortDes);
            tvTime = itemView.findViewById(R.id.tvTime);

        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final ItemNotify notify = notifyList.get(position);
            setImageUrl(notify.getLogo_url(), avatar);
            tvTitle.setText(notify.getTitle());
            tvShortDes.setText(notify.getContent());
            tvTime.setText("" + DateUtltils.timeToString(notify.getCreated()));
            if (notify.isRead()) {
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.gray_99));
                tvShortDes.setTextColor(ContextCompat.getColor(context, R.color.gray_99));
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.gray_99));
            } else {
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.gray_33));
                tvShortDes.setTextColor(ContextCompat.getColor(context, R.color.gray_33));
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.gray_66));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != searchSelectListener) {
                        searchSelectListener.onSearchSelect(notify);
                        if (!notify.isRead()) {
                            notify.setRead(true);
                            notifyDataSetChanged();
                        }
                    }
                }
            });

        }
    }
}
