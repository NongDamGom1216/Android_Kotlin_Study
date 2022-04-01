package com.example.basic_edittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    override fun onStart() {
        super.onStart()

        // 값이 없을 경우 리턴
        val i = intent ?: return

        val sID = i.getStringExtra(MainActivity.ID)
        val sPasswd = i.getStringExtra(MainActivity.PASSWD)

        txtMessage2.text = "아이디: ${sID}\n패스워드: ${sPasswd}"
        i.putExtra(MainActivity.RESULT, txtMessage2.text.toString())

        btnOK.setOnClickListener {
            setResult(MainActivity.REQUEST, i) // 종료할 때 리턴값으로 사용할 인텐트 설정
            // 첫 번째 인자 : 이 요청에 대한 응답 코드, 두 번째 인자 : 인텐트

            finish() // 액티비티 종료 함수
        }

        btnCancel.setOnClickListener {
            finish()
        }


    }
}