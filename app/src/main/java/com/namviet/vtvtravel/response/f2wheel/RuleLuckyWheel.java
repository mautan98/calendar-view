package com.namviet.vtvtravel.response.f2wheel;

import com.namviet.vtvtravel.response.BaseResponse;

public class RuleLuckyWheel extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
