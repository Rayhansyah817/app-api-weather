package com.main.appweather.source.network

import com.main.appweather.source.weather.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") apikey: String,
        @Query("q") q: String

    ): Call<WeatherResponse>

}