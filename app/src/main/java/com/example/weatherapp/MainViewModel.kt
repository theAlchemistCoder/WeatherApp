package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            current = Weather.Current(
                image = "ic_partly_cloudy",
                condition = "A mix of sun and cloud",
                temperature = 13,
                precipitationType = "None",
                precipitationAmount = 0,
                location = "Halifax, NS",
                windDirection = "SW",
                windSpeed = "15 km/h"
            ),
            forecast = listOf(
                Weather.Forecast(
                    date = "Oct 10",
                    image = "ic_sunny",
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
                Weather.Forecast(
                    date = "Oct 11",
                    image = "ic_showers",
                    highTemp = 12,
                    lowTemp = 6,
                    condition = "Chance of showers",
                    precipitationType = "Rain",
                    precipitationAmount = 5,
                    precipitationProbability = 60,
                    windDirection = "NW",
                    windSpeed = 25,
                    humidity = 80
                ),
                Weather.Forecast(
                    date = "Oct 12",
                    image = "ic_cloudy",
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

        // Launch a coroutine to simulate a data update after 10 seconds
        viewModelScope.launch {
            delay(10000)

            // Create new weather data for Toronto
            val updatedWeather = Weather(
                current = Weather.Current(
                    image = "ic_sunny",
                    condition = "Clear and sunny",
                    temperature = 18,
                    precipitationType = "None",
                    precipitationAmount = 0,
                    location = "Toronto, ON",
                    windDirection = "W",
                    windSpeed = "10 km/h"
                ),
                forecast = listOf(
                    Weather.Forecast(
                        date = "Oct 10",
                        image = "ic_sunny",
                        highTemp = 20,
                        lowTemp = 11,
                        condition = "Sunny",
                        precipitationType = "None",
                        precipitationAmount = 0,
                        precipitationProbability = 5,
                        windDirection = "SW",
                        windSpeed = 15,
                        humidity = 55
                    ),
                    Weather.Forecast(
                        date = "Oct 11",
                        image = "ic_sunny",
                        highTemp = 22,
                        lowTemp = 13,
                        condition = "Sunny",
                        precipitationType = "None",
                        precipitationAmount = 0,
                        precipitationProbability = 0,
                        windDirection = "S",
                        windSpeed = 12,
                        humidity = 60
                    ),
                    Weather.Forecast(
                        date = "Oct 12",
                        image = "ic_rainy",
                        highTemp = 19,
                        lowTemp = 12,
                        condition = "Rain developing",
                        precipitationType = "Rain",
                        precipitationAmount = 10,
                        precipitationProbability = 90,
                        windDirection = "E",
                        windSpeed = 22,
                        humidity = 85
                    )
                )
            )

            // Update the StateFlow, which will notify the UI of the change
            _weather.value = updatedWeather
        }
    }
}