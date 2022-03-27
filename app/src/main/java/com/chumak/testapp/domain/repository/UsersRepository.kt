package com.chumak.testapp.domain.repository

import com.chumak.testapp.domain.model.User
import com.chumak.testapp.domain.model.UserDetails

interface UsersRepository {
    suspend fun getUsers(since: Int, perPage: Int): List<User>
    suspend fun getUserDetails(login: String): UserDetails
}