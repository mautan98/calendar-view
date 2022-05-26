package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sort(

	@field:SerializedName("unsorted")
	val isUnsorted: Boolean? = null,

	@field:SerializedName("sorted")
	val isSorted: Boolean? = null,

	@field:SerializedName("empty")
	val isEmpty: Boolean? = null
) : Parcelable