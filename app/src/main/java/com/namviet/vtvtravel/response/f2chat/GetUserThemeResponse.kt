package com.namviet.vtvtravel.response.f2chat

import com.google.gson.annotations.SerializedName
import com.namviet.vtvtravel.response.BaseResponse

data class GetUserThemeResponse(
        @SerializedName("data")
        val data: Content
) : BaseResponse()