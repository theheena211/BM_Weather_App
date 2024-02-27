package com.example.weatherapplication.network.api

import com.example.weatherapplication.data.model.WeatherForecastResponse
import com.example.weatherapplication.data.model.WeatherMainResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("zip") zipCode: String,
        @Query("APPID") apiKey: String
    ): WeatherMainResponse

    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("zip") zipCode: String,
        @Query("APPID") apiKey: String
    ): WeatherForecastResponse
}