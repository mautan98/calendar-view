package com.namviet.vtvtravel.response.f2comment;

import com.namviet.vtvtravel.response.BaseResponse;

public class UpdateCommentResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private String id;
        private String contentType;
        private String contentId;
        private String userId;
    }
}
