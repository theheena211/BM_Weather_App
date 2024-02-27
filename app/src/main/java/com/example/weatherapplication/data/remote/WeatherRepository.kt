package com.example.weatherapplication.data.remote

import com.example.weatherapplication.data.model.WeatherForecastResponse
import com.example.weatherapplication.data.model.WeatherMainResponse
import com.example.weatherapplication.network.api.WeatherAPI

open class WeatherRepository {
    var api: WeatherAPI

    init {
        api = RetrofitInstance.api
    }

    suspend open fun getWeather(zipCode : String, apiKey : String): WeatherMainResponse {
        return api.getWeather(zipCode, apiKey)
    }

    suspend open fun getForecast(zipCode : String, apiKey : String): WeatherForecastResponse {
        return api.getForecast(zipCode, apiKey)
    }
}