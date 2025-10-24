package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
//import com.example.weatherapp.models.Current
//import com.example.weatherapp.models.Forecast
import com.example.weatherapp.models.Weather
import com.example.weatherapp.services.WeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(null)

    val weather = _weather.asStateFlow()

    /*
    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://www.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherService : WeatherService = retrofit.create(WeatherService::class.java)
    */


    init {
        fetchWeatherData()
        /*
        // Create and set the initial weather data for Halifax
        val initialWeather = Weather(
            current = Current(
                image = R.drawable.sunny,
                condition = "A mix of sun and cloud",
                temperature = 13,
                precipitationType = "None",
                precipitationAmount = 0,
                location = "Halifax, NS",
                windDirection = "SW",
                windSpeed = "15 km/h"
            ),
            forecast = listOf(
                Forecast(
                    date = "Oct 10",
                    image = R.drawable.sunny,
                    highTemp = 15,
                    lowTemp = 7,
                    condition = "Mainly Sunny",
                    precipitationType = "None",
                    precipitationAmount = 0,
                    precipitationProbability = 10,
                    windDirection = "W",
                    windSpeed = 20,
                    humidity = 65
                ),
                Forecast(
                    date = "Oct 11",
                    image = R.drawable.snowy,
                    highTemp = 1,
                    lowTemp = -3,
                    condition = "Chance of Snow",
                    precipitationType = "Snow",
                    precipitationAmount = 5,
                    precipitationProbability = 60,
                    windDirection = "NW",
                    windSpeed = 25,
                    humidity = 80
                ),
                Forecast(
                    date = "Oct 12",
                    image = R.drawable.sunny_cloudy,
                    highTemp = 14,
                    lowTemp = 8,
                    condition = "Cloudy periods",
                    precipitationType = "None",
                    precipitationAmount = 0,
                    precipitationProbability = 20,
                    windDirection = "S",
                    windSpeed = 18,
                    humidity = 75
                ),
                Forecast(
                    date = "Oct 10",
                    image = R.drawable.sunny,
                    highTemp = 15,
                    lowTemp = 7,
                    condition = "Mainly Sunny",
                    precipitationType = "None",
                    precipitationAmount = 0,
                    precipitationProbability = 10,
                    windDirection = "W",
                    windSpeed = 20,
                    humidity = 65
                ),
                Forecast(
                    date = "Oct 11",
                    image = R.drawable.snowy,
                    highTemp = 1,
                    lowTemp = -3,
                    condition = "Chance of Snow",
                    precipitationType = "Snow",
                    precipitationAmount = 5,
                    precipitationProbability = 60,
                    windDirection = "NW",
                    windSpeed = 25,
                    humidity = 80
                ),
                Forecast(
                    date = "Oct 12",
                    image = R.drawable.sunny_cloudy,
                    highTemp = 14,
                    lowTemp = 8,
                    condition = "Cloudy periods",
                    precipitationType = "None",
                    precipitationAmount = 0,
                    precipitationProbability = 20,
                    windDirection = "S",
                    windSpeed = 18,
                    humidity = 75
                )
            )
        )

        _weather.value = initialWeather
        */
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            try {
                // IMPORTANT: Replace "YOUR_API_KEY" with your actual WeatherAPI.com key
                val apiKey = "3e9fa8aefa2e41f39f8194225252210"
                val location = "Halifax"
                val days = 3
                _weather.value = WeatherService.api.getForecast(apiKey, location, days)
            } catch (e: Exception) {
                // This error is expected because the data models don't match the API response.
                Log.e("MainViewModel", "Error fetching or parsing weather data", e)
            }
        }
    }
}