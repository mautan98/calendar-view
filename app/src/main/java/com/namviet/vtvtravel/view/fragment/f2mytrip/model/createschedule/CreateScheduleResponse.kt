package com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule

import com.google.gson.annotations.SerializedName
import com.namviet.vtvtravel.response.BaseResponse

data class CreateScheduleResponse(

	@field:SerializedName("isDebug")
	val isDebug: Boolean? = null,

	@field:SerializedName("data")
	val dataCreateTrips: DataCreateTrips? = null,

	@field:SerializedName("requestId")
	val requestId: Any? = null,

	): BaseResponse()