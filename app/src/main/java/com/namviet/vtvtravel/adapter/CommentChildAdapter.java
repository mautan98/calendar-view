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
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Comment;

import java.util.List;

public class CommentChildAdapter  extends RecyclerView.Adapter<CommentChildAdapter.CommentChildHolder> {
    private Context mContext;
    private List<Comment> listComment;

    public CommentChildAdapter(Context mContext, List<Comment> listComment) {
        this.mContext = mContext;
        this.listComment = listComment;
    }

    public CommentChildAdapter(){

    }


    @NonNull
    @Override
    public CommentChildAdapter.CommentChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new CommentChildAdapter.CommentChildHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentChildAdapter.CommentChildHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return listComment == null ? 0 : listComment.size();
    }

    public class CommentChildHolder extends BaseHolder {
        private ImageView avatar;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvComment;
        private TextView tvCommentChild;
        private TextView answerBtn;

        public CommentChildHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvCommentChild = itemView.findViewById(R.id.tvCommentChild);
            answerBtn = itemView.findViewById(R.id.tvAnswer);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            tvCommentChild.setVisibility(View.GONE);
            answerBtn.setVisibility(View.GONE);
            Comment comment = listComment.get(position);
            Account account = comment.getUser();
            setImageUrl(account.getImageProfile(), avatar);
            if (null != account.getFullname()) {
                tvName.setText(account.getFullname());
            } else {
                tvName.setText(account.getUsername());
            }
            tvComment.setText(comment.getContent());
            tvTime.setText(comment.getCreated());
        }
    }
}
