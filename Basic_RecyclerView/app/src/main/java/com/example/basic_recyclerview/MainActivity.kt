package com.example.basic_recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_mainlist.adapter = MainAdapter(MainDatas.items, ::onItemClick) //참조시킬 때 ::
        rv_mainlist.layoutManager = LinearLayoutManager(this)

        add_button.setOnClickListener {
            val title = subject_text.text.toString()
            val content = content_text.text.toString()
            val item = MainData(title, content, "")
            MainDatas.items += item

            rv_mainlist.adapter?.notifyDataSetChanged() // 데이터가 생겼을 때 목록을 업데이트하라는 신호
            // 이 때 어댑터는 null일 수도 있으니 엘비스 연산자

            subject_text.setText("")
            content_text.setText("")

        }
    }

    // 아이템을 클릭했을 때 호출할 메서드
    // Int -> Unit
    // 매개변수가 Int인 리턴 타입이 Unit 함수
    fun onItemClick(position: Int){
//        val data = items[position]

        //Toast.makeText(this, "${data.title}, ${data.content}", Toast.LENGTH_LONG).show()
        val i = Intent(this, MainDetail::class.java)
        i.putExtra("MAIN_DATA", position)
        startActivity(i)
    }

}