package com.namviet.vtvtravel.response.f2biglocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class PartBigLocationResponse extends BaseResponse {
    @Expose
    @SerializedName("data")
    private List<Travel> items;

    private String codeToSplit;

    public String getCodeToSplit() {
        return codeToSplit;
    }

    public void setCodeToSplit(String codeToSplit) {
        this.codeToSplit = codeToSplit;
    }

    public List<Travel> getItems() {
        return items;
    }

    public void setItems(List<Travel> items) {
        this.items = items;
    }
}
