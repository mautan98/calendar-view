package com.namviet.vtvtravel.response.f2searchmain.result;

import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class ResultVideoSearch extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{

        private String total;
        private List<Travel> items;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<Travel> getItems() {
            return items;
        }

        public void setItems(List<Travel> items) {
            this.items = items;
        }
    }
}
