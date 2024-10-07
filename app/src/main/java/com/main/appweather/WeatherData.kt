package com.main.appweather

import com.google.gson.annotations.SerializedName

data class LocationData(
    @field:SerializedName("name")
    val city: String,

    @field:SerializedName("region")
    val region: String,

    @field:SerializedName("country")
    val country: String
)

data class CurrentData(
    @field:SerializedName("last_updated")
    val last_updated: String,

    @field:SerializedName("temp_c")
    val temp_c: Float,

    @field:SerializedName("temp_f")
    val temp_f: Float,

    @field:SerializedName("wind_mph")
    val wind_mph: Float,

    @field:SerializedName("wind_kph")
    val wind_kph: Float,

    @field:SerializedName("pressure_in")
    val pressure_in: Float,

    @field:SerializedName("humidity")
    val humidity: Int
)

data class ConditionData (

    @field:SerializedName("text")
    val cuaca: String,

    @field:SerializedName("icon")
    val icon: String
)
