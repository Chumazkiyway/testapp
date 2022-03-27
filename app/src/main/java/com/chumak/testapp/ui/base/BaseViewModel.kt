package com.chumak.testapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chumak.testapp.ui.common.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    private val _isErrorLiveData = SingleLiveEvent<Boolean>()
    val isErrorLiveData: LiveData<Boolean> = _isErrorLiveData

    protected val defaultErrorHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable)
        _isErrorLiveData.value = true
    }

    fun onErrorDismiss() {
        _isErrorLiveData.value = false
    }
}