package com.example.imagesearch

import android.util.Log
import com.example.imagesearch.data.weather.CurrentWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.openweathermap.org/data/2.5/weather?q=Incheon&appid=51c40a7cd6654a35eee5a1b70456107e&lang=kr&units=metric
// 아이콘 https://openweathermap.org/img/wn/04d@2x.png

const val APPID = "51c40a7cd6654a35eee5a1b70456107e"
interface OpenWeatherService {
    @GET("/data/2.5/weather?APPID=$APPID")
    fun getWeather(
        @Query("q") city: String,
        @Query("lang") lang: String = "kr",
        @Query("units") units: String = "metric"
    ) : Call<CurrentWeather>
}

// 싱글톤 객체
object OpenWeather {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org") // 기본 서버의 주소
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(OpenWeatherService::class.java) // 서비스 초기화

    fun getWeather(city: String, lang: String="kr", units: String="metric",
    callback: (CurrentWeather)->Unit){
        service.getWeather(city, lang, units)
            .enqueue(object: Callback<CurrentWeather> {
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful) {
                        val image = response.body()
                        callback(image!!)
                    }
                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    Log.e("fail", t.toString())
                }
            })
        }
    }


