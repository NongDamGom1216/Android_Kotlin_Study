package com.example.basic_addactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basic_addactivity.MainActivity.Companion.TEXT_KEY
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val text = intent.getStringExtra(TEXT_KEY)
        textView2.text = text
    }
}