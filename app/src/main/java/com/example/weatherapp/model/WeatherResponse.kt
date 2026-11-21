
package com.example.weatherapp.model

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "name")
    val cityName: String,

    @Json(name = "main")
    val main: MainWeather,

    @Json(name = "weather")
    val weather: List<WeatherDescription>,

    @Json(name = "wind")
    val wind: WindWeather
)

data class MainWeather(
    @Json(name = "temp")
    val temperature: Double,

    @Json(name = "feels_like")
    val feelsLike: Double,

    @Json(name = "humidity")
    val humidity: Int,

    @Json(name = "pressure")
    val pressure: Int
)

data class WindWeather(
    @Json(name = "speed")
    val speed: Double
)

data class WeatherDescription(
    @Json(name = "main")
    val shortDescription: String,

    @Json(name = "description")
    val longDescription: String,

    @Json(name = "icon")
    val icon: String
)
