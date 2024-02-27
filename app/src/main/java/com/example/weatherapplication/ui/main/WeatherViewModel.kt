package com.example.weatherapplication.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.model.WeatherForecastResponse
import com.example.weatherapplication.data.model.WeatherMainResponse
import com.example.weatherapplication.data.remote.WeatherRepository
import com.example.weatherapplication.utils.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val mRepository: WeatherRepository
    private val _weatherLiveData = MutableLiveData<WeatherMainResponse>()
    val weatherLiveData: LiveData<WeatherMainResponse> = _weatherLiveData
    private val _forecastLiveData = MutableLiveData<WeatherForecastResponse>()
    val forecastLiveData: LiveData<WeatherForecastResponse> = _forecastLiveData

    init {
        mRepository = WeatherRepository()
    }

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        exception.message?.let { Log.d("Error", it) }
        handleError()
    }

    fun fetchWeatherInformation(zipCode: String) {
        val stringBuilder = StringBuilder()
        stringBuilder.append(zipCode).append(Constants.COMA).append(Constants.INDIA_CODE)
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val weatherDeferred = async { fetchCurrentWeatherInfo(stringBuilder.toString()) }
            val forecastDeferred = async { fetchForecastInfo(stringBuilder.toString()) }
                val weatherResponse = weatherDeferred.await()
                handleWeatherResponse(weatherResponse)
                val forecastResponse = forecastDeferred.await()
                handleForecastResponse(forecastResponse)
        }
    }

    private fun handleWeatherResponse(weatherResponse: WeatherMainResponse?) {
        _weatherLiveData.postValue(weatherResponse)
    }

    private fun handleForecastResponse(forecastResponse: WeatherForecastResponse?) {
        _forecastLiveData.postValue(forecastResponse)
    }

    private fun handleError() {
        _weatherLiveData.postValue(null)
    }

    private suspend fun fetchCurrentWeatherInfo(zipCode: String): WeatherMainResponse {
        return mRepository.getWeather(
            zipCode,
            Constants.API_KEY
        )
    }

    private suspend fun fetchForecastInfo(zipCode: String): WeatherForecastResponse {
        return mRepository.getForecast(
            zipCode,
            Constants.API_KEY
        )
    }
}