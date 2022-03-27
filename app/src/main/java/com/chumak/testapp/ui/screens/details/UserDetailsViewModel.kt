package com.chumak.testapp.ui.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chumak.testapp.domain.model.UserDetails
import com.chumak.testapp.domain.usecases.GetUserDetailsUseCase
import com.chumak.testapp.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserDetailsViewModel(
    login: String,
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) :
    BaseViewModel() {
    private val _stateLiveData = MutableLiveData<UserDetailsState>().apply {
        value = UserDetailsState.empty()
    }
    val stateLiveData: LiveData<UserDetailsState> = _stateLiveData

    init {
        viewModelScope.launch(Dispatchers.Main + defaultErrorHandler) {
            try {
                _stateLiveData.value = stateLiveData.value?.copy(isLoading = true)

                val details = withContext(Dispatchers.IO) {
                    getUserDetailsUseCase.execute(login)
                }

                _stateLiveData.value = stateLiveData.value?.copy(details = details)
            } finally {
                _stateLiveData.value = stateLiveData.value?.copy(isLoading = false)
            }

        }
    }
}


data class UserDetailsState(
    val details: UserDetails,
    val isLoading: Boolean
) {
    companion object {
        fun empty(): UserDetailsState {
            return UserDetailsState(
                details = UserDetails(
                    login = "",
                    id = 0,
                    avatarUrl = "",
                    blog = "",
                    publicRepos = 0,
                    publicGists = 0,
                    followers = 0,
                ),
                isLoading = false
            )
        }
    }
}