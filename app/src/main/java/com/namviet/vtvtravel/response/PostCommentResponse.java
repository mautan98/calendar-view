package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.Comment;

public class PostCommentResponse extends BaseResponse {
    private Comment data;

    public Comment getData() {
        return data;
    }

    public void setData(Comment data) {
        this.data = data;
    }
}
