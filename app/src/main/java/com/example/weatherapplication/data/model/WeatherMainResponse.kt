package com.example.weatherapplication.data.model

import com.google.gson.annotations.SerializedName

data class WeatherMainResponse(
    @SerializedName("dt") val date: Long?,
    @SerializedName("weather") val weather: List<WeatherListResponse>?,
    @SerializedName("main") val main: TemperatureResponse?,
    @SerializedName("wind") val wind: WindResponse?,
    @SerializedName("name") val cityName: String?
)
