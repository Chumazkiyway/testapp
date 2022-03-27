package com.chumak.testapp.data.source.api

import com.chumak.testapp.data.source.responses.UserDetailsResponse
import com.chumak.testapp.data.source.responses.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// "https://api.github.com/users"
interface UserServiceApi {
    @GET("/users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<UserResponse>


    @GET("/users/{login}")
    suspend fun getUserDetails(
        @Path("login") login: String
    ): UserDetailsResponse

}