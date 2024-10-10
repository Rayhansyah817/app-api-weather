package com.main.appweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.main.appweather.source.network.RetrofitClient
import com.main.appweather.source.weather.WeatherRepository
import com.main.appweather.source.weather.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> get() = _weatherData

    private val repository = WeatherRepository(RetrofitClient.getApiService())

    fun fetchWeather() {
        // Memanggil fungsi fetch dari repository
        val client = repository.fetch() // Mengambil data cuaca

        client.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    _weatherData.postValue(weatherResponse) // Mengisi data jika berhasil
                } else {
                    _weatherData.postValue(null) // Tangani kesalahan respon
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _weatherData.postValue(null) // Tangani kegagalan
            }
        })
    }
}
