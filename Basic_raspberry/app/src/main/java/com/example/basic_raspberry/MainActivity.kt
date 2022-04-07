package com.example.basic_raspberry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.niqdev.mjpeg.DisplayMode
import com.github.niqdev.mjpeg.Mjpeg
import com.github.niqdev.mjpeg.MjpegInputStream
import kotlinx.android.synthetic.main.activity_main.*

const val PUB_TOPIC = "iot/camera/angle"
const val SERVER_URI = "tcp://172.30.1.21:1883"
//확인 : mosquitto_sub -v -h 172.30.1.21 -p 1883 -t iot/#

class MainActivity : AppCompatActivity() {

    lateinit var mqttClient: Mqtt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mqttClient = Mqtt(this, SERVER_URI)
        try {
            mqttClient.connect()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 캐시 때문에 버튼을 눌러도 예전 이미지가 뜸 -> 아래 두 줄 추가(load 뒤에)
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.skipMemoryCache(true)
        btnSnapshot.setOnClickListener {
            mjpeg.visibility = View.INVISIBLE
            imageView.visibility = View.VISIBLE
            val url = "http://121.172.128.191:8000/mjpeg/snapshot"
            Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView)
        }

        btnStream.setOnClickListener {
            imageView.visibility = View.INVISIBLE
            mjpeg.visibility = View.VISIBLE

            Mjpeg.newInstance()
                .open("http://121.172.128.191:8000/mjpeg/stream/", 5)
                .subscribe { inputStream: MjpegInputStream? ->
                    mjpeg.setSource(inputStream!!)
                    mjpeg.setDisplayMode(DisplayMode.BEST_FIT)
                }
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(view: SeekBar?, value: Int, fromUser: Boolean) {
                // 사람이 직접 바꾸는 경우, mp3처럼 프로그램에 의해서 바뀌는 경우로 true, false
                // 사람이 움직였을 때가 true
                if(fromUser){
                    txtAngle.text = "SeekBar 값 $value"

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
        if(mjpeg.isStreaming)
            mjpeg.stopPlayback()
    }
}