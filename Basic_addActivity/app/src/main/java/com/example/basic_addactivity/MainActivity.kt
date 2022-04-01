package com.example.basic_addactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // companion object : 자바의 static 개념과 비슷함
    companion object{
        const val TEXT_KEY = "TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnactivity.setOnClickListener {
            val i = Intent(this, MainActivity2::class.java)
            i.putExtra(TEXT_KEY, "Hello Hi")
            startActivity(i)
        }
    }
}