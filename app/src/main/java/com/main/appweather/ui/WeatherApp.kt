package com.main.appweather.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO )
    }

}