package com.namviet.vtvtravel.model;

public class MyLocation {
    private String cityName;
    private String address;
    private String countryName;

    private double lat;
    private double log;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public MyLocation(String cityName, String address, String countryName, double lat, double log) {
        this.cityName = cityName;
        this.address = address;
        this.countryName = countryName;
        this.lat = lat;
        this.log = log;
    }
}
