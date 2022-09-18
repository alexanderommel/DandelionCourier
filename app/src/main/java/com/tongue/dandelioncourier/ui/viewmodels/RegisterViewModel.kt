package com.tongue.dandelioncourier.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tongue.dandelioncourier.data.DriverRemoteRepository
import com.tongue.dandelioncourier.data.model.RegistrationForm
import com.tongue.dandelioncourier.ui.states.RegistrationUiState
import com.tongue.dandelioncourier.utils.AppLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    companion object{
        const val TAG = "RegisterViewModel"
    }

    private val driverRemoteRepository = DriverRemoteRepository()
    private val _uiState = MutableSharedFlow<RegistrationUiState>()
    var uiState : SharedFlow<RegistrationUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

    fun register(registrationForm: RegistrationForm) {
        AppLog.i(TAG, "Register...")
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val driver = driverRemoteRepository.register(registrationForm)
                _uiState.emit(RegistrationUiState.SuccessfulRegistration)
            } catch (e: Exception) {
                AppLog.d(TAG,"$e")
                _uiState.emit(RegistrationUiState.ErrorFound)
            }
        }
    }
}