package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.models.Weather
import com.example.weatherapp.services.WeatherApiService
import com.example.weatherapp.services.WeatherService
//import com.example.weatherapp.services.WeatherService.BASE_URL
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather = _weather.asStateFlow()
    
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    init {
        // Fetch weather for a default location initially
        fetchWeatherData("Halifax")
    }

    fun fetchWeatherDataForLocation(coordinates: String) {
        fetchWeatherData(coordinates)
    }
    
    private fun fetchWeatherData(location: String) {
        viewModelScope.launch {
            try {
                // IMPORTANT: Replace "YOUR_API_KEY" with your actual WeatherAPI.com key
                val apiKey = "3e9fa8aefa2e41f39f8194225252210"
                val days = 14
                val aqi = "yes"
                val alerts = "yes"

                // Fetch both current and forecast data in parallel
                val currentDataJob = async { api.getCurrent(apiKey, location, aqi, alerts) }
                val forecastDataJob = async { api.getForecast(apiKey, location, days, aqi, alerts) }

                // Wait for both requests to complete
                val currentResponse = currentDataJob.await()
                val forecastResponse = forecastDataJob.await()

                // Combine the results into a single Weather object
                val combinedWeather = Weather(
                    location = currentResponse.location,
                    current = currentResponse.current,
                    forecastData = forecastResponse.forecastData,
                    alerts = forecastResponse.alerts
                )

                _weather.value = combinedWeather

            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching or combining weather data", e)
            }
        }
    }
}
