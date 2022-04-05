package com.example.imagesearch

import android.util.Log
import com.example.imagesearch.data.image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoImageSearchService {
    @Headers("Authorization: KakaoAK yourRESTAPI")
    @GET("/v2/search/image")
    fun requestSearchImage(
        @Query("query") keyword: String,
        @Query("sort") sort: String = "recency",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 80
    ): Call<image>
    // 디폴트는 같은 패키지의 image
}

object KakaoImageSearch {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(KakaoImageSearchService::class.java)
    fun search(keyword: String, sort: String="recency", page: Int=1, size: Int=80,
    callback: (image)->Unit){
        service.requestSearchImage(keyword, sort, page, size)
            .enqueue(object: Callback<image>{
                override fun onResponse(call: Call<image>, response: Response<image>) {
                    if (response.isSuccessful){
                        val image = response.body()
                        callback(image!!)
                    }
                }

                override fun onFailure(call: Call<image>, t: Throwable) {
                    Log.e("fail", t.toString())
                }
            }
            )
    }
//    fun getService(): KakaoImageSearchService = retrofit.create(KakaoImageSearchService::class.java)
}