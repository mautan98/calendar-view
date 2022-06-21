package com.namviet.vtvtravel.view.fragment.f2mytrip.place.model

import com.google.gson.annotations.SerializedName

data class PlacesScheduleItem(

	@field:SerializedName("note")
	var note: Any? = null,

	@field:SerializedName("durationMove")
	var durationMove: Int? = null,

	@field:SerializedName("created")
	var created: Long? = null,

	@field:SerializedName("placeId")
	var placeId: String? = null,

	@field:SerializedName("distancePlace")
	var distancePlace: Int? = null,

	@field:SerializedName("durationVisit")
	var durationVisit: Int? = null,

	@field:SerializedName("scheduleCustomId")
	var scheduleCustomId: String? = null,

	@field:SerializedName("logoUrl")
	var logoUrl: Any? = null,

	@field:SerializedName("typePlace")
	var typePlace: String? = null,

	@field:SerializedName("stt")
	var stt: Int? = null,

	@field:SerializedName("arrivalTime")
	var arrivalTime: Long? = null,

	@field:SerializedName("freeTime")
	var freeTime: Int? = null,

	@field:SerializedName("modified")
	var modified: Long? = null,

	@field:SerializedName("id")
	var id: String? = null,

	@field:SerializedName("detailLink")
	var detailLink: String? = null,

	@field:SerializedName("day")
	var day: Long? = null,

	@field:SerializedName("status")
	var status: Any? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("lat")
	var lat: Double? = null,

	@field:SerializedName("lng")
	var lng: Double? = null,

)