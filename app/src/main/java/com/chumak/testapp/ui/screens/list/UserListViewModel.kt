package com.chumak.testapp.ui.screens.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chumak.testapp.domain.model.User
import com.chumak.testapp.domain.usecases.GetUsersParams
import com.chumak.testapp.domain.usecases.GetUsersUseCase
import com.chumak.testapp.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val PER_PAGE = 10
const val MAX_SIZE = 200

class UserListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    private val _stateLiveData = MutableLiveData<UserListState>().apply {
        value = UserListState.empty()
    }
    val stateLiveData: LiveData<UserListState> = _stateLiveData

    private var since = 0
    private var job: Job? = null

    init {
        initialLoad()
    }

    fun initialLoad() {
        since = 0
        _stateLiveData.value = stateLiveData.value?.copy(list = emptyList())
        loadUsers()
    }

    fun loadUsers() {
        if (job?.isActive == true || (stateLiveData.value?.list?.size ?: 0) >= MAX_SIZE) {
            return
        }
        job = viewModelScope.launch(Dispatchers.Main + defaultErrorHandler) {
            try {
                _stateLiveData.value = stateLiveData.value?.copy(isLoading = true)

                val pageUsers = withContext(Dispatchers.IO) {
                    getUsersUseCase.execute(
                        params = GetUsersParams(
                            since = since,
                            perPage = PER_PAGE
                        )
                    )
                }
                val list = if (since == 0) {
                    pageUsers
                } else {
                    stateLiveData.value?.list?.union(pageUsers)?.toList() ?: emptyList()
                }
                since = if (pageUsers.isNotEmpty()) pageUsers.last().id else 0
                _stateLiveData.value = stateLiveData.value?.copy(list = list)
            } finally {
                _stateLiveData.value = stateLiveData.value?.copy(isLoading = false)
            }

        }
    }
}

data class UserListState(
    val list: List<User>,
    val isLoading: Boolean
) {
    companion object {
        fun empty(): UserListState {
            return UserListState(
                list = emptyList(),
                isLoading = false
            )
        }
    }
}