package com.example.weatherapp.models

import com.example.weatherapp.R
import com.google.gson.annotations.SerializedName

// I have commented out your original data classes and created new ones that are compatible with the API.
/*
data class Current(
    val image: Int,
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
    val image: Int,
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
*/

// --- New data classes that match the API response ---

data class Weather(
    val location: Location,
    val current: Current,
    @SerializedName("forecast") private val forecastWrapper: ForecastWrapper
) {
    val forecast: List<Forecast> get() = forecastWrapper.forecastday
}

data class Location(
    val name: String,
    val region: String
) {
    val locationString: String get() = "$name, $region"
}

data class Current(
    @SerializedName("condition") private val conditionData: Condition,
    @SerializedName("temp_c") val temperature: Double,
    @SerializedName("precip_mm") val precipitationAmount: Double,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("wind_kph") val windSpeed: Double
) {
    val image: Int get() = R.drawable.sunny
    val condition: String get() = conditionData.text
}

data class ForecastWrapper(
    val forecastday: List<Forecast>
)

data class Forecast(
    val date: String,
    @SerializedName("day") private val dayData: Day
) {
    val image: Int get() = R.drawable.sunny
    val highTemp: Double get() = dayData.maxtempC
    val lowTemp: Double get() = dayData.mintempC
    val condition: String get() = dayData.conditionData.text
    val precipitationAmount: Double get() = dayData.totalprecipMm
    val precipitationProbability: Int get() = dayData.dailyChanceOfRain
    val windSpeed: Double get() = dayData.maxwindKph
    val humidity: Int get() = dayData.avghumidity
}

data class Day(
    @SerializedName("maxtemp_c") val maxtempC: Double,
    @SerializedName("mintemp_c") val mintempC: Double,
    @SerializedName("maxwind_kph") val maxwindKph: Double,
    @SerializedName("totalprecip_mm") val totalprecipMm: Double,
    @SerializedName("avghumidity") val avghumidity: Int,
    @SerializedName("daily_chance_of_rain") val dailyChanceOfRain: Int,
    @SerializedName("condition") val conditionData: Condition
)

data class Condition(
    val text: String,
    val icon: String
)
