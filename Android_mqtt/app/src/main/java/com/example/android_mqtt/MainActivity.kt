package com.example.android_mqtt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.eclipse.paho.client.mqttv3.MqttMessage

const val SUB_TOPIC = "iot/#"
const val PUB_TOPIC = "iot/led"
const val SERVER_URI = "tcp://172.30.1.21:1883"
// 브로커는 pc주소
// 확인 : mosquitto_sub -v -h 172.30.1.21 -p 1883 -t iot/#
class MainActivity : AppCompatActivity() {

    val TAG = "MqttActivity"
    lateinit var mqttClient: Mqtt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mqttClient = Mqtt(this, SERVER_URI)
        try {
            // mqttClient.setCallback { topic, message ->}
            mqttClient.setCallback(::onReceived)
            mqttClient.connect(arrayOf<String>(SUB_TOPIC))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Ledswitch.setOnCheckedChangeListener { compoundButton, b ->
            if(b) {
                // switch On
                mqttClient.publish(PUB_TOPIC, "on")
            } else {
                // switch Off
                mqttClient.publish(PUB_TOPIC, "off")
            }
        }
    }
    fun onReceived(topic: String, message: MqttMessage) {
        // 토픽 수신 처리
        val msg = String(message.payload)
    }
    fun publish() {
        mqttClient.publish(PUB_TOPIC, "1")
    }
}