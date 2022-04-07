package com.example.imagesearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.imagesearch.data.Document
import com.example.imagesearch.data.image
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    val imageList = mutableListOf<Document>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageListView.adapter = ImageListAdapter(imageList, ::onItemClick)
        imageListView.layoutManager = GridLayoutManager(this, 3)

        btn_search.setOnClickListener {
            val keyword = main_name.text.toString()
            searchImage(keyword)
//            main_name.setText("")
        }

        OpenWeather.getWeather("Incheon"){
            val iconCode = it.weather[0].icon
            val weather = "${it.weather[0].description}     온도 ${it.main.temp}°C / 습도 ${it.main.humidity}%"
            val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"

            Glide.with(this).load(iconUrl).into(icon)
            weatherinfo.text = weather

            Log.d("CurrentWeather", it.toString())
        }
    }

    private fun searchImage(keyword: String) {
        KakaoImageSearch.search(keyword) {
            imageList.clear()
            imageList.addAll(it!!.documents)
            imageListView.adapter?.notifyDataSetChanged()
        }


// 방법2
//        KakaoImageSearch.getService()
//            .requestSearchImage(keyword = keyword, page = 1)
//            .enqueue(object : Callback<image> {
//
//                override fun onFailure(call: Call<image>, t: Throwable) {
//                    Log.e("fail", t.toString())
//                }
//
//                override fun onResponse(call: Call<image>, response: Response<image>) {
//                    if (response.isSuccessful) {
//                        val image = response.body()
//                        imageList.addAll(image!!.documents)
//                        imageListView.adapter?.notifyDataSetChanged()
//                    }
//                }
//        })
    }

    private fun onItemClick(document: Document) {
//        기능 추가 필요
//        val view_detail = Intent(this, MainActivity2::class.java)
//        intent.putExtra("key", imageList.indexOf(document))
//        startActivity(view_detail)
    }

}