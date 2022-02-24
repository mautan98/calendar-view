package com.namviet.vtvtravel.model.f2event;

import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;

public class OnDoneFilterOption {
    private FilterByCodeResponse filterByCodeResponse;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public OnDoneFilterOption(FilterByCodeResponse filterByCodeResponse, int position) {
        this.filterByCodeResponse = filterByCodeResponse;
        this.position = position;
    }

    public OnDoneFilterOption(FilterByCodeResponse filterByCodeResponse) {
        this.filterByCodeResponse = filterByCodeResponse;
    }

    public FilterByCodeResponse getFilterByCodeResponse() {
        return filterByCodeResponse;
    }

    public void setFilterByCodeResponse(FilterByCodeResponse filterByCodeResponse) {
        this.filterByCodeResponse = filterByCodeResponse;
    }
}
