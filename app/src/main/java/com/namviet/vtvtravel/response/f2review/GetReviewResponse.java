package com.namviet.vtvtravel.response.f2review;

import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class GetReviewResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private List<Content> content;

        public List<Content> getContent() {
            return content;
        }

        public class Content {
            private String id;
            private String userId;
            private String contentId;
            private String content;
            private String created;
            private String distanceTime;
            private String totalCommentInPost;
            private Account user;
            private String postRate;
            private List<String> galleryUris;

            private boolean isLiked;
            private String likeCount;
            private String contentType;

            public String getContentType() {
                return contentType;
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

            public List<String> getGalleryUris() {
                return galleryUris;
            }

            public void setGalleryUris(List<String> galleryUris) {
                this.galleryUris = galleryUris;
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

            public String getPostRate() {
                return postRate;
            }
        }
    }
}
