package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TripItem(

	@field:SerializedName("creator")
	val creator: Creator? = null,

	@field:SerializedName("userList")
	val userList: List<UserListItem?>? = null,

	@field:SerializedName("checkRights")
	val checkRights: @RawValue Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("bannerUrl")
	val bannerUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("schedulePlaceByDays")
	val schedulePlaceByDays: @RawValue  Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("estimatedCost")
	val estimatedCost: Int? = null,

	@field:SerializedName("endAt")
	val endAt: Long? = null,

	@field:SerializedName("startAt")
	val startAt: Long? = null,

	@field:SerializedName("numberPeople")
	val numberPeople: Int? = null
) : Parcelable