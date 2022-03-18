package com.namviet.vtvtravel.view.f3.deal.model;

public class Rank {
    private String phoneNumber;
    private String timeKeep;

    public Rank(String phoneNumber, String timeKeep) {
        this.phoneNumber = phoneNumber;
        this.timeKeep = timeKeep;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTimeKeep() {
        return timeKeep;
    }

    public void setTimeKeep(String timeKeep) {
        this.timeKeep = timeKeep;
    }
}
