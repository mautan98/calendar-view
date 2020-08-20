package com.namviet.vtvtravel.response;

public class SearchResultResponse extends BaseResponse {
    private SearchResultData data;

    public SearchResultData getData() {
        return data;
    }

    public void setData(SearchResultData data) {
        this.data = data;
    }
}
