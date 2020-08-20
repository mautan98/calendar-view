package com.namviet.vtvtravel.response;

public class MenuResponse extends BaseResponse {
    private MenuData data;

    public MenuData getData() {
        return data;
    }

    public void setData(MenuData data) {
        this.data = data;
    }
}
