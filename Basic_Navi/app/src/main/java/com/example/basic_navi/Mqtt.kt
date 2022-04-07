package com.example.basic_navi

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

// 첫 번째 인자 : 액티비티, 두 번째 인자 : 브로커 주소
class Mqtt(val ctx: Context, val uri: String) {
    val TAG = "Mqtt"
    var mqttClient: MqttAndroidClient

    init{
        mqttClient = MqttAndroidClient(ctx, uri, MqttClient.generateClientId())
    }

    // topic subscribe 수신을 위한 콜백 등록
    fun setCallback(callback: (topic: String, message: MqttMessage) -> Unit) {
        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                Log.i(TAG, "connection lost")
            }

            // subscribe 메시지 처리
            override fun messageArrived(topic: String, message: MqttMessage) {
                callback(topic, message)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.i(TAG, "msg delivered")
            }
        })
    }

    fun connect(topics : Array<String>?=null){
        val mqttConnectOptions = MqttConnectOptions()
        mqttClient.connect(mqttConnectOptions, null,
        object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i(TAG, "connect succeed")
                topics?.map {subscribeTopic(it)}
            }

            override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                Log.i(TAG, "connect failed")
            }
        })
    }
    // topic subscribe 등록
    private fun subscribeTopic(topic: String, qos:Int = 0){
        mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.i(TAG, "subscribed success")
            }

            override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                Log.i(TAG, "subscribed failed")
            }
        })
    }

    fun publish(topic: String, payload: String, qos: Int = 0){
        if(mqttClient.isConnected() === false) {
            mqttClient.connect()
        }

        val message = MqttMessage()
        message.payload = payload.toByteArray() // 바이트 배열 변환
        message.qos = qos

        mqttClient.publish(topic, message, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.i(TAG, "publish succeed!")
            }

            override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                Log.i(TAG, "publish failed!")
            }
        })
    }
}