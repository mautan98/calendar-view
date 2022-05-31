package com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule

import com.google.gson.annotations.SerializedName

class BodyCreateTrip {

    @field:SerializedName("name")
    var name:String?= null

    @field:SerializedName("description")
    var description:String?= null

    @field:SerializedName("placeCheckInId")
    var placeCheckInId: String? = null

    @field:SerializedName("placeCheckOutId")
    var placeCheckOutId: String? = null

    @field:SerializedName("startAt")
    var startAt: String? = null

    @field:SerializedName("endAt")
    var endAt: String? = null

    @field:SerializedName("adults")
    var adults: Int? = null

    @field:SerializedName("children")
    var children: Int? = null

    @field:SerializedName("infant")
    var infant: Int? = null

    @field:SerializedName("type")
    var type: String? = null

}