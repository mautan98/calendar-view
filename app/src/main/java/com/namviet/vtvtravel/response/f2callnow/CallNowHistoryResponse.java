package com.namviet.vtvtravel.response.f2callnow;

import com.namviet.vtvtravel.model.f2callnow.CallNowHistory;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class CallNowHistoryResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<CallNowHistory> content;

        public List<CallNowHistory> getContent() {
            return content;
        }

        public void setContent(List<CallNowHistory> content) {
            this.content = content;
        }
    }
}
