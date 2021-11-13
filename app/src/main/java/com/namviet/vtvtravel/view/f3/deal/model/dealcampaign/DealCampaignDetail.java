
package com.namviet.vtvtravel.view.f3.deal.model.dealcampaign;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;


public class DealCampaignDetail {

    @SerializedName("data")
    @Expose
    private Data data;
    private boolean dataLoaded;

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        this.dataLoaded = dataLoaded;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    private DealResponse dealByCampaign;

    public DealResponse getDealByCampaign() {
        return dealByCampaign;
    }

    public void setDealByCampaign(DealResponse dealByCampaign) {
        this.dealByCampaign = dealByCampaign;
    }

    public DealCampaignDetail() {
    }
}
