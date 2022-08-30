package com.tongue.dandelioncourier.ui.states

import com.tongue.dandelioncourier.data.domain.Authentication

sealed class PresentationUiState {
    data class AuthenticationPreferencesFound(val authentication: Authentication): PresentationUiState()
    object AuthenticationPreferencesNotFound: PresentationUiState()
}