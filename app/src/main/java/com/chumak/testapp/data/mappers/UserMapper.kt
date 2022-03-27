package com.chumak.testapp.data.mappers

import com.chumak.testapp.data.source.responses.UserResponse
import com.chumak.testapp.domain.model.User

class UserMapper : BaseMapper<UserResponse, User> {
    override fun mapFromBean(src: UserResponse): User {
        return User(
            id = src.id ?: 0,
            login = src.login ?: "",
            avatarUrl = src.avatarUrl ?: "",
        )
    }
}