package com.tongue.dandelioncourier.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tongue.dandelioncourier.data.model.Authentication
import com.tongue.dandelioncourier.data.model.Position
import com.tongue.dandelioncourier.utils.StompInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ActivityViewModel: ViewModel()  {

    lateinit var authentication: Authentication
    var position: Position = Position(0f,0f,"","alex")
    private var fetchJob : Job? = null

    fun connectStomp(jwt: String, courierSubscriptionsCallBack: StompInstance.CourierSubscriptionsCallBack){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            StompInstance.connect(jwt, courierSubscriptionsCallBack)
        }
    }

}