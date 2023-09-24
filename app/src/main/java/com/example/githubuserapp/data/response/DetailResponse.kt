package com.example.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

)
