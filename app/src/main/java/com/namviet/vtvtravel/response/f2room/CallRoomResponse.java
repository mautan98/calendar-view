package com.namviet.vtvtravel.response.f2room;

import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.f2review.CreateReviewResponse;

public class CallRoomResponse extends BaseResponse {
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
