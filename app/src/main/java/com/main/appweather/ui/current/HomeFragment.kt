package com.main.appweather.ui.current

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.main.appweather.R
import com.main.appweather.databinding.FragmentHomeBinding
import com.main.appweather.source.data.FiturWeather
import com.main.appweather.source.data.ForeCast
import com.main.appweather.source.data.Response
import com.main.appweather.source.data.WeatherResponse
import com.main.appweather.ui.forecast.ListForeCastAdapter

class HomeFragment : Fragment() {
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity()) // Inisialisasi lokasi
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCustomProgressBar()
        checkGpsStatus()
        swipeRefresh()

        // Observasi LiveData dari ViewModel untuk perubahan lokasi
        weatherViewModel.locationData.observe(viewLifecycleOwner) { location ->
            location?.let {
                weatherViewModel.fetchWeather(it).observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Response.Loading -> {  }
                        is Response.Success -> {
                            updateWeatherUI(response.data)
                            hideCustomProgressBar()
                            Log.d("HomeFragment", "${response.data}")
                        }
                        is Response.Error -> {
                            hideCustomProgressBar()
                            Log.d("HomeFragment", response.error)
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan: " + response.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun locationRequest(): LocationRequest {
        return LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 60000)
            .setWaitForAccurateLocation(true) // Menunggu hingga mendapatkan lokasi yang akurat
            .build()
    }

    private fun checkGpsStatus() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest())
            .setAlwaysShow(true) // Menampilkan popup untuk mengaktifkan GPS

        val settingsClient: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task = settingsClient.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            // GPS sudah aktif, lanjutkan mendapatkan lokasi
            getPermissionGps()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    // Tampilkan dialog ke pengguna untuk menyalakan GPS
                    exception.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Tangani jika terjadi error saat menampilkan dialog
                    sendEx.printStackTrace()
                }
            }
            getPermissionGps()
            defaultWeatherUI()
            hideCustomProgressBar()
        }
    }

    private fun getPermissionGps() {
        // Mendapatkan lokasi terakhir
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Meminta izin jika belum diberikan
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest(), object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    val locationGetGps = "${location.latitude},${location.longitude}"
                    weatherViewModel.updateLocation(locationGetGps)
                } ?: run {
                    defaultWeatherUI()
                    hideCustomProgressBar()
                }
            }
        }, Looper.getMainLooper())
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            showCustomProgressBar()
            checkGpsStatus()
            binding.swipeRefresh.isRefreshing = false
            Log.e("HomeFragment","Swipe Refresh")
        }
    }

    private fun showCustomProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideCustomProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun updateWeatherUI(weather: WeatherResponse) {
        binding.apply {
            txtCountry.text = weather.locationData.country
            txtRegion.text = weather.locationData.region
            txtCuaca.text = weather.currentData.condition.cuaca
            txtKota.text = weather.locationData.city
            txtSuhu.text = "${weather.currentData.tempC}°C"
            Glide.with(this@HomeFragment)
                .load("https:${weather.currentData.condition.icon}")
                .into(binding.imgCuaca)
            showRecyclerListFitur(weather)
            showRecyclerListForeCast()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun defaultWeatherUI() {
        binding.apply {
            txtCountry.text = ""
            txtRegion.text = ""
            txtCuaca.text = ""
            txtKota.text = "Lokasi tidak tersedia,\nTolong Nyalakan Gps!"
            txtSuhu.text = ""
            binding.listFitur.adapter = ListFiturWeatherAdapter(defaultFitur())
        }
    }

    private fun defaultFitur(): List<FiturWeather> {
        return listOf(
            FiturWeather("Angin", R.drawable.ic_angin, "0"),
            FiturWeather("Tekanan", R.drawable.ic_tekanan, "0"),
            FiturWeather("Kelembaban", R.drawable.ic_lembab, "0"),
            FiturWeather("Awan", R.drawable.ic_lembab, "0")
        )
    }

    private fun listFitur(weather: WeatherResponse): List<FiturWeather> {
        return listOf(
            FiturWeather("Angin", R.drawable.ic_angin, "${weather.currentData.windKph} km/h"),
            FiturWeather("Tekanan", R.drawable.ic_tekanan, "${weather.currentData.pressureIn}"),
            FiturWeather("Kelembaban", R.drawable.ic_lembab, "${weather.currentData.humidity}"),
            FiturWeather("Awan", R.drawable.ic_lembab, "${weather.currentData.cloud}")
        )
    }

    private fun showRecyclerListFitur(weather: WeatherResponse) {
        binding.listFitur.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ListFiturWeatherAdapter(listFitur(weather))
        }
    }

    private fun listForeCast(): List<ForeCast> {
        return listOf(
            ForeCast("Senin", "", ""),
            ForeCast("Selasa", "", "")
        )
    }

    private fun showRecyclerListForeCast() {
        binding.listForeCast.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ListForeCastAdapter(listForeCast())
        }
    }

    companion object {
        private const val LOCATION_REQUEST_CODE = 100
        private const val REQUEST_CHECK_SETTINGS = 101
    }
}
