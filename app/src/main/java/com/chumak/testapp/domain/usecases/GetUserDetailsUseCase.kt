package com.chumak.testapp.domain.usecases

import com.chumak.testapp.domain.model.UserDetails
import com.chumak.testapp.domain.repository.UsersRepository


class GetUserDetailsUseCase(
    private val repository: UsersRepository
) : BaseUseCase<String, UserDetails>{

    override suspend fun execute(params: String?): UserDetails {
        return repository.getUserDetails(login = params ?: "")
    }
}
