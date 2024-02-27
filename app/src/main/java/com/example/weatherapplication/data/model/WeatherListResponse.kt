package com.example.weatherapplication.data.model

import com.google.gson.annotations.SerializedName

data class WeatherListResponse(@SerializedName("description")
                               val description : String)
