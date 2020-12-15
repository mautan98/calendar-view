package com.namviet.vtvtravel.adapter.f2video;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.namviet.vtvtravel.R;

import java.util.List;

public class TagVideoAdapter extends RecyclerView.Adapter<TagVideoAdapter.MyViewHolder> {
    private Context context;
    private List<String> tags;
    private ClickItemTagVideo clickItemTagVideo;

    public TagVideoAdapter(Context context, List<String> tags, ClickItemTagVideo clickItemTagVideo) {
        this.context = context;
        this.tags = tags;
        this.clickItemTagVideo = clickItemTagVideo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.f2_item_tag, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return tags != null ? tags.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTag;
        private LinearLayout linearTag;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.tvTag);
            linearTag = itemView.findViewById(R.id.linearTag);
        }

        public void onBind(int position) {
            String tag = tags.get(position);
            tvTag.setText(tag);
            linearTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItemTagVideo.onClickItemTagVideo(tags.get(position));
                }
            });
        }
    }

    public interface ClickItemTagVideo {
        void onClickItemTagVideo(String tag);
    }
}
