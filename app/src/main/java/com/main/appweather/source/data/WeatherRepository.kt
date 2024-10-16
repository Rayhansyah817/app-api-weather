package com.main.appweather.source.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.main.appweather.BuildConfig
import com.main.appweather.source.network.ApiService

class WeatherRepository(
    private val apiService: ApiService
) {
    fun getFetchWeather(city: String): LiveData<Response<WeatherResponse>> = liveData {
        emit(Response.Loading)
        try {
            Log.d("WeatherRepository", "Fetching weather data for city: $city")
            val response = apiService.getCurrentWeather(BuildConfig.API_KEY, city, 6)
            emit(Response.Success(data = response))
            Log.d("WeatherRepository", "$response")
        } catch (e: Exception) {
            Log.d("WeatherRepository", "getWeather: ${e.message.toString()} ")
            emit(Response.Error(e.message.toString()))
        }
    }
}