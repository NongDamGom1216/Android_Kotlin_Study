package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// AppCompatActivity() -> 생성자 호출하는 클래스!
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2) // 파일명은 소문자이어야 하고, 공백이 없어야 한다
    }
}