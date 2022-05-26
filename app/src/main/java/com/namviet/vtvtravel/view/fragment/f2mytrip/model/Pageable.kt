package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pageable(

	@field:SerializedName("paged")
	val paged: Boolean? = null,

	@field:SerializedName("pageNumber")
	val pageNumber: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("pageSize")
	val pageSize: Int? = null,

	@field:SerializedName("unpaged")
	val unpaged: Boolean? = null,

	@field:SerializedName("sort")
	val sort: Sort? = null
) : Parcelable