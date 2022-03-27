package com.chumak.testapp.domain.usecases

import com.chumak.testapp.domain.model.User
import com.chumak.testapp.domain.repository.UsersRepository
import java.lang.Exception


class GetUsersUseCase(
    private val repository: UsersRepository
) : BaseUseCase<GetUsersParams, List<User>>{

    override suspend fun execute(params: GetUsersParams?): List<User> {
        if(params == null) {
            throw Exception("params must not be null")
        }
        return repository.getUsers(params.since, params.perPage)
    }
}

data class GetUsersParams(
    val since: Int,
    val perPage: Int
)