package com.tongue.dandelioncourier.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tongue.dandelioncourier.data.model.Authentication
import com.tongue.dandelioncourier.ui.states.PresentationUiState
import com.tongue.dandelioncourier.utils.AppLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PresentationViewModel: ViewModel()  {

    companion object{
        const val TAG = "PresentationViewModel"
    }

    private val _uiState = MutableSharedFlow<PresentationUiState>()
    var uiState : SharedFlow<PresentationUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

    fun getAuthenticationPreferences(){
        /** Search on data store or user preferences
         */
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val authentication = Authentication(
                    "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJwYXNzIiwic3ViIjoiYnVubnkiLCJpc3MiOiJjdXN0b21lci1tYW5hZ2VtZW50LXNlcnZpY2UiLCJhdWQiOiJzaG9wcGluZy1zZXJ2aWNlIiwiYXV0aG9yaXRpZXMiOlsiRFJJVkVSIl0sImlhdCI6MTY2MTgzMDQ0NiwiZXhwIjoxNjYxOTE2ODQ2fQ.20KwOmR5Z_w1BKrAEvNuGoPX8R7xLAvgXQHG-w9Qu3Is8YJEsvNcKKyOKPZgIszlSeGEtJzPuw7EeCtj6dQL7g",
                    "alexanderommelsw@gmail.com",
                    "Valeria")
                /** Need to test login screen! **/
                throw Exception("No credentials found")
                _uiState.emit(PresentationUiState.AuthenticationPreferencesFound(authentication))
            }catch (e: Exception){
                AppLog.d(TAG,"$e")
                _uiState.emit(PresentationUiState.AuthenticationPreferencesNotFound)
            }
        }
    }

}