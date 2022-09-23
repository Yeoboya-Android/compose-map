package kr.co.inforexseoul.common_model.test_model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class OpenWeatherMapDataModel(
    @SerializedName("lat") var lat: Double,
    @SerializedName("lon") var lon: Double,
    @SerializedName("timezone") var timezone: String,
    @SerializedName("hourly") var hourly: List<Hourly> = listOf()
)

data class Weather(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("main") var main: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("icon") var icon: String = ""
)

data class Hourly(
    @SerializedName("dt") var dt: Long = 0,
    @SerializedName("temp") var temp: Double = .0,
    @SerializedName("weather") var weather: List<Weather> = listOf()
) {
    fun getIconUrl() = weather.takeIf { it.isNotEmpty() }?.let {
        "http://openweathermap.org/img/wn/${weather[0].icon}@2x.png"
    } ?: ""

    @SuppressLint("SimpleDateFormat")
    fun getDay(): String = SimpleDateFormat("MM.dd").format(Date(dt * 1000))

    @SuppressLint("SimpleDateFormat")
    fun getTime(): String = SimpleDateFormat("HH:mm").format(Date(dt * 1000))

    fun getCelsius(): Double = temp - 273.15
}
