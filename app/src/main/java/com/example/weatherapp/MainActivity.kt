package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.ImageView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.network.RetrofitClient
import com.example.weatherapp.model.WeatherResponse
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.weatherapp.BuildConfig


class MainActivity : AppCompatActivity() {

    val apiKey = BuildConfig.OPENWEATHER_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityInput = findViewById<EditText>(R.id.cityInput)
        val searchButton = findViewById<Button>(R.id.searchButton)

        searchButton.setOnClickListener {
            val city = cityInput.text.toString().trim()

            if (city.isNotEmpty()) {
                getWeather(city)
            } else {
                Toast.makeText(this, "Please enter a city name.", Toast.LENGTH_SHORT).show()
            }
        }

        getWeather()
    }

    private fun getWeather(city: String) {
        Log.d("WEATHER", "City searched: $city")
        Log.d("WEATHER", "API key length: ${apiKey.length}")

        val call = RetrofitClient.weatherApi.getCurrentWeather(city, apiKey)

        call.enqueue(object : retrofit2.Callback<WeatherResponse> {

            override fun onResponse(
                call: retrofit2.Call<WeatherResponse>,
                response: retrofit2.Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weather = response.body()

                    if (weather != null) {
                        findViewById<TextView>(R.id.cityName).text = weather.cityName
                        findViewById<TextView>(R.id.temperature).text =
                            "${weather.main.temperature}°C"
                        findViewById<TextView>(R.id.shortDescription).text =
                            weather.weather[0].shortDescription
                        findViewById<TextView>(R.id.longDescription).text =
                            weather.weather[0].longDescription
                        findViewById<TextView>(R.id.feelsLike).text =
                            "Feels Like: ${weather.main.feelsLike}°C"

                        findViewById<TextView>(R.id.humidity).text =
                            "Humidity: ${weather.main.humidity}%"

                        findViewById<TextView>(R.id.windSpeed).text =
                            "Wind Speed: ${weather.wind.speed} m/s"

                        findViewById<TextView>(R.id.pressure).text =
                            "Pressure: ${weather.main.pressure} hPa"

                        val iconUrl =
                            "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"

                        Glide.with(this@MainActivity)
                            .load(iconUrl)
                            .into(findViewById(R.id.weatherIcon))
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()

                    Log.e("WEATHER", "Error Code: ${response.code()}")
                    Log.e("WEATHER", "Error Message: $errorMessage")

                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(
                call: retrofit2.Call<WeatherResponse>,
                t: Throwable
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "Unable to connect. Please check your internet.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getWeather() {

        val call = RetrofitClient.weatherApi
            .getCurrentWeather(
                city = "New York",
                apiKey = apiKey
            )

        call.enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                if (response.isSuccessful) {

                    val weather = response.body()

                    if (weather != null) {
                        val cityText = findViewById<TextView>(R.id.cityName)
                        val temperatureText = findViewById<TextView>(R.id.temperature)
                        val shortDescriptionText = findViewById<TextView>(R.id.shortDescription)
                        val longDescriptionText = findViewById<TextView>(R.id.longDescription)

                        cityText.text = weather.cityName
                        temperatureText.text = "${weather.main.temperature}°C"
                        shortDescriptionText.text = weather.weather[0].shortDescription
                        longDescriptionText.text = weather.weather[0].longDescription

                        val iconCode = weather.weather[0].icon
                        val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"

                        Log.d("WEATHER", "Icon Code: $iconCode")
                        Log.d("WEATHER", "Icon URL: $iconUrl")

                        val weatherIcon = findViewById<ImageView>(R.id.weatherIcon)

                        Glide.with(this@MainActivity)
                            .load(iconUrl)
                            .into(weatherIcon)
                    }

                } else {
                Log.e("WEATHER", "API Error: ${response.code()}")
                Log.e(
                    "WEATHER",
                    "Error Body: ${response.errorBody()?.string()}"
                )
            }
            }

            override fun onFailure(
                call: Call<WeatherResponse>,
                t: Throwable
            ) {
                Log.e("WEATHER", "Network Error: ${t.message}")
            }
        })
    }
}
