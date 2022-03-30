package com.example.basic_usingxml

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//     id 'kotlin-android-extensions' 를 build.gradle(Module) 플러그인에 추가
        txtHello.apply{
            text = "아이유"
            textSize = 32.0F
            setTextColor(Color.parseColor("#0000FF")) //RGB
        }

//      같은 역할
//        findViewById<TextView>(R.id.txtHello).apply{
//            text = "안녕하세요"
//            textSize = 32.0F
//            setTextColor(Color.parseColor("#0000FF")) //RGB
//        }
    }
}