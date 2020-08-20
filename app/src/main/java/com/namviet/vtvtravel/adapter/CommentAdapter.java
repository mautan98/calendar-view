package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.listener.PostCommentListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Comment;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.dialog.CommentDialogFragment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private MainActivity mActivity;
    private List<Comment> listComment;
    PostCommentListener mPostCommentListener;

    public CommentAdapter(MainActivity mContext, List<Comment> listComment, PostCommentListener postCommentListener) {
        this.mActivity = mContext;
        this.listComment = listComment;
        this.mPostCommentListener = postCommentListener;
    }


    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.item_comment, parent, false);
        return new CommentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return listComment == null ? 0 : listComment.size();
    }

    public class CommentHolder extends BaseHolder {
        private ImageView avatar;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvComment;
        private RecyclerView childCommentRv;
        private TextView tvAnswer;
        private TextView tvCommentChild;

        public CommentHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvComment = itemView.findViewById(R.id.tvComment);
            childCommentRv = itemView.findViewById(R.id.rv_child_comment);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
            tvCommentChild = itemView.findViewById(R.id.tvCommentChild);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
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
            tvCommentChild.setVisibility(View.GONE);

            CommentChildAdapter commentChildAdapter = new CommentChildAdapter(mActivity, listComment.get(position).getChildren());
            childCommentRv.setNestedScrollingEnabled(false);
            childCommentRv.setLayoutManager(new LinearLayoutManager(mActivity));
            childCommentRv.setAdapter(commentChildAdapter);

            tvAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentDialogFragment commentDialogFragment = CommentDialogFragment.newInstance(comment);
                    commentDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                    commentDialogFragment.setPostCommentListener(mPostCommentListener);
                }
            });
        }
    }
}
