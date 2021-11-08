
package com.namviet.vtvtravel.view.f3.deal.model.dealcampaign;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealCampaignDetail {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
