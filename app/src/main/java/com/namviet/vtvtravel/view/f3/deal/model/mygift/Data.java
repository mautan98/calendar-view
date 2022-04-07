package com.namviet.vtvtravel.view.f3.deal.model.mygift;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data {
    @Expose
    @SerializedName("content")
    private ArrayList<MyGift> myGifts;

    public ArrayList<MyGift> getMyGifts() {
        return myGifts;
    }

    public void setMyGifts(ArrayList<MyGift> myGifts) {
        this.myGifts = myGifts;
    }
}
