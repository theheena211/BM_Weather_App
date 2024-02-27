package com.example.weatherapplication.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {
    const val DATE_FORMAT = "dd MMM yyyy hh:mm a"

    fun getFormatttedDateTime(milisec: Long?): String? {
        milisec?.let {
            val date = Date(milisec)
            try {
                val sdf = SimpleDateFormat(DATE_FORMAT)
                val formattedDate : String? = sdf.format(date)
                return formattedDate
            }
            catch (e : Exception)
            {
                Log.e("Date_format_Tag","Date exception")
            }
        }
        return null;
    }


}