package com.namviet.vtvtravel.view.fragment.f2mytrip.place.model

import com.google.gson.annotations.SerializedName

data class ItemPlaces(

    @field:SerializedName("day")
    var day: Long? = null,

    @field:SerializedName("totalPlace")
    var totalPlace: Int? = null,

    @field:SerializedName("schedulePlaceList")
    var schedulePlaceList: MutableList<PlacesScheduleItem>? = null,

    var isSelected: Boolean = false
)
