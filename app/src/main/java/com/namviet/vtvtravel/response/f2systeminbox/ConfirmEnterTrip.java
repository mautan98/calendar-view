package com.namviet.vtvtravel.response.f2systeminbox;

import com.namviet.vtvtravel.response.BaseResponse;

public class ConfirmEnterTrip extends BaseResponse {
    private String tripID;

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }
}
