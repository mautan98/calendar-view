package com.namviet.vtvtravel.view.fragment.f2mytrip.place.model

import com.google.gson.annotations.SerializedName
import com.namviet.vtvtravel.response.BaseResponse

data class DetailPlacesResponse(

	@field:SerializedName("isDebug")
	var isDebug: Boolean? = null,

	@field:SerializedName("data")
	var data: Data? = null,

	@field:SerializedName("requestId")
	var requestId: Any? = null,

): BaseResponse()