package com.tongue.dandelioncourier.ui.states

import com.tongue.dandelioncourier.data.model.Authentication

sealed class PresentationUiState {
    data class AuthenticationPreferencesFound(val authentication: Authentication): PresentationUiState()
    object AuthenticationPreferencesNotFound: PresentationUiState()
}