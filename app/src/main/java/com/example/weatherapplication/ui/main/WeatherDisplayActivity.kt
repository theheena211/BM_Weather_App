package com.example.weatherapplication.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weatherapplication.R
import com.example.weatherapplication.data.model.TemperatureResponse
import com.example.weatherapplication.data.model.WeatherForecastResponse
import com.example.weatherapplication.data.model.WeatherListResponse
import com.example.weatherapplication.data.model.WeatherMainResponse
import com.example.weatherapplication.databinding.ActivityWeatherDisplayBinding
import com.example.weatherapplication.utils.Constants
import com.example.weatherapplication.utils.Constants.KELVIN_CELSIUS

class WeatherDisplayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherDisplayBinding
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var adapter: WeatherForecastAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.toolbar.title = getString(R.string.weather)
        setUpRecyclerView()

        viewModel.weatherLiveData.observe(this, Observer { weatherInfo ->
            binding.progressBar.visibility = View.GONE
            if (weatherInfo == null) {
                binding.mainLayout.visibility = View.GONE
                displayErrorToast()
            } else {
                binding.mainLayout.visibility = View.VISIBLE
                displayWeatherInfo(weatherInfo)
            }
        })

        viewModel.forecastLiveData.observe(this, Observer { forecastInfo ->
            displayForecastInfo(forecastInfo)
        })

        binding.btnGetWeather.setOnClickListener {
            if (!TextUtils.isEmpty(binding.etZipCode.text)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                binding.progressBar.visibility = View.VISIBLE
                viewModel.fetchWeatherInformation(binding.etZipCode.text.toString())
            }
        }

        addTextListenerOnZipCode()
    }

    fun addTextListenerOnZipCode() {
        binding.etZipCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Check if the zip code is empty
                val isZipCodeEmpty = s.isNullOrEmpty()

                // Disable or enable the button based on the zip code status
                binding.btnGetWeather.isEnabled = !isZipCodeEmpty
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }
        })
    }


    fun displayWeatherInfo(weatherInfo: WeatherMainResponse?) {
        val mainResponse: TemperatureResponse? = weatherInfo?.main
        val temperature: Double? = mainResponse?.temperature
        temperature?.let {
            val temp = String.format("%.2f", (it - KELVIN_CELSIUS))
            binding.temperatureTv.text = temp + getString(R.string.temperature_scale)
        }
        val humidity: Double? = mainResponse?.humidity
        var placeHolderString: String = getString(R.string.humidity)
        humidity?.let {
            binding.humidityTv.text = placeHolderString + humidity.toInt() + Constants.PERCENTAGE
        }
        val weatherInfoList: List<WeatherListResponse>? = weatherInfo?.weather
        weatherInfoList?.let {
            if (weatherInfoList.isNotEmpty()) {
                binding.weatherDescTv.text = it[0].description
            }
        }
        placeHolderString = getString(R.string.wind)
        val windScale = getString(R.string.metres_per_sec)
        val wind: Double? = weatherInfo?.wind?.speed
        wind?.let { binding.windTv.text = "$placeHolderString $wind$windScale" }
        weatherInfo?.cityName?.let { binding.cityTv.text = it }
    }

    fun displayForecastInfo(response: WeatherForecastResponse?) {
        response?.let { adapter.submitList(it.weatherList) }
        binding.forecastRv.adapter = this.adapter
    }


    private fun setUpRecyclerView() {
        adapter = WeatherForecastAdapter(this, null)
        binding.forecastRv.apply {
            adapter = this@WeatherDisplayActivity.adapter
        }
    }

    private fun displayErrorToast() {
        Toast.makeText(this, getString(R.string.invalid_zip_code), Toast.LENGTH_SHORT).show()
    }

}