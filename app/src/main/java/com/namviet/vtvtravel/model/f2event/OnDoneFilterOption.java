package com.namviet.vtvtravel.model.f2event;

import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;

public class OnDoneFilterOption {
    private FilterByCodeResponse filterByCodeResponse;

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
