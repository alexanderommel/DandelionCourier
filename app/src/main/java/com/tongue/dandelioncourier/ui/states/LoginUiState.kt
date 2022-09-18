package com.tongue.dandelioncourier.ui.states

import com.tongue.dandelioncourier.data.model.DriverAuthentication

sealed class LoginUiState {
    data class WrongCredentials(val message: String): LoginUiState()
    data class LoginSuccessful(val driverAuthentication: DriverAuthentication): LoginUiState()
}