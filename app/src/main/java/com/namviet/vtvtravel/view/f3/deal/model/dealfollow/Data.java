package com.namviet.vtvtravel.view.f3.deal.model.dealfollow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data {
    @Expose
    @SerializedName("content")
    private ArrayList<DealFollow> dealFollows;

    public ArrayList<DealFollow> getDealFollows() {
        return dealFollows;
    }

    public void setDealFollows(ArrayList<DealFollow> dealFollows) {
        this.dealFollows = dealFollows;
    }
}
