package com.example.weatherapplication

import com.example.weatherapplication.data.model.WeatherForecastResponse
import com.example.weatherapplication.data.model.WeatherMainResponse
import com.example.weatherapplication.data.remote.RetrofitInstance
import com.example.weatherapplication.data.remote.WeatherRepository
import com.example.weatherapplication.network.api.WeatherAPI
import com.example.weatherapplication.ui.main.WeatherViewModel
import com.example.weatherapplication.utils.Constants
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.mockito.MockitoAnnotations

import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    private val testCoroutineScope = TestScope()

    @Mock
    private lateinit var repository: WeatherRepository

    @Mock
    private lateinit var api: WeatherAPI

    private lateinit var viewModel: WeatherViewModel


    private lateinit var weatherResponse: WeatherMainResponse
    private lateinit var forecastResponse: WeatherForecastResponse

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = WeatherViewModel()
        val forecastList: List<WeatherMainResponse>
        forecastList = listOf()
        forecastResponse = WeatherForecastResponse(forecastList)
        weatherResponse = WeatherMainResponse(null, null, null, null, null)
    }

    private suspend fun mockingAPIcall() {
        repository.api = RetrofitInstance.api
        `when`(
            repository.getWeather(
                "133",
                Constants.API_KEY
            )
        ).thenReturn(
            weatherResponse
        )
        `when`(
            repository.getForecast(
                "133",
                Constants.API_KEY
            )
        ).thenReturn(
            forecastResponse
        )
    }

    @Test
    fun testFetchMostPopularArticles() {
        testCoroutineScope.runTest {
            mockingAPIcall()
            viewModel.fetchWeatherInformation("133")
            Assert.assertEquals(forecastResponse.weatherList.size, 0)
        }
    }

    private suspend fun mockingAPIcall_Failurecase() {
        repository.api = RetrofitInstance.api
        `when`(
            repository.getWeather(
                "133",
                Constants.API_KEY
            )
        ).thenThrow(CancellationException())
    }

    @Test
    fun testFetchMostPopularArticles_FailCase() {
        testCoroutineScope.runTest {
            mockingAPIcall_Failurecase()
            viewModel.fetchWeatherInformation("133")
        }
    }
}