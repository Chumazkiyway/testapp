package com.chumak.testapp.domain.model

data class UserDetails(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val blog: String,
    val publicRepos: Int,
    val publicGists: Int,
    val followers: Int,
)