package com.example.basic_navi.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.basic_navi.Mqtt
import com.example.basic_navi.databinding.FragmentSecureCameraBinding
import com.github.niqdev.mjpeg.DisplayMode
import com.github.niqdev.mjpeg.Mjpeg
import com.github.niqdev.mjpeg.MjpegInputStream
import org.eclipse.paho.client.mqttv3.MqttMessage

private const val PUB_TOPIC = "iot/camera/angle"
private const val SERVER_URI = "tcp://172.30.1.21:1883"
//확인 : mosquitto_sub -v -h 172.30.1.21 -p 1883 -t iot/#

class SecureCameraFragment : Fragment() {

    lateinit var mqttClient: Mqtt

    private var _binding: FragmentSecureCameraBinding? = null
    private val binding get() = _binding!!



    // mqtt 생성자의 첫 번째 인자는 context
    // 이벤트 핸들러 사용
    // 프래그먼트가 액티비티에 연결될 때 호출되는 이벤트 핸들러
    override fun onAttach(context: Context) {
        super.onAttach(context)

        mqttClient = Mqtt(context, SERVER_URI)
        try {
            // mqttClient.setCallback { topic, message ->}
            mqttClient.setCallback(::onReceived)
            mqttClient.connect(arrayOf<String>(PUB_TOPIC))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun onReceived(topic: String, message: MqttMessage) {
        // 토픽 수신 처리
        val msg = String(message.payload)
    }

    fun publish() {
        mqttClient.publish(PUB_TOPIC, "1")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSecureCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 캐시 때문에 버튼을 눌러도 예전 이미지가 뜸 -> 아래 두 줄 추가(load 뒤에)
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.skipMemoryCache(true)
        binding.btnSnapshot.setOnClickListener {
            binding.mjpeg.visibility = View.INVISIBLE
            binding.imageView.visibility = View.VISIBLE
            val url = "http://172.30.1.22:8000/mjpeg/snapshot"
            Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imageView)
        }

        binding.btnStream.setOnClickListener {
            binding.imageView.visibility = View.INVISIBLE
            binding.mjpeg.visibility = View.VISIBLE

            Mjpeg.newInstance()
                .open("http://172.30.1.22:8000/mjpeg/stream/", 5)
                .subscribe { inputStream: MjpegInputStream? ->
                    binding.mjpeg.setSource(inputStream!!)
                    binding.mjpeg.setDisplayMode(DisplayMode.BEST_FIT)
                }
        }

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(view: SeekBar?, value: Int, fromUser: Boolean) {
                // 사람이 직접 바꾸는 경우, mp3처럼 프로그램에 의해서 바뀌는 경우로 true, false
                // 사람이 움직였을 때가 true
                if(fromUser){
                    binding.txtAngle.text = "SeekBar 값 $value"

                    // SeekBar의 값을 publish
                    mqttClient.publish(PUB_TOPIC, "$value")

                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

    }

    override fun onPause() {
        super.onPause()
        if(binding.mjpeg.isStreaming)
            binding.mjpeg.stopPlayback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}