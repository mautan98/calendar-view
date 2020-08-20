package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.GroupSchedule;
import com.namviet.vtvtravel.model.Schedule;

import java.util.List;

public class DetailScheduleCreateData {
    private GroupSchedule restaurants;
    private GroupSchedule hotels;
    private GroupSchedule centers;

    public GroupSchedule getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(GroupSchedule restaurants) {
        this.restaurants = restaurants;
    }

    public GroupSchedule getPlaces() {
        return hotels;
    }

    public void setPlaces(GroupSchedule hotels) {
        this.hotels = hotels;
    }

    public GroupSchedule getCenters() {
        return centers;
    }

    public void setCenters(GroupSchedule centers) {
        this.centers = centers;
    }
}
