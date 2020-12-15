package com.namviet.vtvtravel.response.f2chat

import com.google.gson.annotations.SerializedName
import com.namviet.vtvtravel.response.BaseResponse

data class GetListThemeResponse(
        @SerializedName("data")
        val data: Data
) : BaseResponse()

data class Data(
        val content: List<Content>
)

data class Content(
        val id: String,
        val name: String,
        val pathUri: String,
        val status: String,
        val created: String,
        val modified: String,
        var isClicked: Boolean = false
)