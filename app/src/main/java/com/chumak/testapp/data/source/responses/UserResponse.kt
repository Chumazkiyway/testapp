package com.chumak.testapp.data.source.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("login") val login: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
)