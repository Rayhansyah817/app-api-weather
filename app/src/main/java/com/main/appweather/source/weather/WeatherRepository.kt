package com.main.appweather.source.weather

import com.main.appweather.BuildConfig
import com.main.appweather.source.network.ApiService
import retrofit2.Call

class WeatherRepository(
    private val api: ApiService
) {

    fun fetch(): Call<WeatherResponse> {
        return api.getCurrentWeather(BuildConfig.API_KEY, "Bandung")
    }

}