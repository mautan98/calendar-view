package com.namviet.vtvtravel.response;

public class CommentResponse extends BaseResponse {
    private CommentData data;

    public CommentData getData() {
        return data;
    }

    public void setData(CommentData data) {
        this.data = data;
    }
}
