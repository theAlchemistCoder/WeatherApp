package com.example.weatherapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherService {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val api: WeatherApiService by lazy {
//        retrofit.create(WeatherApiService::class.java)
//    }
}
