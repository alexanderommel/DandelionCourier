package com.tongue.dandelioncourier.ui.states

sealed class RegistrationUiState {
    object SuccessfulRegistration: RegistrationUiState()
    object ErrorFound: RegistrationUiState()
}