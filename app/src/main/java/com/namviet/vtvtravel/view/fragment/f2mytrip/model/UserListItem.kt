package com.namviet.vtvtravel.view.fragment.f2mytrip.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class UserListItem(

	@field:SerializedName("googleId")
	val googleId: @RawValue Any? = null,

	@field:SerializedName("birthday")
	val birthday: String? = null,

	@field:SerializedName("maxIncome")
	val maxIncome: Int? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("tokenFacebook")
	val tokenFacebook: @RawValue  Any? = null,

	@field:SerializedName("langCode")
	val langCode: String? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("channel")
	val channel: @RawValue  Any? = null,

	@field:SerializedName("sipTransportType")
	val sipTransportType: @RawValue  Any? = null,

	@field:SerializedName("isTravelingSupporter")
	val isTravelingSupporter: @RawValue  Any? = null,

	@field:SerializedName("password")
	val password: @RawValue  Any? = null,

	@field:SerializedName("icppNumber")
	val icppNumber: String? = null,

	@field:SerializedName("sipDomain")
	val sipDomain: @RawValue  Any? = null,

	@field:SerializedName("rankName")
	val rankName: @RawValue  Any? = null,

	@field:SerializedName("adminId")
	val adminId: @RawValue  Any? = null,

	@field:SerializedName("imageBackground")
	val imageBackground: String? = null,

	@field:SerializedName("modified")
	val modified: Long? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("packageName")
	val packageName: @RawValue  Any? = null,

	@field:SerializedName("tokenGoogle")
	val tokenGoogle: @RawValue  Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("deviceType")
	val deviceType: @RawValue  Any? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("sipPassword")
	val sipPassword: @RawValue  Any? = null,

	@field:SerializedName("created")
	val created: Long? = null,

	@field:SerializedName("oldPassword")
	val oldPassword: @RawValue  Any? = null,

	@field:SerializedName("facebookId")
	val facebookId: @RawValue  Any? = null,

	@field:SerializedName("mobile")
	val mobile: String? = null,

	@field:SerializedName("sipAccount")
	val sipAccount: @RawValue  Any? = null,

	@field:SerializedName("token")
	val token: @RawValue  Any? = null,

	@field:SerializedName("deviceToken")
	val deviceToken: @RawValue  Any? = null,

	@field:SerializedName("totalPoint")
	val totalPoint: @RawValue  Any? = null,

	@field:SerializedName("telcoCode")
	val telcoCode: String? = null,

	@field:SerializedName("accessKey")
	val accessKey: @RawValue  Any? = null,

	@field:SerializedName("packageCode")
	val packageCode: @RawValue  Any? = null,

	@field:SerializedName("imageProfile")
	val imageProfile: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("rankIcon")
	val rankIcon: @RawValue  Any? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: @RawValue  Any? = null
) : Parcelable