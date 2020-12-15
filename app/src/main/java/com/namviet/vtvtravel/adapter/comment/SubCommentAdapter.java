package com.namviet.vtvtravel.adapter.comment;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;

import java.util.List;

public class SubCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private ClickItem clickItem;
    private List<CommentResponse.Data.Comment> comments;

    public SubCommentAdapter(List<CommentResponse.Data.Comment> comments, Context context, ClickItem clickItem) {
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
        try {
            return comments.size();
        } catch (Exception e) {
            return 0;
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
        private RecyclerView rclChildComment;
        private LikeButton imgHeart;
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
            rclChildComment = itemView.findViewById(R.id.rclChildComment);
            imgHeart = itemView.findViewById(R.id.imgHeart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    clickItem.onClickItem(travels.get(position));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    clickItem.onLongClickItem(comments.get(position));
                    return false;
                }
            });

            tvReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickReply(comments.get(position));
                }
            });

            imgHeart.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickHeart();
                        }
                    }, 100);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickHeart();
                        }
                    }, 100);
                }
            });

//            imgHeart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        Account account = MyApplication.getInstance().getAccount();
//                        if (null != account && account.isLogin()) {
//                            clickItem.likeEvent(position);
//                        } else {
//                            LoginAndRegisterActivityNew.startScreen(context, 0, false);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

        }

        private void clickHeart(){
            try {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    clickItem.likeEvent(position);
                } else {
                    LoginAndRegisterActivityNew.startScreen(context, 0, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void bindItem(int position) {
            try {
                this.position = position;
                tvName.setText(comments.get(position).getUser().getFullname());
                tvComment.setText(comments.get(position).getContent());
                Glide.with(context).load(comments.get(position).getUser().getImageProfile()).apply(new RequestOptions().circleCrop()).into(imgAvatar);
                tvTime.setText(DateUtltils.convertTime(comments.get(position).getCreated()));
                tvCountLike.setText(comments.get(position).getLikeCount());
                if (comments.get(position).isLiked()) {
//                    imgHeart.setImageResource(R.drawable.f2_ic_red_heart);
                    imgHeart.setLiked(true);
                } else {
//                    imgHeart.setImageResource(R.drawable.f2_ic_gray_heart);
                    imgHeart.setLiked(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface ClickItem {
        void onClickItem(CommentResponse.Data.Comment comment);

        void onLongClickItem(CommentResponse.Data.Comment comment);

        void onClickReply(CommentResponse.Data.Comment comment);

        void likeEvent(int i);
    }
}
