package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.models.Weather
import com.example.weatherapp.services.WeatherService
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather = _weather.asStateFlow()

    /*
    // This enum is no longer needed as we fetch both data types at once.
    enum class WeatherType(val value: Int) {
        CURRENT(0),
        FORECAST(1)
    }
    */

    init {
        fetchWeatherData()
    }

    /*
    // This function is replaced by the new fetchWeatherData() that gets both current and forecast data.
    fun fetchWeatherData(fetchType: WeatherType = WeatherType.CURRENT) {
        viewModelScope.launch {
            val apiKey = "url"
            val location = "Halifax"
            val days = 14
            if (fetchType == WeatherType.CURRENT)
            {
                _weather.value = WeatherService.api.getCurrent(apiKey, location)
            }
            else
            {
                _weather.value = WeatherService.api.getForecast(apiKey, location, days)
            }
        }
    }
    */

    private fun fetchWeatherData() {
        viewModelScope.launch {
            try {
                // IMPORTANT: Replace "YOUR_API_KEY" with your actual WeatherAPI.com key
                val apiKey = "3e9fa8aefa2e41f39f8194225252210"
                val location = "Halifax"
                val days = 14

                // Fetch both current and forecast data in parallel
                val currentDataJob = async { WeatherService.api.getCurrent(apiKey, location) }
                val forecastDataJob = async { WeatherService.api.getForecast(apiKey, location, days) }

                // Wait for both requests to complete
                val currentResponse = currentDataJob.await()
                val forecastResponse = forecastDataJob.await()

                // Combine the results into a single Weather object
                val combinedWeather = Weather(
                    location = currentResponse.location,
                    current = currentResponse.current,
                    forecastData = forecastResponse.forecastData
                )

                _weather.value = combinedWeather

            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching or combining weather data", e)
            }
        }
    }
}
