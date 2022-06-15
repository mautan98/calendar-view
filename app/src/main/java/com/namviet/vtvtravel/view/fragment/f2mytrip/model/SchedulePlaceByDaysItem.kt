package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SchedulePlaceByDaysItem(

	@field:SerializedName("totalDistance")
	var totalDistance: Int? = null,

	@field:SerializedName("day")
	var day: Long? = null,

	@field:SerializedName("totalPlace")
	var totalPlace: Int? = null,

	@field:SerializedName("schedulePlaceId")
	var schedulePlaceId: String? = null,

	@field:SerializedName("logoUrl")
	var logoUrl: String? = null,

	var removeAble: Boolean = true
):Parcelable