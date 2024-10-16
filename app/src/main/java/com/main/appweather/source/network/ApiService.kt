package com.main.appweather.source.network

import com.main.appweather.source.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("key") apikey: String,
        @Query("q") q: String,
        @Query("days") days: Int
    ): WeatherResponse

}