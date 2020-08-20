package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.Schedule;

public class DetailScheduleResponse extends BaseResponse {
    private Schedule data;

    public Schedule getData() {
        return data;
    }

    public void setData(Schedule data) {
        this.data = data;
    }
}
