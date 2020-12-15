package com.namviet.vtvtravel.model.f2event;

import java.util.ArrayList;

public class OnChooseVoucherToRoll {
    private ArrayList<String> stringArrayList;

    public OnChooseVoucherToRoll(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public ArrayList<String> getStringArrayList() {
        return stringArrayList;
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }
}
