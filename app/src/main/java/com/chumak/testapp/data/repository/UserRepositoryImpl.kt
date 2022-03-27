package com.chumak.testapp.data.repository

import com.chumak.testapp.data.mappers.UserDetailsMapper
import com.chumak.testapp.data.mappers.UserMapper
import com.chumak.testapp.data.source.UserDataSource
import com.chumak.testapp.domain.model.User
import com.chumak.testapp.domain.model.UserDetails
import com.chumak.testapp.domain.repository.UsersRepository

class UserRepositoryImpl(
    private val dataSource: UserDataSource,
    private val userMapper: UserMapper,
    private val userDetailsMapper: UserDetailsMapper
) : UsersRepository {

    override suspend fun getUsers(since: Int, perPage: Int): List<User> {
        return dataSource.getUsers(since, perPage).map { response ->
            userMapper.mapFromBean(response)
        }
    }

    override suspend fun getUserDetails(login: String): UserDetails {
        return dataSource.getUserDetails(login).let { response ->
            userDetailsMapper.mapFromBean(response)
        }
    }
}