package com.example.weatherapplication.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.data.model.WeatherMainResponse
import com.example.weatherapplication.databinding.ItemForecastBinding
import com.example.weatherapplication.utils.Constants
import com.example.weatherapplication.utils.Constants.KELVIN_CELSIUS
import com.example.weatherapplication.utils.DateUtils

class WeatherForecastAdapter(val mContext: Context, var forecastList: List<WeatherMainResponse>?) :
    RecyclerView.Adapter<WeatherForecastAdapter.ForecastViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding =
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return forecastList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = forecastList?.get(position)
        holder.bind(item)
    }

    fun submitList(list: List<WeatherMainResponse>) {
        forecastList = list
    }


    inner class ForecastViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherMainResponse?) {
            item?.main?.temperature?.let {
                val temp = String.format("%.2f", (it - KELVIN_CELSIUS))
                binding.forecastTemperatureTv.text = mContext.resources.getString(
                    R.string.temperature
                ) + temp + mContext.resources.getString(R.string.temperature_scale)
            }
            item?.main?.humidity?.let {
                binding.forecastHumidityTv.text = mContext.resources.getString(R.string.humidity) +
                        it.toInt() + Constants.PERCENTAGE
            }
            item?.wind?.let {
                binding.forecastWindTv.text = mContext.resources.getString(R.string.wind) +
                        it.speed + mContext.resources.getString(R.string.metres_per_sec)
            }
            item?.weather?.let {
                if (!it.isEmpty()) {
                    binding.forecastWeatherDescTv.text = it[0].description
                }
            }
            item?.date?.let{binding.dateTimeTv.text = DateUtils.getFormatttedDateTime(it)
            }
        }
    }
}