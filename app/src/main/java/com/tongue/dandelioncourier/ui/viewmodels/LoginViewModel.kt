package com.tongue.dandelioncourier.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tongue.dandelioncourier.data.DriverRemoteRepository
import com.tongue.dandelioncourier.data.domain.Driver
import com.tongue.dandelioncourier.ui.states.LoginUiState
import com.tongue.dandelioncourier.ui.states.PresentationUiState
import com.tongue.dandelioncourier.utils.AppLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel()  {

    companion object{
        const val TAG = "LoginViewModel"
    }

    private val driverRemoteRepository = DriverRemoteRepository()
    private val _uiState = MutableSharedFlow<LoginUiState>()
    var uiState : SharedFlow<LoginUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

    fun login(username: String, password: String){
        AppLog.i(TAG,"Login...")
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val jwt = driverRemoteRepository.login(Driver(username))
                println(jwt)
                driverRemoteRepository.initState(jwt)
                _uiState.emit(LoginUiState.LoginSuccessful(jwt))
            } catch (e: Exception) {
                AppLog.d(TAG,"$e")
                _uiState.emit(LoginUiState.WrongCredentials("$e"))
            }
        }
    }

}