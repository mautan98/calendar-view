package com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule

import com.google.gson.annotations.SerializedName

data class DataCreateTrips(

	@field:SerializedName("placeCheckOutId")
	val placeCheckOutId: String? = null,

	@field:SerializedName("created")
	val created: Long? = null,

	@field:SerializedName("adults")
	val adults: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("endAt")
	val endAt: Long? = null,

	@field:SerializedName("infant")
	val infant: Int? = null,

	@field:SerializedName("children")
	val children: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("placeCheckInId")
	val placeCheckInId: String? = null,

	@field:SerializedName("modified")
	val modified: Long? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("startAt")
	val startAt: Long? = null,

	@field:SerializedName("status")
	val status: Int? = null
)