package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.models.Current
import com.example.weatherapp.models.Forecast
import kotlinx.coroutines.delay
import com.example.weatherapp.models.Weather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(null)

    val weather = _weather.asStateFlow()

    init {
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
                )
            )
        )

        _weather.value = initialWeather
    }
}