package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("last")
	val isLast: Boolean? = null,

	@field:SerializedName("numberOfElements")
	val numberOfElements: Int? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("pageable")
	val pageable: Pageable? = null,

	@field:SerializedName("sort")
	val sort: Sort? = null,

	@field:SerializedName("content")
	val listTrip: List<TripItem?>? = null,

	@field:SerializedName("first")
	val isFirst: Boolean? = null,

	@field:SerializedName("totalElements")
	val totalElements: Int? = null,

	@field:SerializedName("empty")
	val isEmpty: Boolean? = null
) : Parcelable