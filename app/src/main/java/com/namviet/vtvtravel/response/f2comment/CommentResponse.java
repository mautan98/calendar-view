package com.namviet.vtvtravel.response.f2comment;

import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class CommentResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private List<Comment> content;

        public List<Comment> getContent() {
            return content;
        }

        public class Comment{
            private String status;
            private String id;
            private String userId;
            private String contentId;
            private String content;
            private String created;
            private String distanceTime;
            private String totalCommentInPost;
            private Account user;
            private List<Comment> children;
            private boolean isLiked;
            private String likeCount;

            private String adminName;
            private String adminAvatar;

            public String getAdminAvatar() {
                return adminAvatar;
            }

            public void setAdminAvatar(String adminAvatar) {
                this.adminAvatar = adminAvatar;
            }

            public String getAdminName() {
                return adminName;
            }

            public void setAdminName(String adminName) {
                this.adminName = adminName;
            }

            public boolean isLiked() {
                return isLiked;
            }

            public void setLiked(boolean liked) {
                isLiked = liked;
            }

            public String getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(String likeCount) {
                this.likeCount = likeCount;
            }

            public String getId() {
                return id;
            }

            public String getUserId() {
                return userId;
            }

            public String getContentId() {
                return contentId;
            }

            public String getContent() {
                return content;
            }

            public String getCreated() {
                return created;
            }

            public String getDistanceTime() {
                return distanceTime;
            }

            public String getTotalCommentInPost() {
                return totalCommentInPost;
            }

            public Account getUser() {
                return user;
            }

            public List<Comment> getChildren() {
                return children;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
