package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TripItem(

	@field:SerializedName("creator")
	var creator: Creator? = null,

	@field:SerializedName("userList")
	var userList: List<UserListItem?>? = null,

	@field:SerializedName("checkRights")
	var checkRights: String = "true",

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("bannerUrl")
	var bannerUrl: String? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("schedulePlaceByDays")
	var schedulePlaceByDays: List<SchedulePlaceByDaysItem?>? = null,

	@field:SerializedName("id")
	var id: String? = null,

	@field:SerializedName("estimatedCost")
	var estimatedCost: Long? = null,

	@field:SerializedName("endAt")
	var endAt: Long? = null,

	@field:SerializedName("startAt")
	var startAt: Long? = null,

	@field:SerializedName("numberPeople")
	var numberPeople: Int? = null
) : Parcelable