package com.chumak.testapp.data.mappers

import com.chumak.testapp.data.source.responses.UserDetailsResponse
import com.chumak.testapp.domain.model.UserDetails

class UserDetailsMapper : BaseMapper<UserDetailsResponse, UserDetails> {
    override fun mapFromBean(src: UserDetailsResponse): UserDetails {
        return UserDetails(
            login = src.login ?: "",
            id = src.id ?: 0,
            avatarUrl = src.avatarUrl ?: "",
            blog = src.blog ?: "",
            publicRepos = src.publicRepos ?: 0,
            publicGists = src.publicGists ?: 0,
            followers = src.followers ?: 0,
        )
    }
}