package com.chumak.testapp.data.source

import com.chumak.testapp.data.source.api.UserServiceApi
import com.chumak.testapp.data.source.responses.UserDetailsResponse
import com.chumak.testapp.data.source.responses.UserResponse

interface UserDataSource {
    suspend fun getUsers(since: Int, perPage: Int): List<UserResponse>
    suspend fun getUserDetails(login: String): UserDetailsResponse
}

class UserDataSourceImpl(
    private val api: UserServiceApi
) : UserDataSource {

    override suspend fun getUsers(since: Int, perPage: Int): List<UserResponse> {
        return api.getUsers(since, perPage)
    }

    override suspend fun getUserDetails(login: String): UserDetailsResponse {
        return api.getUserDetails(login = login)
    }

}