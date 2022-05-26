package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.namviet.vtvtravel.response.BaseResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyTripsResponse(

	@field:SerializedName("isDebug")
	val isDebug: Boolean? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("requestId")
	val requestId: Any? = null,

	@field:SerializedName("success")
	val isSuccess: Boolean? = null,

	@field:SerializedName("message")
	val message: Any? = null
) : BaseResponse(), Parcelable
