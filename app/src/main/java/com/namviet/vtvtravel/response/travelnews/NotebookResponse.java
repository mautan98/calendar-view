package com.namviet.vtvtravel.response.travelnews;

import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class NotebookResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private String limit;
        private String page;
        private String hasMore;
        private String more_link;
        private List<NewsCategoryResponse.Item> category_notebook;
        private List<Travel> items;

        public String getLimit() {
            return limit;
        }

        public String getPage() {
            return page;
        }

        public String getHasMore() {
            return hasMore;
        }

        public String getMore_link() {
            return more_link;
        }

        public List<NewsCategoryResponse.Item> getCategory_notebook() {
            return category_notebook;
        }

        public List<Travel> getItems() {
            return items;
        }
    }
}
