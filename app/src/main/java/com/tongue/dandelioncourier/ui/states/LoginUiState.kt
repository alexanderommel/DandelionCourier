package com.tongue.dandelioncourier.ui.states

sealed class LoginUiState {
    data class WrongCredentials(val message: String): LoginUiState()
    data class LoginSuccessful(val jwt: String): LoginUiState()
}