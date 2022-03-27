package com.chumak.testapp.ui.di

import com.chumak.testapp.data.mappers.UserDetailsMapper
import com.chumak.testapp.data.mappers.UserMapper
import com.chumak.testapp.data.repository.UserRepositoryImpl
import com.chumak.testapp.data.source.UserDataSource
import com.chumak.testapp.data.source.UserDataSourceImpl
import com.chumak.testapp.data.source.api.UserServiceApi
import com.chumak.testapp.domain.repository.UsersRepository
import com.chumak.testapp.domain.usecases.GetUserDetailsUseCase
import com.chumak.testapp.domain.usecases.GetUsersUseCase
import com.chumak.testapp.ui.screens.details.UserDetailsViewModel
import com.chumak.testapp.ui.screens.list.UserListViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val networkModule = module {
    single<Gson> { GsonBuilder().create() }
    factory { GsonConverterFactory.create(get()) }
    single {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(get<GsonConverterFactory>())
            .client(okHttpClient)
            .build()
    }
}

private val userModule = module {
    factory { get<Retrofit>().create(UserServiceApi::class.java) }
    factory { UserMapper() }
    factory { UserDetailsMapper() }

    factory<UserDataSource> { UserDataSourceImpl(get()) }
    factory<UsersRepository> { UserRepositoryImpl(get(), get(), get()) }
    factory { GetUsersUseCase(get()) }
    factory { GetUserDetailsUseCase(get()) }

    viewModel { UserListViewModel(get()) }
    viewModel { args ->
        UserDetailsViewModel(
            args.component1(),
            get()
        )
    }
}

val modules = listOf(
    networkModule,
    userModule
)
