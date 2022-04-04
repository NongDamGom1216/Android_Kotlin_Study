package com.example.imagesearch

import com.example.imagesearch.data.image
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoImageSearchService {
    @Headers("Authorization: KakaoAK 91993241356264156056f235e6563db3")
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

//    val service = retrofit.create(KakaoImageSearchService::class.java)
//    fun search(keyword: String, callback: Callback<Image>){
//        service.requestSearchImage(keyword)
//            .enqueue(callback)
//    }
    fun getService(): KakaoImageSearchService = retrofit.create(KakaoImageSearchService::class.java)
}