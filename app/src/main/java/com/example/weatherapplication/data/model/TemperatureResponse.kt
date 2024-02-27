package com.example.weatherapplication.data.model

import com.google.gson.annotations.SerializedName

data class TemperatureResponse(@SerializedName("temp")
                               val temperature : Double, @SerializedName("humidity")
                                val humidity : Double)
