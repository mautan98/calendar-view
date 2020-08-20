package com.namviet.vtvtravel.adapter.travelnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.comment.CommentAdapter;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CommentInDetailTravelNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<CommentResponse.Data.Comment> comments;

    public CommentInDetailTravelNewsAdapter(Context context, List<CommentResponse.Data.Comment> comments,  ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.comments = comments;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_comment, parent, false);
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
        if(comments == null){
            return 0;
        }else if(comments.size() >= 3){
            return 3;
        }else {
            return comments.size();
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private ImageView imgLevel;
        private TextView tvTime;
        private TextView tvComment;
        private TextView tvCountLike;
        private TextView tvReply;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            imgLevel = itemView.findViewById(R.id.imgLevel);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvCountLike = itemView.findViewById(R.id.tvCountLike);
            tvReply = itemView.findViewById(R.id.tvReply);

            tvReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickReply(comments.get(position));
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            tvName.setText(comments.get(position).getUser().getFullname());
            tvComment.setText(comments.get(position).getContent());
            Glide.with(context).load(comments.get(position).getUser().getImageProfile()).apply(new RequestOptions().circleCrop()).into(imgAvatar);
            tvTime.setText(DateUtltils.convertTime(comments.get(position).getCreated()));
        }
    }


    public interface ClickItem{
        void onClickItem(CommentResponse.Data.Comment comment);
        void onClickReply(CommentResponse.Data.Comment comment);
    }


}
