package com.chumak.testapp.data.source.responses

import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("login") val login: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("blog") val blog: String?,
    @SerializedName("public_repos") val publicRepos: Int?,
    @SerializedName("public_gists") val publicGists: Int?,
    @SerializedName("followers") val followers: Int?,
)