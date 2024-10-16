package com.main.appweather.ui.forecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.main.appweather.databinding.FragmentForecastBinding
import com.main.appweather.source.data.RecyclerForecastDay
import com.main.appweather.source.data.RecyclerForecastHour
import com.main.appweather.source.data.Response
import com.main.appweather.source.data.WeatherResponse
import com.main.appweather.ui.current.WeatherViewModel

class ForecastFragment : Fragment() {
    private val weatherViewModel: WeatherViewModel by activityViewModels()


    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCustomProgressBar()
        // Observasi LiveData dari ViewModel untuk perubahan lokasi
        weatherViewModel.locationData.observe(viewLifecycleOwner) { location ->
            location?.let {
                weatherViewModel.fetchWeather(it).observe(viewLifecycleOwner) { response ->
                    handleResponse(response)
                }
            }
        }
    }

    private fun handleResponse(response: Response<WeatherResponse>) {
        when (response) {
            is Response.Loading -> {  }
            is Response.Success -> {
                hideCustomProgressBar()
                showRecyclerListForecastDay(response.data)
                showRecyclerListForecastHour(response.data)
                Log.d("ForecastFragment", "${response.data}")
            }
            is Response.Error -> {
                hideCustomProgressBar()
                Log.d("ForecastFragment", response.error)
                Toast.makeText(
                    context,
                    "Terjadi kesalahan: " + response.error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun listForecastDay(weather: WeatherResponse): List<RecyclerForecastDay> {
        return weather.forecast.forecastDay.map { dateDay ->
            RecyclerForecastDay(
                "Date: ${dateDay.date}",
                "${dateDay.day.avgtempC}",
                "${dateDay.day.condition.cuaca}",
                "https:${dateDay.day.condition.icon}",
                "Sinar UV: ${dateDay.day.uv}"
            )
        }
    }

    private fun showRecyclerListForecastDay(weather: WeatherResponse) {
        binding.listForecastDay.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ListForecastDayAdapter(listForecastDay(weather))
        }
    }

    private fun listForecastHour(weather: WeatherResponse): List<RecyclerForecastHour> {
        val firstForcastDay = weather.forecast.forecastDay[0]
        // Map setiap data jam ke dalam model RecyclerForecast
        return firstForcastDay.hour.map { hourData ->
            RecyclerForecastHour(
                // Waktu
                hourData.time.substring(11),
                // Kondisi cuaca
                "https:${hourData.condition.icon}",
                // Suhu
                "${hourData.tempC}°C"
            )
        }
    }

    private fun showRecyclerListForecastHour(weather: WeatherResponse) {
        binding.listForecastHour.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ListForeCastAdapter(listForecastHour(weather))
        }
    }

    private fun showCustomProgressBar() {
        binding.progressBarForecast.visibility = View.VISIBLE
    }

    private fun hideCustomProgressBar() {
        binding.progressBarForecast.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}