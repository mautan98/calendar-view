package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.namviet.vtvtravel.response.BaseResponse
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class MyTripsResponse(

	@field:SerializedName("isDebug")
	val isDebug: Boolean? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("requestId")
	val requestId: @RawValue Any? = null,

	@field:SerializedName("success")
	val isSuccess: Boolean? = null,

	@field:SerializedName("message")
	val message: @RawValue  Any? = null
) : BaseResponse(), Parcelable
