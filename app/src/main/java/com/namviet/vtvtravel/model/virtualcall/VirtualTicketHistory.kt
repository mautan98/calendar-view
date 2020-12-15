package com.namviet.vtvtravel.model.virtualcall

data class VirtualTicketHistory(
        val id: String,
        val field: String,
        val origin_value: String,
        val new_value: String,
        val fullname: String,
        val mobile: String,
        val user_system_type_code: String,
        val note: String,
        val created: String
)
