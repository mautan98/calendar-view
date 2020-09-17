package com.namviet.vtvtravel.adapter.f2systeminbox;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.newhome.NewHomeAdapter;
import com.namviet.vtvtravel.response.f2systeminbox.SystemInbox;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;

public class SystemInboxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private List<SystemInbox.Data.InboxItem> inboxItems;
    private ClickItem clickItem;


    public SystemInboxAdapter(List<SystemInbox.Data.InboxItem> inboxItems, Context context, ClickItem clickItem) {
        this.context = context;
        this.inboxItems = inboxItems;
        this.clickItem = clickItem;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_system_inbox, parent, false);
            return new HeaderViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return inboxItems.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTime;
        private TextView tvTitle;
        private CardView cardView;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (inboxItems.get(position).getStatus().equals("0")) {
                        clickItem.onClickItem(inboxItems.get(position));
                        inboxItems.get(position).setStatus("1");
                        notifyDataSetChanged();
//                    }
                }
            });


        }

        public void bindItem(int position) {
            this.position = position;
            tvTitle.setText(inboxItems.get(position).getContent());
            try {
                if (inboxItems.get(position).getStatus().equals("0")) {
                    cardView.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                    tvTitle.setTextColor(Color.parseColor("#000000"));
                } else {
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    tvTitle.setTextColor(Color.parseColor("#878787"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tvTime.setText(DateUtltils.timeToString(Long.valueOf(inboxItems.get(position).getCreatedAt()) / 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public interface ClickItem {
        void onClickItem(SystemInbox.Data.InboxItem inboxItem);
    }


}
