package com.namviet.vtvtravel.response.f2review;

import com.namviet.vtvtravel.response.BaseResponse;

public class CreateReviewResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private String id;

        public String getId() {
            return id;
        }
    }
}
