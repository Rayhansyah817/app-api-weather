package com.main.appweather.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.main.appweather.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val weatherViewModel: WeatherViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Memanggil fetchWeather untuk mendapatkan data cuaca
        weatherViewModel.fetchWeather()

        /// Observasi LiveData dari ViewModel untuk memperbarui UI
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            // Update UI dengan data cuaca yang didapat dari ViewModel
            weatherResponse?.let {
                Log.d("HomeFragment", "Weather Icon URL: ${it.currentData.condition.icon}")

                binding.txtCountry.text = it.locationData.country // Mengakses negara
                binding.txtRegion.text = it.locationData.region // Mengakses region
                binding.txtCuaca.text = it.currentData.condition.cuaca // Mengakses cuaca
                binding.txtKota.text = it.locationData.city // Mengakses kota dengan benar
                binding.txtSuhu.text = "${it.currentData.tempC}°C" // Pastikan menggunakan properti yang benar
                binding.imgCuaca.let { imageView ->
                    Glide.with(this)
                        .load("https:${it.currentData.condition.icon}") // Asumsikan ini adalah URL
                        .into(imageView)
                } // Mengatur ikon cuaca
                binding.txtSuhuDetail1.text = it.currentData.windKph.toString()
                binding.txtSuhuDetail2.text = it.currentData.pressureIn.toString()
                binding.txtSuhuDetail3.text = it.currentData.humidity.toString()
            } ?: run {
                // Tangani jika data tidak ditemukan
                binding.txtSuhu.text = ""
                binding.txtKota.text = "Data tidak ditemukan"
                binding.txtCuaca.text = ""
            }
        }
    }
}
