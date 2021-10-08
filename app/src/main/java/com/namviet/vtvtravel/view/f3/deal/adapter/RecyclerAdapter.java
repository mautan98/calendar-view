package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.view.Item;
import com.namviet.vtvtravel.view.f3.deal.view.SimpleDiffCallback;

import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;
import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>
        implements StickyHeaderHandler {

    private final List<Item> data = new ArrayList<>();

    public void setData(List<Item> items) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SimpleDiffCallback(data, items));
        data.clear();
        data.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        final BaseViewHolder viewHolder;
        if (viewType == 1) {
            view = from(parent.getContext()).inflate(R.layout.item_view_2, parent, false);
            viewHolder = new MyViewHolder(view);
        } else {
            view = from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            viewHolder = new MyOtherViewHolder(view);
        }
        view.setOnClickListener(v -> {
            // This is unsafe to do in OnClickListeners attached to sticky headers. The adapter
            // position of the holder will be out of sync if any items have been added/removed.
            // If a click listener needs to be set on a sticky header, it is recommended to identify the header
            // based on its backing model, rather than position in the data set.
            int position = viewHolder.getAdapterPosition();
            if (position != NO_POSITION) {
                List<Item> newData = new ArrayList<>(data);
                newData.remove(position);
                setData(newData);
            }
        });
        return viewHolder;
    }

    @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
        Item item = data.get(position);
        holder.titleTextView.setText("titlw");
        holder.messageTextView.setText("item.message");
        if (item instanceof StickyHeader) {
            holder.itemView.setBackgroundColor(Color.YELLOW);
        } else if (holder instanceof MyOtherViewHolder){
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override public int getItemCount() {
        return data.size();
    }

    @Override public int getItemViewType(int position) {
       if(position == 3){
           return 1;
       }
       else return 0;
    }

    @Override public List<?> getAdapterData() {
        return data;
    }

    private static final class MyViewHolder extends BaseViewHolder {

        MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static final class MyOtherViewHolder extends BaseViewHolder {

        MyOtherViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView messageTextView;

        BaseViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            messageTextView = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }
}
