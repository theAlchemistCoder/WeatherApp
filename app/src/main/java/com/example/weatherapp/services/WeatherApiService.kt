package com.example.weatherapp.services

import com.example.weatherapp.models.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int
//        @Query("aqi") aqi: String,
//        @Query("alerts") alerts: String
    ): Weather

    @GET("current.json")
    suspend fun getCurrent(
        @Query("key") apiKey: String,
        @Query("q") location: String,
//        @Query("aqi") aqi: String,
//        @Query("alerts") alerts: String
    ): Weather
}
