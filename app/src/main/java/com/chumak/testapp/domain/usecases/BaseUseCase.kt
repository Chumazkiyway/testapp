package com.chumak.testapp.domain.usecases

interface BaseUseCase<Input, Output> {
    suspend fun execute(params: Input?): Output
}
