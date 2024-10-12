package com.main.appweather.source.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @field:SerializedName("location")
    val locationData: LocationData,

    @field:SerializedName("current")
    val currentData: CurrentData
)

data class LocationData(
    @field:SerializedName("name")
    val city: String,

    @field:SerializedName("region")
    val region: String,

    @field:SerializedName("country")
    val country: String,

    @field:SerializedName("lat")
    val lat: Double,

    @field:SerializedName("lon")
    val lon: Double
)

data class CurrentData(
    @field:SerializedName("last_updated")
    val lastUpdated: String,

    @field:SerializedName("temp_c")
    val tempC: Float,

    @field:SerializedName("temp_f")
    val tempF: Float,

    @field:SerializedName("wind_mph")
    val windMph: Float,

    @field:SerializedName("wind_kph")
    val windKph: Float,

    @field:SerializedName("pressure_in")
    val pressureIn: Float,

    @field:SerializedName("humidity")
    val humidity: Int,

    @field:SerializedName("cloud")
    val cloud: Int,

    @SerializedName("condition")
    val condition: ConditionData
)

data class ConditionData (

    @field:SerializedName("text")
    val cuaca: String,

    @field:SerializedName("icon")
    val icon: String
)



data class FiturWeather(
    val fitur: String,
    val icon: Int,
    val suhu: String
)

data class ForeCast(
    val day: String,
    val icon: String,
    val suhu: String
)
