package com.tongue.dandelioncourier.utils

import com.google.gson.Gson
import com.tongue.dandelioncourier.data.domain.Position
import com.tongue.dandelioncourier.data.domain.ShippingNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.conversions.jackson.withJackson
import org.hildan.krossbow.stomp.frame.FrameBody
import org.hildan.krossbow.stomp.sendText
import org.hildan.krossbow.stomp.subscribeText
import org.hildan.krossbow.websocket.WebSocketClient
import org.hildan.krossbow.websocket.builtin.builtIn
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient

class StompInstance {
    companion object{

        private const val HOST = "192.168.100.29"
        private const val PORT = "8088"
        const val URL = "ws://${HOST}:${PORT}/ws"

        val client = StompClient(OkHttpWebSocketClient())
        var sessionHandler: StompSession? = null


        suspend fun connect(jwt: String, courierSubscriptionsCallBack: CourierSubscriptionsCallBack){

            val uri = "${URL}?jwtToken=${jwt}"
            val session: StompSession = client.connect(uri)//.withJackson()

            val gson = Gson()
            val positionString = gson.toJson(Position(0.0f,0.0f,"my house","alex"))

            session.sendText("/app/shipping/position/share",positionString)

            val subscription: Flow<String> = session.subscribeText("/user/queue/shipping/couriers")
            subscription.collect {
                msg ->
                println("Received message")
                println(msg)
                val shippingNotification = gson.fromJson(msg,ShippingNotification::class.java)
                courierSubscriptionsCallBack.onShippingNotificationReceived(shippingNotification)
            }

            //sessionHandler = session
        }


    }

    interface CourierSubscriptionsCallBack{
        fun onShippingNotificationReceived(shippingNotification: ShippingNotification)
    }

}