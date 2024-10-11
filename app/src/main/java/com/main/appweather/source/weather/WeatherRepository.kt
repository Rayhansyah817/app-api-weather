package com.main.appweather.source.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.main.appweather.BuildConfig
import com.main.appweather.source.network.ApiService

class WeatherRepository(
    private val apiService: ApiService
) {

//    fun fetch(city: String): Call<WeatherResponse> {
//        return apiService.getCurrentWeather(BuildConfig.API_KEY, city)
//    }

    fun getFetchWeather(city: String): LiveData<Response<WeatherResponse>> = liveData {
        emit(Response.Loading)
        try {
            Log.d("WeatherRepository", "Fetching weather data for city: $city")
            val response = apiService.getCurrentWeather(BuildConfig.API_KEY, city)
            emit(Response.Success(response))
            Log.d("HomeFragment","$response")
        } catch (e: Exception) {
            Log.d("NewsRepository", "getWeather: ${e.message.toString()} ")
            emit(Response.Error(e.message.toString()))
        }
    }
}