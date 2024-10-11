package com.main.appweather.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.main.appweather.source.network.RetrofitClient
import com.main.appweather.source.weather.Response
import com.main.appweather.source.weather.WeatherRepository
import com.main.appweather.source.weather.WeatherResponse

class WeatherViewModel : ViewModel() {
//    private val _weatherData = MutableLiveData<WeatherResponse?>()
//    val weatherData: LiveData<WeatherResponse?> get() = _weatherData

    // Tambahkan fungsi untuk mengubah lokasi GPS
    private val _locationData = MutableLiveData<String>()
    val locationData: LiveData<String> get() = _locationData

    private val repository = WeatherRepository(RetrofitClient.getApiService())

    // Fungsi untuk memperbarui lokasi dari Fragment
    fun updateLocation(location: String) {
        Log.d("HomeFragment", "Accurate Location: $location")
        _locationData.value = location
    }

    fun fetchWeather(city: String): LiveData<Response<WeatherResponse>> {
        return repository.getFetchWeather(city)
    }

//    // Pindahkan logika fetch cuaca ke dalam ViewModel
//    fun fetchWeather(city: String) {
//        // Memanggil fungsi fetch dari repository
//        val client = repository.fetch(city) // Mengambil data cuaca
//
//        client.enqueue(object : Callback<WeatherResponse> {
//            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
//                if (response.isSuccessful) {
//                    val weatherResponse = response.body()
//                    Log.e("WeatherViewModel", "API Success: ${response.body()}")
//                    _weatherData.postValue(weatherResponse) // Mengisi data jika berhasil
//                } else {
//                    _weatherData.postValue(null) // Tangani kesalahan respon
//                    Log.e("WeatherViewModel", "Error Response: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
//                Log.e("WeatherViewModel", "API Failure: ${t.message}")
//                _weatherData.postValue(null) // Tangani kegagalan
//            }
//        })
//    }
}
