package com.namviet.vtvtravel.response.f2searchmain.result;

import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class ResultVideoSearch extends BaseResponse {
    private boolean isLoadMore;

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{

        private String total;
        private List<Video> items;
        private String more_link;
        private boolean approximately;

        public boolean getApproximately() {
            return approximately;
        }

        public String getMore_link() {
            return more_link;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<Video> getItems() {
            return items;
        }

        public void setItems(List<Video> items) {
            this.items = items;
        }
    }
}
