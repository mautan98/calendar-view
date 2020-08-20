package com.namviet.vtvtravel.response.f2comment;

import com.namviet.vtvtravel.response.BaseResponse;

public class CreateCommentResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private String id;
        private String contentId;
        private String userId;
        private String parentId;
        private String content;
        private String created;

        public String getId() {
            return id;
        }

        public String getContentId() {
            return contentId;
        }

        public String getUserId() {
            return userId;
        }

        public String getParentId() {
            return parentId;
        }

        public String getContent() {
            return content;
        }

        public String getCreated() {
            return created;
        }
    }
}
