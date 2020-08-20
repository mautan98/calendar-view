package com.namviet.vtvtravel.response;

public class ScheduleResponse extends BaseResponse {
    private ScheduleData data;

    public ScheduleData getData() {
        return data;
    }

    public void setData(ScheduleData data) {
        this.data = data;
    }
}
