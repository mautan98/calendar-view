package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SchedulePlaceByDaysItem(

	@field:SerializedName("totalDistance")
	val totalDistance: Int? = null,

	@field:SerializedName("day")
	val day: Long? = null,

	@field:SerializedName("totalPlace")
	val totalPlace: Int? = null,

	@field:SerializedName("schedulePlaceId")
	val schedulePlaceId: String? = null,

	@field:SerializedName("logoUrl")
	val logoUrl: String? = null
):Parcelable