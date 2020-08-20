package com.namviet.vtvtravel.response.f2filter;

import com.namviet.vtvtravel.model.filter.ItemTab;
import com.namviet.vtvtravel.response.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class FilterByCodeResponse extends BaseResponse implements Serializable {
    private boolean typeOpen;
    private DistanceClass distanceClass;

    public DistanceClass getDistanceClass() {
        return distanceClass;
    }

    public void setDistanceClass(DistanceClass distanceClass) {
        this.distanceClass = distanceClass;
    }

    public boolean isTypeOpen() {
        return typeOpen;
    }

    public void setTypeOpen(boolean typeOpen) {
        this.typeOpen = typeOpen;
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable{
        private List<ItemTab> items;

        public List<ItemTab> getItems() {
            return items;
        }
    }
}
