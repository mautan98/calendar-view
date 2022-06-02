package com.namviet.vtvtravel.view.fragment.f2mytrip.model.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceScheduleResponse(

    @field:SerializedName("isDebug")
	val isDebug: Boolean? = null,

    @field:SerializedName("data")
	val data: TripItem? = null,

    @field:SerializedName("requestId")
	val requestId: String? = null,

) : BaseResponse(), Parcelable