package com.example.weatherapp.models

//class Weather(current: Current, forecast: Any) {

    data class Current(
        val image: String,
        val condition: String,
        val temperature: Int,
        val precipitationType: String,
        val precipitationAmount: Int,
        val location: String,
        val windDirection: String,
        val windSpeed: String
    )

    data class Forecast(
        val date: String,
        val image: String,
        val highTemp: Int,
        val lowTemp: Int,
        val condition: String,
        val precipitationType: String,
        val precipitationAmount: Int,
        val precipitationProbability: Int,
        val windDirection: String,
        val windSpeed: Int,
        val humidity: Int
    )

    data class Weather(
        val current: Current,
        val forecast: List<Forecast>
    )

    data class Images(
        val image: String
    )
//}