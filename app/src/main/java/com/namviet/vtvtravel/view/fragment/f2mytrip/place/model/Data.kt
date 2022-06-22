package com.namviet.vtvtravel.view.fragment.f2mytrip.place.model

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("checkRights")
	var checkRights: String? = null,

	@field:SerializedName("content")
	var content: List<ItemPlaces?>? = null
)