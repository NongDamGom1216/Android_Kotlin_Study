package com.example.imagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.data.Document
import com.example.imagesearch.data.image
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            main_name.setText("")
        }
    }

    private fun searchImage(keyword: String) {
        KakaoImageSearch.getService()
            .requestSearchImage(keyword = keyword, page = 1)
            .enqueue(object : Callback<image> {

                override fun onFailure(call: Call<image>, t: Throwable) {
                    Log.e("fail", t.toString())
                }

                override fun onResponse(call: Call<image>, response: Response<image>) {
                    if (response.isSuccessful) {
                        val image = response.body()
                        imageList.addAll(image!!.documents)
                        imageListView.adapter?.notifyDataSetChanged()
                    }
                }
        })
    }

    private fun onItemClick(document: Document) {

    }

}