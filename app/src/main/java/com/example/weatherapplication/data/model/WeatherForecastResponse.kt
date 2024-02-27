package com.example.weatherapplication.data.model

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("list") val weatherList: List<WeatherMainResponse>
)
