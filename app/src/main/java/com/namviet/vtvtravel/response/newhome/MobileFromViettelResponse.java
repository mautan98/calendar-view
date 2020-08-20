package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

public class MobileFromViettelResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private String mobile;

        public String getMobile() {
            return mobile;
        }
    }
}
