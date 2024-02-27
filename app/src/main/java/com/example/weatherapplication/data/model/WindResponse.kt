package com.example.weatherapplication.data.model

import com.google.gson.annotations.SerializedName

data class WindResponse(@SerializedName("speed")
                               val speed : Double)
