package com.namviet.vtvtravel.model.virtualcall

import com.namviet.vtvtravel.response.BaseResponse

class ListTicketHistoryResponse : BaseResponse() {
    val data: Data? = null

    class Data {
        val items: ArrayList<VirtualTicketHistory>? = null
    }
}