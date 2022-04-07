package com.example.basic_navi.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basic_navi.Mqtt
import com.example.basic_navi.databinding.FragmentDeviceControlBinding

import org.eclipse.paho.client.mqttv3.MqttMessage

private const val SUB_TOPIC = "iot/#"
private const val PUB_TOPIC = "iot/led"
private const val SERVER_URI = "tcp://172.30.1.21:1883"
// 브로커는 pc주소
// 확인 : mosquitto_sub -v -h 172.30.1.21 -p 1883 -t iot/#

class DeviceControlFragment : Fragment() {

    private var _binding: FragmentDeviceControlBinding? = null
    private val binding get() = _binding!!

    lateinit var mqttClient: Mqtt


    // mqtt 생성자의 첫 번째 인자는 context
    // 이벤트 핸들러 사용
    // 프래그먼트가 액티비티에 연결될 때 호출되는 이벤트 핸들러
    override fun onAttach(context: Context) {
        super.onAttach(context)

        mqttClient = Mqtt(context, SERVER_URI)
        try {
            // mqttClient.setCallback { topic, message ->}
            mqttClient.setCallback(::onReceived)
            mqttClient.connect(arrayOf<String>(SUB_TOPIC))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDeviceControlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.Ledswitch.setOnCheckedChangeListener { compoundButton, b ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}