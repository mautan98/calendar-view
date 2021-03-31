package com.namviet.vtvtravel.response.travelnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.ArrayList;

public class PlaceNearByResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @Expose
        @SerializedName("items")
        private ArrayList<Travel> places;

        public ArrayList<Travel> getPlaces() {
            return places;
        }

        public void setPlaces(ArrayList<Travel> places) {
            this.places = places;
        }
    }
}
