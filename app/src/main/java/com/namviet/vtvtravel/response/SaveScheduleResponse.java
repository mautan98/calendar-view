package com.namviet.vtvtravel.response;

public class SaveScheduleResponse extends BaseResponse {
    private SaveScheduleData data;

    public SaveScheduleData getData() {
        return data;
    }

    public void setData(SaveScheduleData data) {
        this.data = data;
    }
}
