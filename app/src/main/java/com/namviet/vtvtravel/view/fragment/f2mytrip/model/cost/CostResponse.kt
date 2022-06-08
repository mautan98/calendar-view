package com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost

import com.google.gson.annotations.SerializedName
import com.namviet.vtvtravel.response.BaseResponse

data class CostResponse(
    @field:SerializedName("data")
    val data: List<TypeCost?>? = null,
) : BaseResponse()