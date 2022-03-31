package com.example.basic_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlus.setOnClickListener {
            val first = firstNumber.text.toString()
            val second = SecondNumber.text.toString()

            if(first.length == 0 || second.length == 0) {
                return@setOnClickListener
            }

            val result = first.toInt() + second.toInt()

            txtResult.text = result.toString()
        }

        btnMinus.setOnClickListener {
            val first = firstNumber.text.toString()
            val second = SecondNumber.text.toString()

            if(first.length == 0 || second.length == 0) {
                return@setOnClickListener
            }

            val result = first.toInt() - second.toInt()

            txtResult.text = result.toString()
        }
    }
}